package nl.svenar.powercore.bukkit.commands.home;

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
import nl.svenar.powercore.bukkit.modules.general.PCLocation;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("home")
@Description("Teleport to your home")
public class HomeCommand extends PowerBaseCommand {

    public HomeCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Default
    @CommandPermission("powercore.home")
    @CommandCompletion("@pchomes")
    public void onCommand(CommandSender sender, @Optional String homeName) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);

        if (pcPlayer.getHomes().isEmpty()) {
            sendMessage(player, PowerColor.ChatColor.RED + "You don't have any homes set.");
            return;
        }

        if (homeName == null) {
            if (pcPlayer.getHomes().size() > 1) {
                sendMessage(player, PowerColor.ChatColor.RED + "You have more than one home, please specify which one you want to teleport to.");
                sendMessage(player, PowerColor.ChatColor.YELLOW + " - " + String.join(" ", pcPlayer.getHomes().keySet()));
                return;
            }
            homeName = pcPlayer.getHomes().keySet().iterator().next();
        }

        PCLocation homeLocation = pcPlayer.getHomes().get(homeName);
        if (homeLocation == null) {
            sendMessage(player, PowerColor.ChatColor.RED + "Home " + homeName + " does not exist.");
            return;
        }

        player.teleport(homeLocation.toLocation(player.getLocation()));
        sendMessage(player, PowerColor.ChatColor.GREEN + "Teleported to home " + homeName);
    }
}
