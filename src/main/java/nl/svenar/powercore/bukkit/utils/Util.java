package nl.svenar.powercore.bukkit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import nl.svenar.powercore.bukkit.modules.general.PCPlayer;

public class Util {

    public static int TASK_TPS = 20;

    public static String getServerVersion(Server server) {
        try {
            Matcher matcher = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}").matcher(server.getVersion());

            List<String> results = new ArrayList<String>();
            while (matcher.find()) {
                if (matcher.groupCount() > 0) {
                    results.add(matcher.group(1));
                } else {
                    results.add(matcher.group());
                }
            }

            return results.get(0);
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static String getServerType(Server server) {
        try {
            Matcher matcher = Pattern.compile("-\\w{1,32}-").matcher(server.getVersion());

            List<String> results = new ArrayList<String>();
            while (matcher.find()) {
                if (matcher.groupCount() > 0) {
                    results.add(matcher.group(1));
                } else {
                    results.add(matcher.group());
                }
            }

            return results.get(0).replaceAll("-", "");
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static Player getPlayer(String identifier) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(identifier) || player.getUniqueId().toString().equalsIgnoreCase(identifier)) {
                return player;
            }
        }

        return null;
    }

    public static Player getPlayer(PCPlayer pcPlayer) {
        if (pcPlayer == null) {
            return null;
        }
        return getPlayer(pcPlayer.getUUID().toString());
    }

    public static Player getPlayer(Object object) {
        if (object instanceof Player) {
            return (Player) object;
        } else if (object instanceof PCPlayer) {
            return getPlayer((PCPlayer) object);
        } else if (object instanceof String) {
            return getPlayer((String) object);
        }

        return null;
    }
}
