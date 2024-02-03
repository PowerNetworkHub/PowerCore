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
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("helpop")
@Description("Send a message to all online staff members")
public class HelpopCommand extends PowerBaseCommand {

    public HelpopCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.helpop")
    @CommandCompletion("@nothing")
    public void onCommand(CommandSender sender, String... messageList) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        if (messageList.length == 0) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "You must specify a message");
            return;
        }

        Player player = (Player) sender;
        String message = String.join(" ", messageList);

        plugin.getServer().getOnlinePlayers().stream().filter(p -> p.hasPermission("powercore.helpop.receive"))
                .forEach(p -> sendMessageRaw(p, PowerColor.ChatColor.DARK_GRAY + "[" + PowerColor.ChatColor.GOLD + "Helpop"
                        + PowerColor.ChatColor.DARK_GRAY + "] " + PowerColor.ChatColor.RESET + player.getName() + ": "
                        + message));
    }

}