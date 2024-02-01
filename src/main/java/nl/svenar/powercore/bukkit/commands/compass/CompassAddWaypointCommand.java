package nl.svenar.powercore.bukkit.commands.compass;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCLocation;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.modules.general.Waypoint;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("compass")
@Description("Add a waypoint to the compass")
public class CompassAddWaypointCommand extends PowerBaseCommand {

    public CompassAddWaypointCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("addwaypoint")
    @CommandPermission("powercore.compass.waypoints")
    @CommandCompletion("@players waypointName @players")
    public void addWaypoint(CommandSender sender, String player, String waypointName, String targetPlayer) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        PCPlayer pcTargetPlayer = plugin.getPCPlayerHandler().getPlayer(targetPlayer);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player " + player + " not found.");
            return;
        }
        if (pcTargetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player " + targetPlayer + " not found.");
            return;
        }
        Player targetPlayerObject = Bukkit.getServer().getPlayer(pcTargetPlayer.getUUID());

        Waypoint waypoint = new Waypoint(waypointName, targetPlayerObject);
        pcPlayer.addWaypoint(waypoint);

        sendMessage(sender, PowerColor.ChatColor.GREEN + "Waypoint " + waypointName + " added for player " + player
                + " to player " + targetPlayer + ".");
    }

    @Subcommand("addwaypoint")
    @CommandPermission("powercore.compass.waypoints")
    @CommandCompletion("@players waypointName X Y Z @worlds")
    public void addWaypoint(CommandSender sender, String player, String waypointName, double x, double y, double z,
            @Optional World world) {
        if (world == null) {
            world = Bukkit.getWorld(""); // Passing an empty string retrieves the default world
        }

        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player " + player + " not found.");
            return;
        }

        PCLocation location = new PCLocation(world.getName(), x, y, z);
        Waypoint waypoint = new Waypoint(waypointName, location);
        pcPlayer.addWaypoint(waypoint);

        sendMessage(sender, PowerColor.ChatColor.GREEN + "Waypoint " + waypointName + " added for player " + player
                + " to location " + location.toString() + ".");
    }
}
