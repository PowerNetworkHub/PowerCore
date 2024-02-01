package nl.svenar.powercore.bukkit.commands.gamemode;

import org.bukkit.GameMode;
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

@CommandAlias("creative|gmc")
@Description("Set your or someone else's game mode to creative")
public class CreativeCommand extends PowerBaseCommand {

    public CreativeCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.gamemode")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, @Optional String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        Player targetPlayer = playerName != null ? plugin.getServer().getPlayer(playerName) : (Player) sender;
        targetPlayer.setGameMode(GameMode.CREATIVE);
        sendMessage(targetPlayer, "Set your game mode to creative!");
        if (targetPlayer != sender) {
            sendMessage(sender, "Set " + targetPlayer.getName() + "'s game mode to creative!");
        }
    }

}