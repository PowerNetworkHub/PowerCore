package nl.svenar.powercore.bukkit.commands.player;

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

@CommandAlias("afk")
@Description("Toggle AFK status")
public class AFKCommand extends PowerBaseCommand {

    public AFKCommand(PowerCore plugin) {
        super(plugin);
    }
    
    @Default
    @CommandPermission("powercore.afk")
    @CommandCompletion("@nothing")
    public void onCommand(CommandSender sender) {
        if (!isPlayer(sender)) {
            sendMessage(sender, PowerColor.ChatColor.RED + "This command can only be used by players.");
            return;
        }

        Player player = (Player) sender;
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(player);
        plugin.getAFKManager().setAFK(pcPlayer, !pcPlayer.isAFK());
    }
}
