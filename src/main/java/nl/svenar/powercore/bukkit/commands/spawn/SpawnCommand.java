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

@CommandAlias("spawn")
@Description("Teleport to the spawn location")
public class SpawnCommand extends PowerBaseCommand {

    public SpawnCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.spawn")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be used by players.");
            return;
        }
        Player player = (Player) sender;
        if (plugin.getSpawnConfigManager().getConfig().get("spawn") != null) {
            String world = plugin.getSpawnConfigManager().getConfig().getString("spawn.world");
            double x = plugin.getSpawnConfigManager().getConfig().getDouble("spawn.x");
            double y = plugin.getSpawnConfigManager().getConfig().getDouble("spawn.y");
            double z = plugin.getSpawnConfigManager().getConfig().getDouble("spawn.z");
            float yaw = (float) plugin.getSpawnConfigManager().getConfig().getDouble("spawn.yaw");
            float pitch = (float) plugin.getSpawnConfigManager().getConfig().getDouble("spawn.pitch");
            Location location = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
            player.teleport(location);
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Teleported to spawn!");

        } else {
            sendMessage(sender, "No spawn location available!");
        }
    }

}