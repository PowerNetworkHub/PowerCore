package nl.svenar.powercore.bukkit.commands.other;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("enchant")
@Description("Enchant an item")
public class EnchantCommand extends PowerBaseCommand {

    public EnchantCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandCompletion("@enchantments")
    @CommandPermission("powercore.enchant")
    public void onCommand(CommandSender sender, String enchantmentName, @Optional Integer level) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "This command can only be used by players.");
            return;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            sendMessage(player, PowerColor.ChatColor.RED + "You need to hold an item in your hand.");
            return;
        }

        if (enchantmentName.equalsIgnoreCase("all")) {
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                item.addUnsafeEnchantment(enchantment, level == null ? 1 : (level > enchantment.getMaxLevel() ? enchantment.getMaxLevel() : level));
            }
            sendMessage(player, PowerColor.ChatColor.GREEN + "Item enchanted with all enchantments.");
            return;
        }

        NamespacedKey namespacedKey = NamespacedKey.minecraft(enchantmentName.toLowerCase());
        Enchantment enchantment = Registry.ENCHANTMENT.get(namespacedKey);
        if (enchantment == null) {
            sendMessage(player, PowerColor.ChatColor.RED + "Enchantment not found.");
            return;
        }

        if (level == null) {
            level = 1;
        }

        if (level < 1 || level > enchantment.getMaxLevel()) {
            sendMessage(player,
                    PowerColor.ChatColor.RED + "Invalid enchantment level. Please provide a level between 1 and "
                            + enchantment.getMaxLevel() + ".");
            return;
        }

        item.addUnsafeEnchantment(enchantment, level);
        sendMessage(player,
                PowerColor.ChatColor.GREEN + "Item enchanted with " + enchantment.getKey().getKey() + " at level " + level + ".");
    }
}
