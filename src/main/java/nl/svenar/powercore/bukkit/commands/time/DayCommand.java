package nl.svenar.powercore.bukkit.commands.time;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.MCTime;

@CommandAlias("day")
@Description("Set the time to day")
public class DayCommand extends PowerBaseCommand {

    public DayCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.time")
    public void onCommand(CommandSender sender) {
        plugin.getServer().getWorlds().forEach(world -> world.setTime(MCTime.DAY));
        sendMessage(sender, "Set the time to day!");
    }

}
