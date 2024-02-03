package nl.svenar.powercore.bukkit.commands.admin;

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

@CommandAlias("kill")
@Description("Kill a player")
public class KillCommand extends PowerBaseCommand {

    public KillCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.kill")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, String playerName) {
        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            return;
        }

        targetPlayer.setHealth(0);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Killed " + targetPlayer.getName() + "!");
    }

}