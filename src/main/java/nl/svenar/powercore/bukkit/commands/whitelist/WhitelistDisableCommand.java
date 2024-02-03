package nl.svenar.powercore.bukkit.commands.whitelist;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("whitelist")
@Description("Disable the whitelist")
public class WhitelistDisableCommand extends PowerBaseCommand {

    public WhitelistDisableCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("disable")
    @CommandPermission("powercore.whitelist")
    public void onCommand(CommandSender sender) {
        if (!plugin.getWhitelistConfigManager().getConfig().getBoolean("whitelist.enabled")) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Whitelist is already disabled!");
            return;
        }
        plugin.getWhitelistConfigManager().getConfig().set("whitelist.enabled", false);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Whitelist has been disabled!");
    }
}
