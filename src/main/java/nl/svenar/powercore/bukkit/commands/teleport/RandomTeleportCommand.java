package nl.svenar.powercore.bukkit.commands.teleport;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("rtp")
@Description("Teleport to a random location")
public class RandomTeleportCommand extends PowerBaseCommand {

    private final Random random = new Random();
    private Map<UUID, Instant> rtpBuffer = new HashMap<>();

    public RandomTeleportCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.rtp")
    public void onRTP(Player player) {
        teleportRandomly(player);
    }

    @Subcommand("minradius")
    @CommandCompletion("@range:0-10000 @nothing")
    @CommandPermission("powercore.rtp.admin")
    public void setMinRadius(CommandSender sender, int radius) {
        int maxRadius = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.max_distance", 10000);
        if (radius > maxRadius) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Min radius can't be higher than max radius.");
            return;
        }
        plugin.getPluginConfigManager().getConfig().set("teleport.random.min_distance", radius);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Min radius set to " + radius);
    }

    @Subcommand("maxradius")
    @CommandCompletion("@range:0-10000 @nothing")
    @CommandPermission("powercore.rtp.admin")
    public void setMaxRadius(CommandSender sender, int radius) {
        int minRadius = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.min_distance", 10000);
        if (radius < minRadius) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Max radius can't be lower than min radius.");
            return;
        }
        plugin.getPluginConfigManager().getConfig().set("teleport.random.max_distance", radius);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Max radius set to " + radius);
    }

    @Subcommand("setcenter")
    @CommandCompletion("@range:-10000-10000|X @range:-10000-10000|Z @nothing")
    @CommandPermission("powercore.rtp.admin")
    public void setCenter(CommandSender sender, int x, int z) {
        plugin.getPluginConfigManager().getConfig().set("teleport.random.center.x", x);
        plugin.getPluginConfigManager().getConfig().set("teleport.random.center.z", z);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Center set to " + x + ", " + z);
    }

    @Subcommand("settimeout")
    @CommandPermission("powercore.rtp.admin")
    public void setTimeout(CommandSender sender, int timeout) {
        if (timeout < 0) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Timeout can't be lower than 0.");
            return;
        }
        plugin.getPluginConfigManager().getConfig().set("teleport.random.timeout", timeout);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Timeout set to " + timeout + "s");
    }

    private void teleportRandomly(Player player) {
        int minRadius = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.min_distance", 1000);
        int maxRadius = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.max_distance", 10000);
        int centerX = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.center.x", 0);
        int centerZ = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.center.z", 0);
        int teleportTimeout = plugin.getPluginConfigManager().getConfig().getInt("teleport.random.timeout", 300);

        if (rtpBuffer.containsKey(player.getUniqueId())) {
            Instant lastRTP = rtpBuffer.get(player.getUniqueId());
            if (Instant.now().isBefore(lastRTP.plusSeconds(teleportTimeout))) {
                sendMessage(player, PowerColor.ChatColor.RED + "You can only use /rtp every " + teleportTimeout
                        + "s (cooldown: "
                        + (lastRTP.plusSeconds(teleportTimeout).getEpochSecond() - Instant.now().getEpochSecond())
                        + "s)");
                return;
            }
        }

        rtpBuffer.put(player.getUniqueId(), Instant.now());

        int x = random.nextInt(maxRadius - minRadius) + minRadius;
        int z = random.nextInt(maxRadius - minRadius) + minRadius;

        x = random.nextBoolean() ? x : -x;
        z = random.nextBoolean() ? z : -z;

        Location randomLocation = player.getLocation().clone();
        randomLocation.setX(centerX + x);
        randomLocation.setZ(centerZ + z);
        randomLocation.setY(player.getWorld().getHighestBlockYAt(randomLocation) + 1);

        player.teleport(randomLocation);
        sendMessage(player, PowerColor.ChatColor.GREEN + "Teleported to a random location.");
    }
}