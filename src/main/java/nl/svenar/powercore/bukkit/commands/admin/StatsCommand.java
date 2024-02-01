package nl.svenar.powercore.bukkit.commands.admin;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("%powercorecommand")
@Description("Show stats about PowerCore")
public class StatsCommand extends PowerBaseCommand {

    public StatsCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("stats")
    @CommandPermission("powercore.stats")
    public void onCommand(CommandSender sender) {
        messageStats(sender);
    }

    private void messageStats(CommandSender sender) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Instant current_time = Instant.now();

        sender.sendMessage(PowerColor.ChatColor.LIGHT_PURPLE + "=== " + PowerColor.ChatColor.DARK_PURPLE
                + "PowerCore Stats " + PowerColor.ChatColor.LIGHT_PURPLE + "===");
        sender.sendMessage(PowerColor.ChatColor.GREEN + "Server version: " + PowerColor.ChatColor.DARK_GREEN
                + Bukkit.getVersion() + " | " + Bukkit.getServer().getBukkitVersion());
        sender.sendMessage(PowerColor.ChatColor.GREEN + "Java version: " + PowerColor.ChatColor.DARK_GREEN
                + System.getProperty("java.version"));
        sender.sendMessage(PowerColor.ChatColor.GREEN + "Uptime: " + PowerColor.ChatColor.DARK_GREEN
                + format.format(Duration.between(plugin.getStartupTime(), current_time).toMillis()));
        sender.sendMessage(PowerColor.ChatColor.GREEN + "PowerCore Version: " + PowerColor.ChatColor.DARK_GREEN
                + plugin.getDescription().getVersion());

        boolean hex_color_supported = false;
        try {
            "#FF0000a".replace("#FF0000", net.md_5.bungee.api.ChatColor.of("#FF0000") + "");
            hex_color_supported = true;
        } catch (Exception | NoSuchMethodError e) {
            hex_color_supported = false;
        }
        sender.sendMessage(PowerColor.ChatColor.GREEN + "RGB colors: "
                + (hex_color_supported ? PowerColor.ChatColor.DARK_GREEN + "" : PowerColor.ChatColor.DARK_RED + "un")
                + "supported");

        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        String pluginNames = "";
        for (Plugin plugin : plugins) {
            pluginNames += plugin.getName() + "(" + plugin.getDescription().getVersion() + "), ";
        }
        pluginNames = pluginNames.substring(0, pluginNames.length() - 2);

        sender.sendMessage(PowerColor.ChatColor.GREEN + "Plugins (" + plugins.length + "): "
                + PowerColor.ChatColor.DARK_GREEN + pluginNames);

        sender.sendMessage(PowerColor.ChatColor.LIGHT_PURPLE + "=======================");
    }
}
