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
    @CommandCompletion("@players @players")
    public void onCommand(CommandSender sender, String playerName, @Optional String targetPlayerName) {
        if (!(sender instanceof Player) && targetPlayerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = targetPlayerName != null ? plugin.getServer().getPlayer(targetPlayerName)
                : plugin.getServer().getPlayer(playerName);
        Player player = targetPlayerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        player.teleport(targetPlayer.getLocation());
        sendMessage(player, "Teleported to " + targetPlayer.getName() + "!");
        if (targetPlayer != sender) {
            sendMessage(sender, "Teleported " + player.getName() + " to " + targetPlayer.getName() + "!");
        }
    }

    @Default
    @CommandPermission("powercore.teleport")
    @CommandCompletion("X Y Z")
    public void onCommand(CommandSender sender, double x, double y, double z) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = (Player) sender;
        Location location = targetPlayer.getLocation();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        targetPlayer.teleport(location);
        sendMessage(targetPlayer, "Teleported to " + x + ", " + y + ", " + z + "!");
    }

    @Default
    @CommandPermission("powercore.teleport")
    @CommandCompletion("@players X Y Z")
    public void onCommand(CommandSender sender, String playerName, double x, double y, double z) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        Player player = (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        Location location = targetPlayer.getLocation();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        player.teleport(location);
        sendMessage(player, "Teleported to " + x + ", " + y + ", " + z + "!");
        if (targetPlayer != sender) {
            sendMessage(sender, "Teleported " + player.getName() + " to " + x + ", " + y + ", " + z + "!");
        }
    }

}