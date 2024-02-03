package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("enderchest|ec")
@Description("Open the enderchest of a player or yourself")
public class EnderchestCommand extends PowerBaseCommand {

    public EnderchestCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.enderechest")
    public void onCommand(CommandSender sender, @Optional String playerName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        if (playerName != null && !sender.hasPermission("powercore.enderchest.others")) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED
                    + "You do not have permission to open the enderchest of other players.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        Player player = (Player) sender;
        player.openInventory(targetPlayer.getEnderChest());
    }

}