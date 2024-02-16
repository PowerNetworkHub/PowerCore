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
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("mail")
@Description("Delete mail from the mailbox")
public class DeleteMailCommand extends PowerBaseCommand {

    public DeleteMailCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Subcommand("delete")
    @CommandPermission("powercore.mail")
    @CommandCompletion("@pcmails")
    public void onCommand(CommandSender sender, String title) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);

        PCMail mail = pcPlayer.getMail(title);
        if (mail == null) {
            sendMessage(player, PowerColor.ChatColor.RED + "Mail not found.");
            return;
        }

        pcPlayer.removeMail(mail);
        sendMessage(player, PowerColor.ChatColor.GREEN + "Mail deleted.");
    }
    
}