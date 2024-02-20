package nl.svenar.powercore.bukkit.commands.teleport.tpa;

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

@CommandAlias("tpdeny|tpno|tpad")
@Description("Deny a teleport request")
public class TPDenyCommand extends PowerBaseCommand {

    public TPDenyCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Default
    @CommandCompletion("@nothing")
    @CommandPermission("powercore.tpa")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);

        if (pcPlayer.getTpaBuffer().isEmpty()) {
            sendMessage(player, PowerColor.ChatColor.RED + "No pending teleport requests.");
            return;
        }

        pcPlayer.getTpaBuffer().clear();
        sendMessage(player, PowerColor.ChatColor.GREEN + "Teleport request denied.");
    }
    
}
