package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("clearinventory|clearinv|clear|ci")
@Description("emove all items from your inventory")
public class ClearInventoryCommand extends PowerBaseCommand {

    public ClearInventoryCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.clearinventory")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "This command can only be executed by a player.");
            return;
        }

        Player player = (Player) sender;
        player.getInventory().clear();
        sendMessage(player, PowerColor.ChatColor.GREEN + "Your inventory has been cleared!");
    }

}
