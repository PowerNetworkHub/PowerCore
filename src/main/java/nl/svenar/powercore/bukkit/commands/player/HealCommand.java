package nl.svenar.powercore.bukkit.commands.player;

import java.util.Objects;

import org.bukkit.attribute.Attribute;
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

@CommandAlias("heal")
@Description("Heal a player or yourself")
public class HealCommand extends PowerBaseCommand {

    public HealCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.heal")
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
        targetPlayer
                .setHealth(Objects.requireNonNull(targetPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        
        sendMessage(targetPlayer, PowerColor.ChatColor.GREEN + "Your health has been restored!");
        if (targetPlayer != sender) {
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Health of " + targetPlayer.getName() + " has been restored!");
        }
    }

}