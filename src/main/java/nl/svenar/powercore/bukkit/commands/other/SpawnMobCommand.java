package nl.svenar.powercore.bukkit.commands.other;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;
import org.bukkit.entity.EntityType;

@CommandAlias("spawnmob")
@Description("Spawn a mob at your location")
public class SpawnMobCommand extends PowerBaseCommand {

    public SpawnMobCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.spawnmob")
    @CommandCompletion("@mobs @range:1-10")
    public void onCommand(CommandSender sender, String mobName, @Optional String amountString) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        int amount = 0;
        try {
            if (amountString == null) {
                amount = 1;
            } else {
                amount = Integer.parseInt(amountString);
            }
        } catch (NumberFormatException e) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Amount must be a number!");
            return;
        }

        if (amount < 1) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Amount must be at least 1!");
            return;
        }

        if (amount > plugin.getPluginConfigManager().getConfig().getInt("command.spawnmob.limit")) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Amount must be at most "
                    + plugin.getPluginConfigManager().getConfig().getInt("command.spawnmob.limit") + "!");
            return;
        }

        Player player = (Player) sender;
        EntityType mob = null;

        try {
            mob = EntityType.valueOf(mobName.toUpperCase());
        } catch (IllegalArgumentException e) {
        }

        if (mob == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Invalid mob name.");
            return;
        }

        for (int i = 0; i < amount; i++) {
            player.getWorld().spawnEntity(player.getLocation(), mob);
        }

        sendMessage(sender, PowerColor.ChatColor.GREEN + "Spawned " + mobName + " at your location!");
    }

}