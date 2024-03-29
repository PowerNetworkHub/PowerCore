package nl.svenar.powercore.bukkit.storage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.modules.general.PCLocation;
import nl.svenar.powercore.bukkit.modules.general.PCMail;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.modules.general.Waypoint;
import nl.svenar.powercore.bukkit.utils.TimeConverter;

public class PCPlayerHandler {

    private PowerCore plugin;
    private Map<UUID, PCPlayer> players = new HashMap<>();

    public PCPlayerHandler(PowerCore plugin) {
        this.plugin = plugin;
    }

    public void loadPlayers() {
        if (!plugin.getPlayerConfigManager().getConfig().contains("players")) {
            return;
        }

        for (String uuidString : plugin.getPlayerConfigManager().getConfig().getConfigurationSection("players")
                .getKeys(false)) {
            UUID uuid = UUID.fromString(uuidString);
            String name = plugin.getPlayerConfigManager().getConfig().getString("players." + uuidString + ".name");
            PCPlayer pcPlayer = new PCPlayer(uuid, name);

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".compass.enabled")) {
                pcPlayer.setCompassEnabled(plugin.getPlayerConfigManager().getConfig()
                    .getBoolean("players." + uuidString + ".compass.enabled"));
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".ban.is_banned")) {
                pcPlayer.setBanned(plugin.getPlayerConfigManager().getConfig()
                    .getBoolean("players." + uuidString + ".ban.is_banned"));
            }
            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".ban.reason")) {
                pcPlayer.setBanReason(plugin.getPlayerConfigManager().getConfig()
                    .getString("players." + uuidString + ".ban.reason"));
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".last_seen")) {
                pcPlayer.setLastSeen(TimeConverter.stringToInstant(plugin.getPlayerConfigManager().getConfig()
                    .getString("players." + uuidString + ".last_seen")));
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".muted")) {
                pcPlayer.setMuted(plugin.getPlayerConfigManager().getConfig()
                    .getBoolean("players." + uuidString + ".muted"));
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".logout_location")) {
                double x = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".logout_location.x");
                double y = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".logout_location.y");
                double z = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".logout_location.z");
                String world = plugin.getPlayerConfigManager().getConfig().getString(
                    "players." + uuidString + ".logout_location.world");
                PCLocation logoutLocation = new PCLocation(world, x, y, z);
                pcPlayer.setLogoutLocation(logoutLocation);
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".last_location")) {
                double x = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".last_location.x");
                double y = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".last_location.y");
                double z = plugin.getPlayerConfigManager().getConfig()
                    .getDouble("players." + uuidString + ".last_location.z");
                String world = plugin.getPlayerConfigManager().getConfig().getString(
                    "players." + uuidString + ".last_location.world");
                PCLocation lasLocation = new PCLocation(world, x, y, z);
                pcPlayer.setLogoutLocation(lasLocation);
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".compass.waypoints")
                    && plugin.getPlayerConfigManager().getConfig()
                            .isList("players." + uuidString + ".compass.waypoints")) {
                for (String waypointData : plugin.getPlayerConfigManager().getConfig()
                        .getStringList("players." + uuidString + ".compass.waypoints")) {
                    pcPlayer.addWaypoint(Waypoint.fromString(waypointData));
                }
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".homes")
                    && plugin.getPlayerConfigManager().getConfig()
                            .isConfigurationSection("players." + uuidString + ".homes")) {
                for (String homeName : plugin.getPlayerConfigManager().getConfig()
                        .getConfigurationSection("players." + uuidString + ".homes").getKeys(false)) {
                    String world = plugin.getPlayerConfigManager().getConfig()
                        .getString("players." + uuidString + ".homes." + homeName + ".world");
                    double x = plugin.getPlayerConfigManager().getConfig()
                        .getDouble("players." + uuidString + ".homes." + homeName + ".x");
                    double y = plugin.getPlayerConfigManager().getConfig().getDouble(
                        "players." + uuidString + ".homes." + homeName + ".y");
                    double z = plugin.getPlayerConfigManager().getConfig()
                        .getDouble("players." + uuidString + ".homes." + homeName + ".z");

                    PCLocation homeLocation = new PCLocation(world, x, y, z);
                    pcPlayer.addHome(homeName, homeLocation);
                }
            }

            if (plugin.getPlayerConfigManager().getConfig().contains("players." + uuidString + ".mails")
                    && plugin.getPlayerConfigManager().getConfig()
                            .isConfigurationSection("players." + uuidString + ".mails")) {
                for (String mailTimestamp : plugin.getPlayerConfigManager().getConfig()
                        .getConfigurationSection("players." + uuidString + ".mails").getKeys(false)) {
                    UUID senderUUID = UUID.fromString(plugin.getPlayerConfigManager().getConfig()
                        .getString("players." + uuidString + ".mails." + mailTimestamp + ".sender"));
                    String title = plugin.getPlayerConfigManager().getConfig()
                        .getString("players." + uuidString + ".mails." + mailTimestamp + ".title");
                    String message = plugin.getPlayerConfigManager().getConfig()
                        .getString("players." + uuidString + ".mails." + mailTimestamp + ".message");
                    boolean read = plugin.getPlayerConfigManager().getConfig()
                        .getBoolean("players." + uuidString + ".mails." + mailTimestamp + ".read");

                    pcPlayer.addMail(
                        new PCMail(senderUUID, title, message, TimeConverter.stringToInstant(mailTimestamp),
                                read));
                }
            }

            players.put(uuid, pcPlayer);
        }
    }

    public void savePlayers() {
        for (PCPlayer pcPlayer : players.values()) {

            plugin.getPlayerConfigManager().getConfig().set("players." + pcPlayer.getUUID().toString() + ".name",
                pcPlayer.getName());

            plugin.getPlayerConfigManager().getConfig()
                .set("players." + pcPlayer.getUUID().toString() + ".compass.enabled", pcPlayer.isCompassEnabled());

            plugin.getPlayerConfigManager().getConfig()
                .set("players." + pcPlayer.getUUID().toString() + ".ban.is_banned", pcPlayer.isBanned());
            plugin.getPlayerConfigManager().getConfig().set("players." + pcPlayer.getUUID().toString() + ".ban.reason",
                pcPlayer.getBanReason() != null ? pcPlayer.getBanReason() : "");

            plugin.getPlayerConfigManager().getConfig()
                .set("players." + pcPlayer.getUUID().toString() + ".muted", pcPlayer.isMuted());

            if (pcPlayer.getLastSeen() != null) {
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".last_seen",
                    TimeConverter.instantToString(pcPlayer.getLastSeen()));
            }

            if (pcPlayer.getLogoutLocation() != null) {
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".logout_location.x",
                    pcPlayer.getLogoutLocation().getX());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".logout_location.y",
                    pcPlayer.getLogoutLocation().getY());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".logout_location.z",
                    pcPlayer.getLogoutLocation().getZ());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".logout_location.world",
                    pcPlayer.getLogoutLocation().getWorld());
            }

            if (pcPlayer.getLastLocation() != null) {
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".last_location.x",
                    pcPlayer.getLastLocation().getX());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".last_location.y",
                    pcPlayer.getLastLocation().getY());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".last_location.z",
                    pcPlayer.getLastLocation().getZ());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".last_location.world",
                    pcPlayer.getLastLocation().getWorld());
            }

            List<String> waypointStrings = new ArrayList<>();
            for (Waypoint waypoint : pcPlayer.getWaypoints()) {
                waypointStrings.add(waypoint.toString());
            }
            plugin.getPlayerConfigManager().getConfig().set(
                "players." + pcPlayer.getUUID().toString() + ".compass.waypoints",
                waypointStrings);

            plugin.getPlayerConfigManager().getConfig().set("players." + pcPlayer.getUUID().toString() + ".homes", null);
            for (String homeName : pcPlayer.getHomes().keySet()) {
                PCLocation homeLocation = pcPlayer.getHomes().get(homeName);
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".homes." + homeName + ".world",
                    homeLocation.getWorld());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".homes." + homeName + ".x",
                    homeLocation.getX());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".homes." + homeName + ".y",
                    homeLocation.getY());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".homes." + homeName + ".z",
                    homeLocation.getZ());
            }

            plugin.getPlayerConfigManager().getConfig().set("players." + pcPlayer.getUUID().toString() + ".mails", null);
            for (PCMail mail : pcPlayer.getMail()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                ZonedDateTime zonedDateTime = mail.getTimestamp().atZone(ZoneId.systemDefault());
                String formattedDateTime = formatter.format(zonedDateTime);

                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".mails." + formattedDateTime + ".sender",
                    mail.getSenderUUID().toString());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".mails." + formattedDateTime + ".title",
                    mail.getTitle());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".mails." + formattedDateTime + ".message",
                    mail.getMessage());
                plugin.getPlayerConfigManager().getConfig().set(
                    "players." + pcPlayer.getUUID().toString() + ".mails." + formattedDateTime + ".read",
                    mail.isRead());
            }
        }

        plugin.getPlayerConfigManager().saveConfig();
    }

    public PCPlayer getPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return getPlayer((Player) sender);
        }

        return null;
    }

    public PCPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId(), player.getName());
    }

    public PCPlayer getPlayer(UUID uuid, String name) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        }

        PCPlayer pcPlayer = new PCPlayer(uuid, name);
        pcPlayer.setCompassEnabled(
                plugin.getPluginConfigManager().getConfig().getBoolean("player.default.compass.enabled"));
        players.put(uuid, pcPlayer);
        return pcPlayer;
    }

    public PCPlayer getPlayer(UUID uniqueId) {
        if (!players.containsKey(uniqueId)) {
            return null;
        }
        return players.get(uniqueId);
    }

    public PCPlayer getPlayer(String player) {
        for (PCPlayer pcPlayer : players.values()) {
            if (pcPlayer.getUUID().toString().equalsIgnoreCase(player) || pcPlayer.getName().equalsIgnoreCase(player)) {
                return pcPlayer;
            }
        }
        return null;
    }

    public List<String> getPlayers() {
        List<String> playerNames = new ArrayList<>();
        for (PCPlayer pcPlayer : players.values()) {
            playerNames.add(pcPlayer.getName());
        }
        return playerNames;
    }

    public List<PCPlayer> getPCPlayers() {
        return new ArrayList<>(players.values());
    }

}
