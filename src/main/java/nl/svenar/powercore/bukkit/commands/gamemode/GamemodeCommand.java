package nl.svenar.powercore.bukkit.commands.gamemode;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("gamemode|gm")
@Description("Change your gamemode")
public class GamemodeCommand extends PowerBaseCommand {

    public GamemodeCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandCompletion("survival|creative|adventure|spectator @players")
    public void onCommand(CommandSender sender, String gamemode, @Optional String playerName) {
        if (!(sender instanceof Player) && playerName == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be used by players.");
            return;
        }

        if (gamemode.equalsIgnoreCase("survival") || gamemode.equalsIgnoreCase("s") || gamemode.equalsIgnoreCase("0")) {
            gamemode = "survival";
        } else if (gamemode.equalsIgnoreCase("creative") || gamemode.equalsIgnoreCase("c")
                || gamemode.equalsIgnoreCase("1")) {
            gamemode = "creative";
        } else if (gamemode.equalsIgnoreCase("adventure") || gamemode.equalsIgnoreCase("a")
                || gamemode.equalsIgnoreCase("2")) {
            gamemode = "adventure";
        } else if (gamemode.equalsIgnoreCase("spectator") || gamemode.equalsIgnoreCase("sp")
                || gamemode.equalsIgnoreCase("3")) {
            gamemode = "spectator";
        } else {
            sendMessage(sender, "Invalid gamemode!");
            return;
        }

        if (!sender.hasPermission("gamemode." + gamemode.toLowerCase())) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "You don't have permission to use this gamemode.");
            return;
        }

        Player targetPlayer = playerName == null ? (Player) sender : plugin.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found.");
            return;
        }

        if (targetPlayer.getGameMode().toString().equalsIgnoreCase(gamemode)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player is already in this gamemode.");
            return;
        }

        targetPlayer.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
        sendMessage(targetPlayer, PowerColor.ChatColor.GREEN + "Gamemode set to " + gamemode + "!");
        if (targetPlayer != sender) {
            sendMessage(sender, PowerColor.ChatColor.GREEN + "Gamemode set to " + gamemode + " for "
                    + targetPlayer.getName() + "!");
        }
    }
}
