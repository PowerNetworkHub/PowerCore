package nl.svenar.powercore.bukkit.commands.item;

import java.util.ArrayList;
import java.util.List;

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

@CommandAlias("itemlore")
@Description("Set the lore of the item in your hand")
public class ItemLoreCommand extends PowerBaseCommand {

    public ItemLoreCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.itemlore")
    public void onCommand(CommandSender sender, String... lore) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = new ArrayList<>();
        for (String line : String.join(" ", lore).split("\\\\n")) {
            loreList.add(new BukkitPowerColor().format(PowerColor.UNFORMATTED_COLOR_CHAR, line, true, false, false));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

}
