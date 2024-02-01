package nl.svenar.powercore.bukkit.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("ban")
@Description("Ban a player")
public class BanCommand extends PowerBaseCommand {

    public BanCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.ban")
    @CommandCompletion("@pcplayers")
    public void onCommand(CommandSender sender, String playerName, @Optional String reason) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(playerName);
        if (pcPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        if (reason == null) {
            reason = "You have been banned from the server.";
        }

        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        if (targetPlayer != null) {
            targetPlayer.kickPlayer(reason);
        }

        pcPlayer.setBanned(true);
        pcPlayer.setBanReason(reason);

        sendMessage(sender, "Banned " + pcPlayer.getName() + "!");
    }

}