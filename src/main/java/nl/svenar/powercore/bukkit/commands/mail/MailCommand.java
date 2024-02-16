package nl.svenar.powercore.bukkit.commands.mail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCMail;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("mail")
@Description("Show your mailbox")
public class MailCommand extends PowerBaseCommand {

    public MailCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.mail")
    public void onCommand(CommandSender sender) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);

        if (pcPlayer.getMail().size() == 0) {
            sendMessage(player, PowerColor.ChatColor.GREEN + "Your mailbox is empty.");
            return;
        }

        sendMessage(player, PowerColor.ChatColor.GREEN + "Your mailbox:");
        for (int i = 0; i < pcPlayer.getMail().size(); i++) {
            PCMail mail = pcPlayer.getMail().get(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ZonedDateTime zonedDateTime = mail.getTimestamp().atZone(ZoneId.systemDefault());
            String formattedDateTime = formatter.format(zonedDateTime);

            sendMessage(player, PowerColor.ChatColor.GRAY + "Mail #" + (i + 1) + " " + formattedDateTime + ": "
                    + (mail.isRead() ? PowerColor.ChatColor.GRAY : PowerColor.ChatColor.GREEN) + mail.getTitle());
        }
    }
}
