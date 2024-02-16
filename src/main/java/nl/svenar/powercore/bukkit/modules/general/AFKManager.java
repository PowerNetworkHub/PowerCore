package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.utils.Util;
import nl.svenar.powercore.common.utils.PowerColor;

public class AFKManager {

    private BukkitRunnable task;
    private Map<UUID, Instant> lastActivity;
    private PowerCore plugin;

    public AFKManager(PowerCore plugin) {
        this.plugin = plugin;
        lastActivity = new HashMap<>();
    }

    public void setupTask(Plugin plugin) {
        if (task != null) {
            if (!task.isCancelled()) {
                task.cancel();
            }
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };

        task.runTaskTimerAsynchronously(plugin, Util.TASK_TPS, Util.TASK_TPS);
    }

    public void stopTask() {
        if (task != null) {
            if (!task.isCancelled()) {
                task.cancel();
            }
        }
    }

    public void tick() {
        int afkTime = plugin.getPluginConfigManager().getConfig().getInt("afk.timeout", 300);
        Instant now = Instant.now();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("powercore.afk.bypass")) {
                continue;
            }

            if (!lastActivity.containsKey(player.getUniqueId())) {
                lastActivity.put(player.getUniqueId(), now);
            }

            PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player.getUniqueId());

            if (lastActivity.get(player.getUniqueId()).plusSeconds(afkTime).isBefore(now)) {
                if (!pcPlayer.isAFK()) {
                    setAFK(pcPlayer, true);
                }
            } else {
                if (pcPlayer.isAFK()) {
                    setAFK(pcPlayer, false);
                }

            }
        }
    }

    public void setAFK(PCPlayer pcPlayer, boolean afk) {
        if (afk) {
            int afkTime = plugin.getPluginConfigManager().getConfig().getInt("afk.timeout", 300);
            lastActivity.put(pcPlayer.getUUID(), Instant.now().minusSeconds(afkTime * 2));
        } else {
            lastActivity.put(pcPlayer.getUUID(), Instant.now());
        }

        pcPlayer.setAFK(afk);
        Player player = Util.getPlayer(pcPlayer);
        if (player != null) {
            if (afk) {
                player.sendMessage(PowerColor.ChatColor.BLACK + "[" + PowerColor.ChatColor.DARK_PURPLE + "PC"
                        + PowerColor.ChatColor.BLACK + "] " + PowerColor.ChatColor.GRAY + "You are now AFK");
            } else {
                player.sendMessage(PowerColor.ChatColor.BLACK + "[" + PowerColor.ChatColor.DARK_PURPLE + "PC"
                        + PowerColor.ChatColor.BLACK + "] " + PowerColor.ChatColor.GRAY + "You are no longer AFK");
            }
        }
    }

    public void resetPlayerActivity(UUID playerUUID) {
        lastActivity.put(playerUUID, Instant.now());
    }
}
