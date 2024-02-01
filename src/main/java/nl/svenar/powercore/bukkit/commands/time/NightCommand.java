package nl.svenar.powercore.bukkit.commands.time;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.MCTime;

@CommandAlias("night")
@Description("Set the time to night")
public class NightCommand extends PowerBaseCommand {

    public NightCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.time")
    public void onCommand(CommandSender sender) {
        plugin.getServer().getWorlds().forEach(world -> world.setTime(MCTime.NIGHT));
        sendMessage(sender, "Set the time to night!");
    }

}
