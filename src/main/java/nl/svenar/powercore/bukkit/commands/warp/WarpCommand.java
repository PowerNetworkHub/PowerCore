package nl.svenar.powercore.bukkit.commands.warp;

import org.bukkit.Location;
import org.bukkit.World;
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

@CommandAlias("warp")
@Description("Warp to a location")
public class WarpCommand extends PowerBaseCommand {

    public WarpCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.warp")
    @CommandCompletion("@warps")
    public void onCommand(CommandSender sender, String warpName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;

        if (!plugin.getWarpConfigManager().getConfig().contains("warps." + warpName)) {
            sendMessage(sender, "Warp " + warpName + " does not exist.");
            return;
        }

        World world = plugin.getServer()
                .getWorld(plugin.getWarpConfigManager().getConfig().getString("warps." + warpName + ".world"));
        double x = plugin.getWarpConfigManager().getConfig().getDouble("warps." + warpName + ".x");
        double y = plugin.getWarpConfigManager().getConfig().getDouble("warps." + warpName + ".y");
        double z = plugin.getWarpConfigManager().getConfig().getDouble("warps." + warpName + ".z");
        float yaw = (float) plugin.getWarpConfigManager().getConfig().getDouble("warps." + warpName + ".yaw");
        float pitch = (float) plugin.getWarpConfigManager().getConfig().getDouble("warps." + warpName + ".pitch");

        player.teleport(new Location(world, x, y, z, yaw, pitch));
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Teleported to warp " + warpName + "!");
    }

}