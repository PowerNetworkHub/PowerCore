package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.Util;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("burn")
@Description("Burn a player")
public class BurnCommand extends PowerBaseCommand {

    public BurnCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.burn")
    @CommandCompletion("@players @range:1-10")
    public void onCommand(CommandSender sender, String playerName, int duration) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        if (duration < 1) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Duration must be at least 1 second!");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        targetPlayer.setFireTicks(Util.TASK_TPS * duration);
        if (targetPlayer != sender) {
            sendMessage(sender, PowerColor.ChatColor.GREEN + "You have set " + targetPlayer.getName() + " on fire for "
                    + duration + " seconds!");
        }
    }

}