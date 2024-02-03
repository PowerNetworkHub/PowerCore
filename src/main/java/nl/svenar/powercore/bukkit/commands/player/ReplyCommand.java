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

@CommandAlias("reply|r")
@Description("Reply to a player")
public class ReplyCommand extends PowerBaseCommand {

    public ReplyCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.msg")
    @CommandCompletion("@nothing")
    public void onCommand(CommandSender sender, String... messageList) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        if (messageList.length == 0) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "You must specify a message.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        String playerName = pcPlayer.getLastDirectMessageSender();
        if (playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "No player to reply to!");
            return;
        }

        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            pcPlayer.setLastDirectMessageSender(null);
            return;
        }

        String message = String.join(" ", messageList);
        String messageToSender = PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + "Me"
                + PowerColor.ChatColor.DARK_PURPLE + " -> " + PowerColor.ChatColor.GOLD + targetPlayer.getName()
                + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + message;
        String messageToTarget = PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + sender.getName()
                + PowerColor.ChatColor.DARK_PURPLE + " -> " + PowerColor.ChatColor.GOLD + "Me"
                + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + message;
        String messageToSpy = PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + sender.getName()
                + PowerColor.ChatColor.DARK_PURPLE + " -> " + PowerColor.ChatColor.GOLD + targetPlayer.getName()
                + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + message;

        sendMessageRaw(sender, messageToSender);
        sendMessageRaw(targetPlayer, messageToTarget);

        for (Player spyPlayer : plugin.getServer().getOnlinePlayers()) {
            if (spyPlayer.hasPermission("powercore.msg.spy") && spyPlayer != sender && spyPlayer != targetPlayer) {
                sendMessageRaw(spyPlayer, messageToSpy);
            }
        }
    }

}