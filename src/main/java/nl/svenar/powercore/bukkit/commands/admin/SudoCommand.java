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

@CommandAlias("sudo")
@Description("Execute a command as another player")
public class SudoCommand extends PowerBaseCommand {

    public SudoCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.sudo")
    @CommandCompletion("@players @nothing")
    public void onCommand(CommandSender sender, String playerName, String... actionList) {
        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            return;
        }

        String action = String.join(" ", actionList);
        plugin.getServer().dispatchCommand(targetPlayer, action);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Executed command as " + targetPlayer.getName() + "!");
    }

}