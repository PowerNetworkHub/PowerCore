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
@Description("Enable the whitelist")
public class WhitelistEnableCommand extends PowerBaseCommand {

    public WhitelistEnableCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("enable")
    @CommandPermission("powercore.whitelist")
    public void onCommand(CommandSender sender) {
        if (plugin.getWhitelistConfigManager().getConfig().getBoolean("whitelist.enabled")) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Whitelist is already enabled!");
            return;
        }
        plugin.getWhitelistConfigManager().getConfig().set("whitelist.enabled", true);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Whitelist has been enabled!");
    }
}
