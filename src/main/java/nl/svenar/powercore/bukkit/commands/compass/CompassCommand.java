package nl.svenar.powercore.bukkit.commands.compass;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("compass")
@Description("Toggle the compass")
public class CompassCommand extends PowerBaseCommand {

    public CompassCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.compass")
    public void onCommand(CommandSender sender) {
        PCPlayer pcPlayer = plugin.getPCPlayerHandler().getPlayer(sender);

        pcPlayer.setCompassEnabled(!pcPlayer.isCompassEnabled());
        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "The compas has been "
                        + (pcPlayer.isCompassEnabled() ? PowerColor.ChatColor.DARK_GREEN + "enabled"
                                : PowerColor.ChatColor.DARK_RED + "disabled"));
    }
}
