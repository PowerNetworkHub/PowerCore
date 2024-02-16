package nl.svenar.powercore.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.common.utils.PowerColor;

public class PowerBaseCommand extends BaseCommand {

    protected PowerCore plugin;

    public PowerBaseCommand(PowerCore plugin) {
        this.plugin = plugin;
    }

    protected int numTagsStartsWith(String[] tags, String search) {
        int count = 0;
        for (String tag : tags) {
            if (tag.toLowerCase().startsWith(search)) {
                count++;
            }
        }
        return count;
    }

    protected int numTagsEndsWith(String[] tags, String search) {
        int count = 0;
        for (String tag : tags) {
            if (tag.toLowerCase().endsWith(search)) {
                count++;
            }
        }
        return count;
    }

    protected void sendMessage(CommandSender sender, String message) {
        sendMessageRaw(sender, PowerColor.ChatColor.BLACK + "[" + PowerColor.ChatColor.DARK_PURPLE + "PC"
                + PowerColor.ChatColor.BLACK + "] " + PowerColor.ChatColor.WHITE + message);
    }

    protected void sendMessageRaw(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    protected String repeatChar(String character, int count) {
        return new String(new char[count]).replace("\0", character);
    }
}
