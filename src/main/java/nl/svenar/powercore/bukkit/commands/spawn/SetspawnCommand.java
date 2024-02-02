package nl.svenar.powercore.bukkit.commands.spawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("setspawn")
@Description("Set the spawn location")
public class SetspawnCommand extends PowerBaseCommand {

    public SetspawnCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.spawn.set")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be used by players.");
            return;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();
        plugin.getSpawnConfigManager().getConfig().set("spawn.world", location.getWorld().getName());
        plugin.getSpawnConfigManager().getConfig().set("spawn.x", location.getX());
        plugin.getSpawnConfigManager().getConfig().set("spawn.y", location.getY());
        plugin.getSpawnConfigManager().getConfig().set("spawn.z", location.getZ());
        plugin.getSpawnConfigManager().getConfig().set("spawn.yaw", location.getYaw());
        plugin.getSpawnConfigManager().getConfig().set("spawn.pitch", location.getPitch());
        plugin.getSpawnConfigManager().saveConfig();
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Spawn location set to your current location!");
    }

}