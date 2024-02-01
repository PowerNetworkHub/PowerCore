package nl.svenar.powercore.bukkit.commands.player;

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

@CommandAlias("speedinfo")
@Description("Get the walk/fly speed for a player or yourself")
public class SpeedInfoCommand extends PowerBaseCommand {

    public SpeedInfoCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.speed")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, @Optional String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        if (sender != targetPlayer) {
            sendMessage(sender,
                    targetPlayer.getDisplayName() + "'s current walk speed: " + targetPlayer.getWalkSpeed() * 10);
            sendMessage(sender,
                    targetPlayer.getDisplayName() + "'s current fly speed: " + targetPlayer.getFlySpeed() * 10);

        } else {
            sendMessage(targetPlayer, "Current walk speed: " + targetPlayer.getWalkSpeed() * 10);
            sendMessage(targetPlayer, "Current fly speed: " + targetPlayer.getFlySpeed() * 10);
        }
    }
}