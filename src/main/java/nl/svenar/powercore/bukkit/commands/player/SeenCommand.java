package nl.svenar.powercore.bukkit.commands.player;

import java.time.format.DateTimeFormatter;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
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
    public void onCommand(CommandSender sender, String playerName) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        if (pcPlayer.getLastSeen() == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "No history found for this player.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        sendMessage(sender, pcPlayer.getName() + " was last seen on " + formatter.format(pcPlayer.getLastSeen()));
    }

}