package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.utils.Util;

public class TPAManager {

    private BukkitRunnable task;
    private PowerCore plugin;

    public TPAManager(PowerCore plugin) {
        this.plugin = plugin;
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
        int tpaTimeout = plugin.getPluginConfigManager().getConfig().getInt("tpa.timeout", 120);
        Instant now = Instant.now();

        for (PCPlayer pcPlayer : plugin.getPCPlayerHandler().getPCPlayers()) {
            Iterator<Entry<UUID, Instant>> iterator = pcPlayer.getTpaBuffer().entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<UUID, Instant> teleportRequestPlayer = iterator.next();
                if (now.getEpochSecond() - teleportRequestPlayer.getValue().getEpochSecond() > tpaTimeout) {
                    iterator.remove();
                }
            }
        }
    }
}
