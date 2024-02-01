package nl.svenar.powercore.bukkit.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;

public class PlayerListener implements Listener {

    private PowerCore plugin;

    public PlayerListener(PowerCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
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
        plugin.getPCPlayerHandler().getPlayer(event.getPlayer());

        boolean silentJoin = plugin.getPluginConfigManager().getConfig().getBoolean("event.join.chat.silent");
        String message = silentJoin ? null
                : ChatColor.translateAlternateColorCodes('&',
                        plugin.getPluginConfigManager().getConfig().getString("event.join.chat.message")
                                .replace("{player}", event.getPlayer().getDisplayName()));
        event.setJoinMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(event.getPlayer());
        pcPlayer.setLogoutLocation(event.getPlayer().getLocation());

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
}
