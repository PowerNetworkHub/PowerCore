package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("god")
@Description("Make a player or yourself invincible")
public class GodCommand extends PowerBaseCommand {

    public GodCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.god")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, @Optional String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }
        
        if (targetPlayer.isInvulnerable()) {
            targetPlayer.setInvulnerable(false);
            sendMessage(targetPlayer, PowerColor.ChatColor.GREEN + "You are no longer invincible!");
            if (targetPlayer != sender) {
                sendMessage(sender, PowerColor.ChatColor.GREEN + "Invincibility of " + targetPlayer.getName() + " has been removed!");
            }
        } else {
            targetPlayer.setInvulnerable(true);
            sendMessage(targetPlayer, PowerColor.ChatColor.GREEN + "You are now invincible!");
            if (targetPlayer != sender) {
                sendMessage(sender, PowerColor.ChatColor.GREEN + "Invincibility of " + targetPlayer.getName() + " has been enabled!");
            }
        }
    }

}