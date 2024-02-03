package nl.svenar.powercore.bukkit.commands.whitelist;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.utils.MojangUtils;
import nl.svenar.powercore.common.utils.PowerColor.ChatColor;

@CommandAlias("whitelist")
@Description("List all whitelisted players")
public class WhitelistListCommand extends PowerBaseCommand {

    public WhitelistListCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("list")
    @CommandPermission("powercore.whitelist")
    public void onCommand(CommandSender sender) {
        boolean whitelistEnabled = plugin.getWhitelistConfigManager().getConfig().getBoolean("whitelist.enabled");
        List<String> whitelistedUUIDs = plugin.getWhitelistConfigManager().getConfig()
                .getStringList("whitelist.players");
        sendMessage(sender, ChatColor.GREEN + "Whitelist is "
                + (whitelistEnabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.GREEN
                + " and contains " + ChatColor.WHITE + whitelistedUUIDs.size() + ChatColor.GREEN + " players");
        sendMessage(sender, "Whitelisted players:");
        for (String uuid : whitelistedUUIDs) {
            PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(UUID.fromString(uuid));
            String playerName = "Unknown player";
            if (pcPlayer != null) {
                playerName = pcPlayer.getName();
            } else {
                playerName = MojangUtils.getNameFromAPI(uuid);
            }
            sendMessage(sender,
                    ChatColor.GRAY + " - " + ChatColor.WHITE + playerName + ChatColor.GRAY + " ("
                            + ChatColor.WHITE + uuid + ChatColor.GRAY + ")");
        }
    }
}
