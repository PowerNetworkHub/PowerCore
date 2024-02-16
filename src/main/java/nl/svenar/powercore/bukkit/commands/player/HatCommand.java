package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("hat")
@Description("Put an item on your head")
public class HatCommand extends PowerBaseCommand {

    public HatCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.hat")
    public void onHatCommand(Player player) {
        if (player.getInventory().getItemInMainHand() == null) {
            sendMessage(player,
                    PowerColor.ChatColor.DARK_RED + "You need to hold an item in your hand to put it on your head!");
            return;
        }
        ItemStack helmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
        player.getInventory().setItemInMainHand(helmet);

        sendMessage(player,
                PowerColor.ChatColor.GREEN + (player.getInventory().getHelmet() != null
                        ? (player.getInventory().getHelmet().getItemMeta().getDisplayName().length() > 0
                                ? player.getInventory().getHelmet().getItemMeta().getDisplayName()
                                : player.getInventory().getHelmet().getType().name().toLowerCase().replace("_", " "))
                        : "Nothing") + PowerColor.ChatColor.GREEN + " is now on your head!");
    }
}
