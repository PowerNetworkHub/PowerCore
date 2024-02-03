package nl.svenar.powercore.bukkit.commands.compass;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.modules.general.Waypoint;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("compass")
@Description("Remove a waypoint from a compass")
public class CompassDelWaypointCommand extends PowerBaseCommand {

    public CompassDelWaypointCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("delwaypoint")
    @CommandPermission("powercore.compass.waypoints")
    @CommandCompletion("@players waypointName")
    public void delWaypoint(CommandSender sender, String player, String waypointName) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player " + player + " not found.");
            return;
        }

        Waypoint waypoint = pcPlayer.getWaypoint(waypointName);
        if (waypoint == null) {
            sendMessage(sender,
                    PowerColor.ChatColor.RED + "Waypoint " + waypointName + " not found for player " + player
                            + "!");
            return;
        }

        pcPlayer.removeWaypoint(waypoint);
        sendMessage(sender, "Waypoint " + waypointName + " removed for player " + player + "!");
    }
}
