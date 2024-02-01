package nl.svenar.powercore.bukkit.commands.admin;

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

@CommandAlias("kick")
@Description("Kick a player")
public class KickCommand extends PowerBaseCommand {

    public KickCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.kick")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, String playerName, @Optional String reason) {
        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        targetPlayer.kickPlayer(reason != null ? reason : "You have been kicked from the server.");
        sendMessage(sender, "Kicked " + targetPlayer.getName() + "!");
    }

}