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

@CommandAlias("speed")
@Description("Set the walk/fly speed for a player or yourself")
public class SpeedCommand extends PowerBaseCommand {

    public SpeedCommand(PowerCore plugin) {
        super(plugin);
    }

    // @Default
    // @CommandPermission("powercore.speed")
    // @CommandCompletion("@range:0.1-10")
    // public void onCommand(CommandSender sender, double speed) {
    //     if (!(sender instanceof Player)) {
    //         sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
    //         return;
    //     }

    //     Player targetPlayer = (Player) sender;

    //     if (speed < 0.1 || speed > 10) {
    //         sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Speed must be between 0.1 and 10.");
    //         return;
    //     }

    //     if (targetPlayer.isFlying()) {
    //         targetPlayer.setFlySpeed((float) speed / 10f);
    //         sendMessage(targetPlayer, "Fly speed set to " + speed + "!");
    //     } else {
    //         targetPlayer.setWalkSpeed((float) speed / 10f);
    //         sendMessage(targetPlayer, "Walk speed set to " + speed + "!");
    //     }
    // }

    @Default
    @CommandPermission("powercore.speed")
    @CommandCompletion("@range:1-10 @players")
    public void onCommand(CommandSender sender, double speed, @Optional String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        if (speed < 0.1 || speed > 10) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Speed must be between 0.1 and 10.");
            return;
        }

        if (targetPlayer.isFlying()) {
            targetPlayer.setFlySpeed((float) speed / 10f);
            sendMessage(targetPlayer, "Fly speed set to " + speed + "!");
            if (targetPlayer != sender) {
                sendMessage(sender, "Fly speed set to " + speed + " for " + targetPlayer.getName() + "!");
            }
        } else {
            targetPlayer.setWalkSpeed((float) speed / 10f);
            sendMessage(targetPlayer, "Walk speed set to " + speed + "!");
            if (targetPlayer != sender) {
                sendMessage(sender, "Walk speed set to " + speed + " for " + targetPlayer.getName() + "!");
            }
        }
    }

}