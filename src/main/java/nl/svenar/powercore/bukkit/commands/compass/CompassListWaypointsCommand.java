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
@Description("List waypoints on a compass")
public class CompassListWaypointsCommand extends PowerBaseCommand {

    public CompassListWaypointsCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("listwaypoints")
    @CommandPermission("powercore.compass.waypoints")
    @CommandCompletion("@players")
    public void listWaypoints(CommandSender sender, String player) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player " + player + " not found.");
            return;
        }

        if (pcPlayer.getWaypoints().isEmpty()) {
            sendMessage(sender, PowerColor.ChatColor.RED + "No waypoints found for player " + player + "!");
            return;
        }

        sendMessage(sender, "Waypoints for player " + player + ":");
        for (Waypoint waypoint : pcPlayer.getWaypoints()) {
            if (waypoint.getTargetPlayer() != null) {
                sendMessage(sender, " - " + waypoint.getName() + " (" + waypoint.getTargetPlayer().getName() + ")");
            } else {
                sendMessage(sender, " - " + waypoint.getName() + " (" + waypoint.getTargetLocation().getWorld() + ", "
                        + waypoint.getTargetLocation().getBlockX() + ", " + waypoint.getTargetLocation().getBlockY()
                        + ", "
                        + waypoint.getTargetLocation().getBlockZ() + ")");
            }
        }
    }
}
