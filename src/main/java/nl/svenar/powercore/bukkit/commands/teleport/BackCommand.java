package nl.svenar.powercore.bukkit.commands.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("back")
@Description("Return to your previous location")
public class BackCommand extends PowerBaseCommand {

    public BackCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.back")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(targetPlayer);
        if (pcPlayer.getLastLocation() == null) {
            sendMessage(targetPlayer, PowerColor.ChatColor.DARK_RED + "No previous location found.");
            return;
        }
        targetPlayer.teleport(pcPlayer.getLastLocation().toLocation(targetPlayer.getLocation()));
        sendMessage(targetPlayer, "Returned to your previous location!");
    }

}