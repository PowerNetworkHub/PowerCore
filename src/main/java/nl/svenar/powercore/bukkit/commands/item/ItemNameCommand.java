package nl.svenar.powercore.bukkit.commands.item;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.BukkitPowerColor;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("itemname")
@Description("Set the name of the item in your hand")
public class ItemNameCommand extends PowerBaseCommand {

    public ItemNameCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.itemname")
    public void onCommand(CommandSender sender, String... name) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(new BukkitPowerColor().format(PowerColor.UNFORMATTED_COLOR_CHAR, String.join(" ", name),
                true, false, false));
        item.setItemMeta(meta);
    }
}
