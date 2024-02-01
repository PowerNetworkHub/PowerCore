package nl.svenar.powercore.bukkit.commands.weather;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;

@CommandAlias("rain")
@Description("Set the weather to rain")
public class RainCommand extends PowerBaseCommand {

    public RainCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.weather")
    public void onCommand(CommandSender sender) {
        plugin.getServer().getWorlds().forEach(world -> world.setStorm(true));
        plugin.getServer().getWorlds().forEach(world -> world.setThundering(false));
        sendMessage(sender, "Set the weather to rain!");
    }

}