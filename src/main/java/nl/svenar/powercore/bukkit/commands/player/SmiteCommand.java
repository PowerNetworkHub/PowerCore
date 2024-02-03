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
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("smite")
@Description("Smite a player")
public class SmiteCommand extends PowerBaseCommand {

    public SmiteCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.smite")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
        if (targetPlayer != sender) {
            sendMessage(sender, PowerColor.ChatColor.GREEN + "You have smited " + targetPlayer.getName() + "!");
        }
    }

}