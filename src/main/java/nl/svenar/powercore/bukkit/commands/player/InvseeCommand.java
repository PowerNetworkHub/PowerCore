package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("invsee")
@Description("View the inventory of a player or yourself")
public class InvseeCommand extends PowerBaseCommand {

    public InvseeCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.invsee")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, @Optional String playerName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;

        if (playerName == null) {
            // View own inventory
            openInventory(player, player.getDisplayName() + "'s inventory", 45, player.getInventory());
        } else {
            // View other player's inventory
            Player targetPlayer = player.getServer().getPlayer(playerName);
            if (targetPlayer != null) {
                openInventory(player, targetPlayer.getDisplayName() + "'s inventory", 45, targetPlayer.getInventory());
            } else {
                sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            }
        }

    }

    private void openInventory(Player player, String title, int size, Inventory inventory) {
        // Create a copy of the inventory to prevent modification
        Inventory copyInventory = player.getServer().createInventory(null, size, title);
        copyInventory.setContents(inventory.getContents());

        // Open the copied inventory for the player
        player.openInventory(copyInventory);
    }

}