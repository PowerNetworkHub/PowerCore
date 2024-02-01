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

@CommandAlias("fly")
@Description("Set fly mode for a player or yourself")
public class FlyCommand extends PowerBaseCommand {

    public FlyCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.fly")
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
        targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());
        sendMessage(targetPlayer, "Fly mode " + (targetPlayer.getAllowFlight() ? "enabled" : "disabled") + "!");
        if (targetPlayer != sender) {
            sendMessage(sender, "Fly mode " + (targetPlayer.getAllowFlight() ? "enabled" : "disabled") + " for "
                    + targetPlayer.getName() + "!");
        }
    }

}