package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("mute")
@Description("Mute a player")
public class MuteCommand extends PowerBaseCommand {

    public MuteCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.mute")
    @CommandCompletion("@pcplayers")
    public void onCommand(CommandSender sender, String targetPlayer) {
        PCPlayer player = plugin.getPCPlayerHandler().getPlayer(targetPlayer);
        if (player == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found");
            return;
        }

        if (player.isMuted()) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player is already muted");
            return;
        }

        player.setMuted(true);
        sendMessage(sender, PowerColor.ChatColor.GREEN + "Player " + player.getName() + " has been muted");
    }
}
