package nl.svenar.powercore.bukkit.events;

import java.time.Instant;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

public class PlayerListener implements Listener {

    private PowerCore plugin;

    public PlayerListener(PowerCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        if (plugin.getWhitelistConfigManager().getConfig().getBoolean("whitelist.enabled")) {
            if (!plugin.getWhitelistConfigManager().getConfig().getStringList("whitelist.players")
                    .contains(event.getUniqueId().toString())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                        ChatColor.translateAlternateColorCodes('&',
                                plugin.getWhitelistConfigManager().getConfig().getString("whitelist.kickmessage")));
            }
        }

        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getUniqueId());
        if (pcPlayer == null) {
            return;
        }
        if (pcPlayer.isBanned()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, pcPlayer.getBanReason());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        pcPlayer.setOnline(true);

        boolean silentJoin = plugin.getPluginConfigManager().getConfig().getBoolean("event.join.chat.silent");
        String message = silentJoin ? null
                : ChatColor.translateAlternateColorCodes('&',
                        plugin.getPluginConfigManager().getConfig().getString("event.join.chat.message")
                                .replace("{player}", event.getPlayer().getDisplayName()));
        event.setJoinMessage(message);

        int numUnreadMail = (int) pcPlayer.getMail().stream().filter(mail -> !mail.isRead()).count();
        if (numUnreadMail > 0) {
            event.getPlayer().sendMessage(PowerColor.ChatColor.BLACK + "[" + PowerColor.ChatColor.DARK_PURPLE + "!" + PowerColor.ChatColor.BLACK + "] "
                    + PowerColor.ChatColor.GRAY + "You have " + numUnreadMail + " unread mail" + (numUnreadMail != 1 ? "s" : ""));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        pcPlayer.setOnline(true);
        plugin.getAFKManager().resetPlayerActivity(pcPlayer.getUUID());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        pcPlayer.setLogoutLocation(event.getPlayer().getLocation());
        pcPlayer.setLastSeen(Instant.now());
        pcPlayer.setOnline(false);

        boolean silentQuit = plugin.getPluginConfigManager().getConfig().getBoolean("event.leave.chat.silent");
        String message = silentQuit ? null
                : ChatColor.translateAlternateColorCodes('&',
                        plugin.getPluginConfigManager().getConfig().getString("event.leave.chat.message")
                                .replace("{player}", event.getPlayer().getDisplayName()));
        event.setQuitMessage(message);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        pcPlayer.setLastLocation(event.getFrom());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getEntity().hasPermission("powercore.back.ondeath")) {
            return;
        }
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getEntity());
        pcPlayer.setLastLocation(event.getEntity().getLocation());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        if (pcPlayer.isMuted()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are muted");
        }

        plugin.getAFKManager().resetPlayerActivity(pcPlayer.getUUID());
    }
}
