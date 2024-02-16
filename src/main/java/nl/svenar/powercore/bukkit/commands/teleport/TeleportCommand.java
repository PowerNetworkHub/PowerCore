package nl.svenar.powercore.bukkit.commands.teleport;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("teleport|tp")
@Description("Teleport to a player")
public class TeleportCommand extends PowerBaseCommand {

    public TeleportCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.teleport")
    @CommandCompletion("@players|X @players|X|Y Y|Z Z")
    public void onCommand(CommandSender sender, String arg1, @Optional String arg2, @Optional String arg3, @Optional String arg4) {
        // Sender to coordinates
        if (arg1 != null && arg2 != null && arg3 != null && arg4 == null) {
            try {
                Player player = (Player) sender;

                Location targetLocation = player.getLocation();
                targetLocation.setX(Double.parseDouble(arg1));
                targetLocation.setY(Double.parseDouble(arg2));
                targetLocation.setZ(Double.parseDouble(arg3));

                player.teleport(targetLocation);
                sendMessage(player, "Teleported to " + targetLocation.getBlockX() + ", " + targetLocation.getBlockY() + ", " + targetLocation.getBlockZ() + "!");
            } catch (NumberFormatException e) {
                if (!(sender instanceof Player)) {
                    sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
                    return;
                }

                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Invalid coordinates.");
                return;
            }
        }

        // Player to coordinates
        if (arg1 != null && arg2 != null && arg3 != null && arg4 != null) {
            Player targetPlayer = plugin.getServer().getPlayer(arg1);
            if (targetPlayer == null) {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
                return;
            }

            try {
                Location targetLocation = targetPlayer.getLocation();
                targetLocation.setX(Double.parseDouble(arg2));
                targetLocation.setY(Double.parseDouble(arg3));
                targetLocation.setZ(Double.parseDouble(arg4));

                targetPlayer.teleport(targetLocation);
                sendMessage(targetPlayer, "Teleported " + targetPlayer.getName() + " to " + targetLocation.getBlockX() + ", " + targetLocation.getBlockY() + ", " + targetLocation.getBlockZ() + "!");
                if (targetPlayer != sender) {
                    sendMessage(sender, "Teleported " + targetPlayer.getName() + " to " + targetLocation.getBlockX() + ", " + targetLocation.getBlockY() + ", " + targetLocation.getBlockZ() + "!");
                }
            } catch (NumberFormatException e) {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Invalid coordinates.");
                return;
            }
        }

        // Sender to player
        if (arg1 != null && arg2 == null && arg3 == null && arg4 == null) {
            Player targetPlayer = plugin.getServer().getPlayer(arg1);
            if (targetPlayer == null) {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
                return;
            }

            Player player = (Player) sender;
            player.teleport(targetPlayer.getLocation());
            sendMessage(player, "Teleported to " + targetPlayer.getName() + "!");
        }

        // Player to player
        if (arg1 != null && arg2 != null && arg3 == null && arg4 == null) {
            Player player = plugin.getServer().getPlayer(arg1);
            if (player == null) {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
                return;
            }

            Player targetPlayer = plugin.getServer().getPlayer(arg2);
            if (targetPlayer == null) {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Target player not found.");
                return;
            }

            player.teleport(targetPlayer.getLocation());
            sendMessage(player, "Teleported to " + targetPlayer.getName() + "!");
            if (targetPlayer != sender) {
                sendMessage(sender, "Teleported " + player.getName() + " to " + targetPlayer.getName() + "!");
            }
        }

        // if (!(sender instanceof Player) && arg2 == null) {
        //     sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
        //     return;
        // }

        // Player targetPlayer = targetPlayerName != null ? plugin.getServer().getPlayer(targetPlayerName)
        //         : plugin.getServer().getPlayer(playerName);
        // Player player = targetPlayerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        // if (targetPlayer == null) {
        //     sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
        //     return;
        // }
        // player.teleport(targetPlayer.getLocation());
        // sendMessage(player, "Teleported to " + targetPlayer.getName() + "!");
        // if (targetPlayer != sender) {
        //     sendMessage(sender, "Teleported " + player.getName() + " to " + targetPlayer.getName() + "!");
        // }
    }

    // @Default
    // @CommandPermission("powercore.teleport")
    // @CommandCompletion("X Y Z")
    // public void onCommand(CommandSender sender, double x, double y, double z) {
    //     if (!(sender instanceof Player)) {
    //         sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
    //         return;
    //     }

    //     Player targetPlayer = (Player) sender;
    //     Location location = targetPlayer.getLocation();
    //     location.setX(x);
    //     location.setY(y);
    //     location.setZ(z);
    //     targetPlayer.teleport(location);
    //     sendMessage(targetPlayer, "Teleported to " + x + ", " + y + ", " + z + "!");
    // }

    // @Default
    // @CommandPermission("powercore.teleport")
    // @CommandCompletion("@players X Y Z")
    // public void onCommand(CommandSender sender, String playerName, double x, double y, double z) {
    //     if (!(sender instanceof Player)) {
    //         sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
    //         return;
    //     }

    //     Player targetPlayer = plugin.getServer().getPlayer(playerName);
    //     Player player = (Player) sender;
    //     if (targetPlayer == null) {
    //         sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
    //         return;
    //     }
    //     Location location = targetPlayer.getLocation();
    //     location.setX(x);
    //     location.setY(y);
    //     location.setZ(z);
    //     player.teleport(location);
    //     sendMessage(player, "Teleported to " + x + ", " + y + ", " + z + "!");
    //     if (targetPlayer != sender) {
    //         sendMessage(sender, "Teleported " + player.getName() + " to " + x + ", " + y + ", " + z + "!");
    //     }
    // }

}