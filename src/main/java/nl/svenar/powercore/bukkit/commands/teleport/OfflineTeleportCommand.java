package nl.svenar.powercore.bukkit.commands.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("offlineteleport|offlinetp|otp")
@Description("Teleport to an offline player")
public class OfflineTeleportCommand extends PowerBaseCommand {

    public OfflineTeleportCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.teleport")
    @CommandCompletion("@pcplayers")
    public void onCommand(CommandSender sender, String playerName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        PCPlayer targetPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        Player player = (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        if (targetPlayer.getLogoutLocation() == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player has no logout location.");
            return;
        }
        player.teleport(targetPlayer.getLogoutLocation().toLocation(player.getLocation()));

        sendMessage(player, "Teleported to " + targetPlayer.getName() + "'s last known location!");
    }

}