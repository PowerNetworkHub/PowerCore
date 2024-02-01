package nl.svenar.powercore.bukkit.modules.compass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.modules.general.PCLocation;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.modules.general.Waypoint;
import nl.svenar.powercore.common.utils.PowerColor;
import nl.svenar.powercore.common.utils.PowerMath;

public class CompassHandler {

    private String compassTemplate = "1 · · · · 22 · · · · 3 · · · · 44 · · · · 5 · · · · 66 · · · · 7 · · · · 88 · · · · ";

    private PowerCore plugin;
    private BukkitRunnable task;

    private Map<UUID, BossBar> bossBars = new HashMap<>();
    private Map<UUID, Location> locationCache = new HashMap<>();

    public CompassHandler(PowerCore plugin) {
        this.plugin = plugin;

    }

    public void start() {
        stop();

        task = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        };
        task.runTaskTimer(plugin, 1L, 1L);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }

        for (BossBar bossBar : bossBars.values()) {
            bossBar.removeAll();
        }
    }

    private void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);

            if (!pcPlayer.isCompassEnabled()) {
                if (bossBars.containsKey(player.getUniqueId())) {
                    bossBars.get(player.getUniqueId()).removeAll();
                    bossBars.remove(player.getUniqueId());
                }
                if (locationCache.containsKey(player.getUniqueId())) {
                    locationCache.remove(player.getUniqueId());
                }
                continue;
            }
            boolean forceUpdate = false;
            if (!locationCache.containsKey(player.getUniqueId())) {
                locationCache.put(player.getUniqueId(), player.getLocation());
                forceUpdate = true;
            }

            Location lastLocation = locationCache.get(player.getUniqueId());
            Location currentLocation = player.getLocation();
            if (lastLocation.distance(currentLocation) < 1 && lastLocation.getYaw() == currentLocation.getYaw()
                    && !forceUpdate) {
                continue;
            }
            locationCache.put(player.getUniqueId(), currentLocation);

            boolean bossbarExists = bossBars.containsKey(player.getUniqueId());
            BossBar bossBar = bossBars.computeIfAbsent(player.getUniqueId(),
                    uuid -> Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID));

            int yaw = Math.round((player.getLocation().getYaw() + 360) / 9);
            yaw = (int) PowerMath.map(yaw, 20, 60, compassTemplate.length() / 2,
                    compassTemplate.length() / 2 + compassTemplate.length());

            String compass = generateCompassString(player, pcPlayer, yaw);

            bossBar.setTitle(compass);
            if (!bossbarExists) {
                bossBar.setProgress(0);
                bossBar.addPlayer(player);
            }
        }
    }

    private String generateCompassString(Player player, PCPlayer pcPlayer, double rotation) {
        String compassTemplateWithWaypoints = addWaypoints(compassTemplate, player, pcPlayer, rotation);
        String output = compassTemplateWithWaypoints + compassTemplateWithWaypoints + compassTemplateWithWaypoints;

        if (rotation >= compassTemplate.length()) {
            rotation -= compassTemplate.length();
        }

        output = output.substring((int) rotation, (int) rotation + (int) Math.floor(compassTemplate.length() / 2) + 1);

        output = output.replace("7", PowerColor.ChatColor.WHITE + "N" + PowerColor.ChatColor.RESET);
        output = output.replace("1", PowerColor.ChatColor.WHITE + "E" + PowerColor.ChatColor.RESET);
        output = output.replace("22", PowerColor.ChatColor.GRAY + "SE" + PowerColor.ChatColor.RESET);
        output = output.replace(" 2", PowerColor.ChatColor.GRAY + " S" + PowerColor.ChatColor.RESET);
        output = output.replace("2 ", PowerColor.ChatColor.GRAY + "E " + PowerColor.ChatColor.RESET);
        output = output.replace("3", PowerColor.ChatColor.WHITE + "S" + PowerColor.ChatColor.RESET);
        output = output.replace("44", PowerColor.ChatColor.GRAY + "SW" + PowerColor.ChatColor.RESET);
        output = output.replace(" 4", PowerColor.ChatColor.GRAY + " S" + PowerColor.ChatColor.RESET);
        output = output.replace("4 ", PowerColor.ChatColor.GRAY + "W " + PowerColor.ChatColor.RESET);
        output = output.replace("5", PowerColor.ChatColor.WHITE + "W" + PowerColor.ChatColor.RESET);
        output = output.replace("66", PowerColor.ChatColor.GRAY + "NW" + PowerColor.ChatColor.RESET);
        output = output.replace(" 6", PowerColor.ChatColor.GRAY + " N" + PowerColor.ChatColor.RESET);
        output = output.replace("6 ", PowerColor.ChatColor.GRAY + "W " + PowerColor.ChatColor.RESET);
        output = output.replace("88", PowerColor.ChatColor.GRAY + "NE" + PowerColor.ChatColor.RESET);
        output = output.replace(" 8", PowerColor.ChatColor.GRAY + " N" + PowerColor.ChatColor.RESET);
        output = output.replace("8 ", PowerColor.ChatColor.GRAY + "E " + PowerColor.ChatColor.RESET);

        output = output.replaceAll("·", PowerColor.ChatColor.DARK_GRAY + "·" + PowerColor.ChatColor.RESET);

        return PowerColor.ChatColor.DARK_PURPLE + "[ " + PowerColor.ChatColor.RESET + output
                + PowerColor.ChatColor.DARK_PURPLE + " ]";
    }

    private String addWaypoints(String compass, Player player, PCPlayer pcPlayer, double rotation) {
        List<Character> directions = new ArrayList<Character>() {
            {
                add('N');
                add('E');
                add('S');
                add('W');
                add('\u00A7');
                add('0');
                add('1');
                add('2');
                add('3');
                add('4');
                add('5');
                add('6');
                add('7');
                add('8');
                add('9');
            }
        };

        for (Waypoint waypoint : pcPlayer.getWaypoints()) {
            PCLocation location = waypoint.getTargetLocation();
            if (location == null) {
                if (waypoint.getTargetPlayer() != null && waypoint.getTargetPlayer().isOnline()) {
                    Player targetPlayer = Bukkit.getPlayer(waypoint.getTargetPlayer().getUniqueId());
                    location = new PCLocation(targetPlayer.getLocation());
                }
            }
            if (location == null) {
                continue;
            }

            Location playerLoc = player.getLocation();

            if (!location.getWorld().equals(playerLoc.getWorld().getName())) {
                continue;
            }

            Vector target = location.toVector();

            playerLoc.setDirection(target.subtract(playerLoc.toVector()));
            int offset = 0;
            int yaw = Math.round((playerLoc.getYaw() + 360 + offset) / 9);
            yaw = (int) PowerMath.map(yaw, (360 + offset) / 9, (720 + offset) / 9, 0, compassTemplate.length());
            yaw = (yaw + compassTemplate.length() / 4) % compassTemplate.length();

            /////////////////
            // int width = (720 / 9) - (360 / 9);

            // if (rotation >= compassTemplate.length()) {
            //     rotation -= compassTemplate.length();
            // }

            // yaw = PowerMath.contsrain(yaw, (int) rotation, (int) rotation + width);
            /////////////////

            // int prevYaw = yaw;
            // if (yaw < rotation) {
            //     yaw = (int) rotation;
            // }
            // int yaw2 = yaw;
            // if (yaw2 < rotation) {
            //     yaw2 = (int) rotation;
            // }

            // System.out.print(waypoint.getName() + ": \t");
            // System.out.print(rotation + "\t");
            // System.out.print((rotation + width) + "\t");
            // System.out.print(yaw + " - " + yaw2 + "\t");
            // System.out.println(prevYaw);

            yaw = yaw < 0 ? 0 : yaw;
            yaw = yaw > compass.length() - 1 ? compass.length() - 1 : yaw;

            while (directions.contains(compass.charAt(yaw % compass.length()))) {
                yaw++;
                yaw = yaw % compass.length();
            }

            StringBuilder build = new StringBuilder(compass);

            build.setCharAt(yaw % compass.length(), '?');

            compass = build.toString();
        }

        return compass;
    }
}
