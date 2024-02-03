package nl.svenar.powercore.bukkit.commands.player;

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

@CommandAlias("msg|whisper")
@Description("Send a message to a player")
public class MsgCommand extends PowerBaseCommand {

    public MsgCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.msg")
    @CommandCompletion("@players @nothing")
    public void onCommand(CommandSender sender, String playerName, String... messageList) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        if (messageList.length == 0) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "You must specify a message.");
            return;
        }

        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        PCPlayer targetPCPlayer = plugin.getPCPlayerHandler().getPlayer(targetPlayer);
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            return;
        }

        if (targetPCPlayer != null) {
            targetPCPlayer.setLastDirectMessageSender(sender.getName());
        }

        String message = String.join(" ", messageList);
        String messageToSender = PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + "Me"
                + PowerColor.ChatColor.DARK_PURPLE + " -> " + PowerColor.ChatColor.GOLD + targetPlayer.getName()
                + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + message;
        String messageToTarget = PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + sender.getName()
                + PowerColor.ChatColor.DARK_PURPLE + " -> " + PowerColor.ChatColor.GOLD + "Me"
                + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + message;

        sendMessageRaw(sender, messageToSender);
        sendMessageRaw(targetPlayer, messageToTarget);
    }

}