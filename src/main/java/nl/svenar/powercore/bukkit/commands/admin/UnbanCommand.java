package nl.svenar.powercore.bukkit.commands.admin;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("unban|pardon")
@Description("Unban a player")
public class UnbanCommand extends PowerBaseCommand {

    public UnbanCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.ban")
    @CommandCompletion("@bannedpcplayers")
    public void onCommand(CommandSender sender, String playerName) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        pcPlayer.setBanned(false);
        pcPlayer.setBanReason(null);
        
        sendMessage(sender, "Unbanned " + pcPlayer.getName() + "!");
    }

}