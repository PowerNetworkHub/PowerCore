package nl.svenar.powercore.bukkit.commands.time;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("time")
@Description("Set the time")
public class TimeSetCommand extends PowerBaseCommand {

    public TimeSetCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("set")
    @CommandPermission("powercore.time")
    @CommandCompletion("@time")
    public void onCommand(CommandSender sender, long time) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "This command can only be executed by a player.");
            return;
        }

        plugin.getServer().getWorlds().forEach(world -> world.setTime(time));
        sendMessage(sender, "Set the time to: " + time);

    }

}
