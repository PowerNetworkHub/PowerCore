package nl.svenar.powercore.bukkit.commands.whitelist;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.MojangUtils;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("whitelist")
@Description("Remove a player from the whitelist")
public class WhitelistRemoveCommand extends PowerBaseCommand {

    public WhitelistRemoveCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("remove")
    @CommandPermission("powercore.whitelist")
    @CommandCompletion("@whitelistedplayers")
    public void onCommand(CommandSender sender, String playerName) {
        UUID uuid = MojangUtils.getUUIDFromAPI(playerName);

        if (uuid == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player " + playerName + " not found");
            return;
        }

        if (!plugin.getWhitelistConfigManager().getConfig().getStringList("whitelist.players")
                .contains(uuid.toString())) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player " + playerName + " is not whitelisted!");
            return;
        }

        List<String> whitelistedUUIDs = plugin.getWhitelistConfigManager().getConfig()
                .getStringList("whitelist.players");
        whitelistedUUIDs.remove(uuid.toString());
        plugin.getWhitelistConfigManager().getConfig().set("whitelist.players", whitelistedUUIDs);

        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Player " + playerName + " has been removed from the whitelist!");
    }
}
