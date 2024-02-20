package nl.svenar.powercore.bukkit.commands.teleport.tpa;

import java.time.Instant;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("tpa")
@Description("Request to teleport to a player")
public class TPACommand extends PowerBaseCommand {

    public TPACommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandCompletion("@pcplayers")
    @CommandPermission("powercore.tpa")
    public void onCommand(CommandSender sender, String targetPlayer) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        Player target = plugin.getServer().getPlayer(targetPlayer);

        if (target == null) {
            sendMessage(player, PowerColor.ChatColor.RED + "Player not found.");
            return;
        }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            sendMessage(player, PowerColor.ChatColor.RED + "You can't teleport to yourself.");
            return;
        }

        if (plugin.getPCPlayerHandler().getPlayer(target.getUniqueId()).isAFK()) {
            sendMessage(player, PowerColor.ChatColor.RED + "Player is AFK.");
            return;
        }

        if (plugin.getPCPlayerHandler().getPlayer(target.getUniqueId()).getTpaBuffer()
                .containsKey(player.getUniqueId())) {
            sendMessage(player, PowerColor.ChatColor.RED + "You already have a pending request to this player.");
            return;
        }

        plugin.getPCPlayerHandler().getPlayer(target.getUniqueId()).getTpaBuffer().put(player.getUniqueId(),
                Instant.now());
        sendMessage(player, PowerColor.ChatColor.GREEN + "Teleport request sent to " + target.getName() + ".");
        sendMessage(target,
                PowerColor.ChatColor.GREEN + player.getName() + " has requested to teleport to you. Use "
                        + PowerColor.ChatColor.DARK_GREEN + "/tpaccept " + PowerColor.ChatColor.GREEN + "to accept or "
                        + PowerColor.ChatColor.DARK_GREEN + "/tpdeny " + PowerColor.ChatColor.GREEN + "to deny.");

    }
}
