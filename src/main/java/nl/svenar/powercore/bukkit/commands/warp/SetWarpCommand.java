package nl.svenar.powercore.bukkit.commands.warp;

import org.bukkit.Location;
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

@CommandAlias("setwarp")
@Description("Set a warp location")
public class SetWarpCommand extends PowerBaseCommand {

    public SetWarpCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.warp.set")
    @CommandCompletion("WarpName")
    public void onCommand(CommandSender sender, String warpName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;

        if (plugin.getWarpConfigManager().getConfig().contains("warps." + warpName)) {
            sendMessage(sender, "Warp " + warpName + " already exists.");
            return;
        }

        Location location = player.getLocation();

        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".world", location.getWorld().getName());
        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".x", location.getX());
        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".y", location.getY());
        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".z", location.getZ());
        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".yaw", location.getYaw());
        plugin.getWarpConfigManager().getConfig().set("warps." + warpName + ".pitch", location.getPitch());

        plugin.getWarpConfigManager().saveConfig();

        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Warp " + warpName + " has been set to your current location!");
    }

}
