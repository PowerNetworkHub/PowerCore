package nl.svenar.powercore.bukkit.commands.mail;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCMail;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.utils.Util;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("mail")
@Description("Send mail to a player")
public class SendMailCommand extends PowerBaseCommand {

    public SendMailCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Subcommand("send")
    @CommandPermission("powercore.mail")
    @CommandCompletion("@pcplayers @nothing @nothing")
    public void onCommand(CommandSender sender, String target, String title, String[] message) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        if (target.equalsIgnoreCase(sender.getName())) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You can't send mail to yourself.");
            // return;
        }

        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(target);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Player not found.");
            return;
        }

        PCMail mail = new PCMail(((Player) sender).getUniqueId(), title, String.join(" ", message));
        pcPlayer.addMail(mail);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Mail sent to " + target + ".");

        Player targetPlayer = Util.getPlayer(pcPlayer);
        if (targetPlayer != null) {
            sendMessage(targetPlayer, PowerColor.ChatColor.GREEN + "You have new mail.");
        }
    }
    
}