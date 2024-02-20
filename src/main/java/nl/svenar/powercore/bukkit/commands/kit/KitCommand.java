package nl.svenar.powercore.bukkit.commands.kit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCKit;
import nl.svenar.powercore.bukkit.modules.general.KitManager.KitResult;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("kit")
@Description("Kit command")
public class KitCommand extends PowerBaseCommand {

    public KitCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("create")
    @CommandPermission("powercore.kit.admin")
    public void createKitCommand(CommandSender sender, String kitName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        if (plugin.getKitManager().kitExists(kitName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Kit already exists.");
            return;
        }

        Player player = (Player) sender;
        plugin.getKitManager().createKit(kitName, player.getInventory().getContents());
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Kit created with name " + kitName
                + " and the content of your inventory.");
    }

    @Subcommand("delete|remove")
    @CommandCompletion("@pckits @nothing")
    @CommandPermission("powercore.kit.admin")
    public void deleteKitCommand(CommandSender sender, String kitName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        if (!plugin.getKitManager().kitExists(kitName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Kit does not exist.");
            return;
        }

        plugin.getKitManager().deleteKit(kitName);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Kit deleted.");
    }

    @Subcommand("setcooldown")
    @CommandCompletion("@pckits Seconds @nothing")
    @CommandPermission("powercore.kit.admin")
    public void setCooldownKitCommand(CommandSender sender, String kitName, int cooldown) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        if (cooldown < 0) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Cooldown cannot be negative.");
            return;
        }

        if (!plugin.getKitManager().kitExists(kitName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Kit does not exist.");
            return;
        }

        plugin.getKitManager().setKitCooldown(kitName, cooldown);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Kit cooldown set to " + cooldown + " seconds.");
    }

    @Subcommand("list")
    @CommandPermission("powercore.kit")
    public void listKitCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;

        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Available kits (" + plugin.getKitManager().getKits().stream()
                        .filter(kit -> player.hasPermission("powercore.kit." + kit.getName())).count() + "):");
        for (PCKit kit : plugin.getKitManager().getKits()) {
            if (player.hasPermission("powercore.kit." + kit.getName())) {
                sendMessage(sender,
                        (kit.isOnCooldown(player.getUniqueId()) ? PowerColor.ChatColor.RED : PowerColor.ChatColor.GREEN)
                                + "- " + kit.getName());
            }
        }
    }

    @Subcommand("give|get")
    @CommandCompletion("@pckits @nothing")
    @CommandPermission("powercore.kit")
    public void giveKitCommand(CommandSender sender, String kitName) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        if (!plugin.getKitManager().kitExists(kitName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Kit does not exist.");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("powercore.kit." + kitName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You do not have permission to use this kit.");
            return;
        }

        KitResult result = plugin.getKitManager().giveKit(player, kitName);
        switch (result) {
            case KIT_NOT_FOUND:
                sendMessage(sender, PowerColor.ChatColor.RED + "Kit not found.");
                break;

            case KIT_NO_SPACE:
                sendMessage(sender, PowerColor.ChatColor.RED + "Not enough space in your inventory.");
                break;

            case KIT_COOLDOWN:
                sendMessage(sender, PowerColor.ChatColor.RED + "Kit is on cooldown.");
                break;

            case KIT_GIVEN:
                sendMessage(sender, PowerColor.ChatColor.GREEN + "Kit " + kitName + " given.");
                break;
        }
    }
}
