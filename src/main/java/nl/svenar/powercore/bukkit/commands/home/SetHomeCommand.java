package nl.svenar.powercore.bukkit.commands.home;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.modules.general.PCLocation;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("sethome")
@Description("Set your home")
public class SetHomeCommand extends HomeCommand {

    public SetHomeCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Default
    @CommandPermission("powercore.home")
    public void onCommand(CommandSender sender, String homeName) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        
        if (pcPlayer.hasHome(homeName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "A home with that name already exists.");
            return;
        }

        PCLocation location = new PCLocation(player.getLocation());
        pcPlayer.addHome(homeName, location);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Home " + homeName + " set.");
    }
}
