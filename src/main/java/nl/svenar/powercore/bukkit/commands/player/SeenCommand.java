package nl.svenar.powercore.bukkit.commands.player;

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

@CommandAlias("seen")
@Description("Check when a player was last online")
public class SeenCommand extends PowerBaseCommand {

    public SeenCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.seen")
    @CommandCompletion("@pcplayers")
    public void onCommand(CommandSender sender, String playerName) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        if (pcPlayer.getLastSeen() == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "No history found for this player.");
            return;
        }
        boolean online = pcPlayer.isOnline();

        if (online) {
            sendMessage(sender, playerName + " is currently online!");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ZonedDateTime zonedDateTime = pcPlayer.getLastSeen().atZone(ZoneId.systemDefault());
            String formattedDateTime = formatter.format(zonedDateTime);
            
            sendMessage(sender, pcPlayer.getName() + " was last seen on " + formattedDateTime);
        }
    }

}