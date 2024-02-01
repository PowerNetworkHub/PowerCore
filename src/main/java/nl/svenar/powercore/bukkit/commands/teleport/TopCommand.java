package nl.svenar.powercore.bukkit.commands.teleport;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("top")
@Description("Teleport to the highest block")
public class TopCommand extends PowerBaseCommand {

    public TopCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.teleport")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = (Player) sender;
        Location location = targetPlayer.getLocation();
        location.setY(targetPlayer.getWorld().getHighestBlockYAt(location) + 1.0);
        targetPlayer.teleport(location);
        sendMessage(targetPlayer, "Teleported to the highest block!");
    }

}