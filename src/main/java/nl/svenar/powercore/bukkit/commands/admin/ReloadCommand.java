package nl.svenar.powercore.bukkit.commands.admin;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("%powercorecommand")
@Description("Reload parts of the plugin")
public class ReloadCommand extends PowerBaseCommand {

    public ReloadCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("reload")
    @CommandPermission("powercore.reload")
    @CommandCompletion("@reloadable")
    public void onCommand(CommandSender sender, String arg1) {
        if (arg1.equalsIgnoreCase("config")) {
            reloadConfig();
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Config reloaded!");

        } else if (arg1.equalsIgnoreCase("players")) {
            reloadPlayers();
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Players reloaded!");

        } else if (arg1.equalsIgnoreCase("spawn")) {
            reloadSpawn();
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Spawn config reloaded!");

        } else if (arg1.equalsIgnoreCase("warps")) {
            reloadWarps();
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Warps reloaded!");

        } else if (arg1.equalsIgnoreCase("whitelist")) {
            reloadWhitelist();
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Whitelist reloaded!");

        } else if (arg1.equalsIgnoreCase("all")) {
            reloadConfig();
            reloadPlayers();
            reloadSpawn();
            reloadWarps();
            reloadWhitelist();

            sendMessage(sender, PowerColor.ChatColor.GREEN + "Config reloaded!");
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Players reloaded!");
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Spawn config reloaded!");
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Warps reloaded!");
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Whitelist reloaded!");

        } else {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Invalid option to reload!");
        }
    }

    private void reloadConfig() {
        plugin.loadPluginConfig();
    }

    private void reloadPlayers() {
        plugin.loadPlayerConfig();
    }

    private void reloadSpawn() {
        plugin.loadSpawnConfig();
    }

    private void reloadWarps() {
        plugin.loadWarpConfig();
    }

    private void reloadWhitelist() {
        plugin.loadWhitelistConfig();
    }
}