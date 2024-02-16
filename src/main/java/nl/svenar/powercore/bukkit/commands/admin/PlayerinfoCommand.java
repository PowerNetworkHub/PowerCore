package nl.svenar.powercore.bukkit.commands.admin;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

@CommandAlias("playerinfo")
@Description("Get information about a player")
public class PlayerinfoCommand extends PowerBaseCommand {

    public PlayerinfoCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.playerinfo")
    @CommandCompletion("@pcplayers")
    public void onCommand(CommandSender sender, String playerName) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        if (pcPlayer == null) {
            sendMessage(sender, "Player " + playerName + " not found.");
            return;
        }

        // OfflinePlayer offlinePlayer =
        // plugin.getServer().getOfflinePlayer(pcPlayer.getUUID());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ZonedDateTime zonedDateTime = pcPlayer.getLastSeen().atZone(ZoneId.systemDefault());
        String formattedDateTime = formatter.format(zonedDateTime);

        String lastLocationString = "unknown";
        if (pcPlayer.getLastLocation() != null) {
            lastLocationString = pcPlayer.getLastLocation().toString();
        }

        String logoutLocationString = "unknown";
        if (pcPlayer.getLogoutLocation() != null) {
            logoutLocationString = pcPlayer.getLogoutLocation().toString();
        }

        sendMessageRaw(sender, "");
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Information about " + pcPlayer.getName());
        sendMessage(sender, PowerColor.ChatColor.GREEN + repeatChar("-", 40));

        sendMessage(sender,
                PowerColor.ChatColor.DARK_GREEN + "UUID: " + PowerColor.ChatColor.GREEN + pcPlayer.getUUID());

        sendMessage(sender,
                PowerColor.ChatColor.DARK_GREEN + "Last seen: " + PowerColor.ChatColor.GREEN + formattedDateTime);

        sendMessage(sender,
                PowerColor.ChatColor.DARK_GREEN + "Online: " + PowerColor.ChatColor.GREEN
                        + (pcPlayer.isOnline() ? "yes" : "no"));

        sendMessage(sender, PowerColor.ChatColor.DARK_GREEN + "Banned: " + PowerColor.ChatColor.GREEN
                + (pcPlayer.isBanned() ? "yes (" + pcPlayer.getBanReason() + ")" : "no"));

        sendMessage(sender, PowerColor.ChatColor.DARK_GREEN + "Muted: " + PowerColor.ChatColor.GREEN
                + (pcPlayer.isMuted() ? "yes" : "no"));

        sendMessage(sender,
                PowerColor.ChatColor.DARK_GREEN + "Last /back location: " + PowerColor.ChatColor.GREEN
                        + lastLocationString);

        sendMessage(sender, PowerColor.ChatColor.DARK_GREEN + "Log-out location: " + PowerColor.ChatColor.GREEN
                + logoutLocationString);
    }
}
