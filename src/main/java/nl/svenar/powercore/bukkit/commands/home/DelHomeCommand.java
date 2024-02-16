package nl.svenar.powercore.bukkit.commands.home;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("delhome")
@Description("Delete a home")
public class DelHomeCommand extends PowerBaseCommand {

    public DelHomeCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Default
    @CommandPermission("powercore.home")
    @CommandCompletion("@pchomes")
    public void onCommand(CommandSender sender, String homeName) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "You must be a player to use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        
        if (!pcPlayer.hasHome(homeName)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "A home with that name does not exist.");
            return;
        }

        pcPlayer.removeHome(homeName);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Home " + homeName + " removed.");
    }
}
