package nl.svenar.powercore.bukkit.commands.mail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
@Description("Read mail from the mailbox")
public class ReadMailCommand extends PowerBaseCommand {

    public ReadMailCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("read")
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ZonedDateTime zonedDateTime = mail.getTimestamp().atZone(ZoneId.systemDefault());
        String formattedDateTime = formatter.format(zonedDateTime);

        sendMessage(player, PowerColor.ChatColor.GREEN + "Mail from "
                + plugin.getServer().getOfflinePlayer(mail.getSenderUUID()).getName() + ":");
        sendMessage(player, PowerColor.ChatColor.GRAY + "Timestamp: " + PowerColor.ChatColor.WHITE + formattedDateTime);
        sendMessage(player, PowerColor.ChatColor.GRAY + "Title: " + PowerColor.ChatColor.WHITE + mail.getTitle());
        sendMessage(player, PowerColor.ChatColor.GRAY + "Message: " + PowerColor.ChatColor.WHITE + mail.getMessage());

        if (!mail.isRead()) {
            mail.setRead(true);
            sendMessage(player, PowerColor.ChatColor.GREEN + "Mail marked as read.");
        }
    }

}