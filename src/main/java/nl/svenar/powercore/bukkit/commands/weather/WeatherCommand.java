package nl.svenar.powercore.bukkit.commands.weather;

import org.bukkit.command.CommandSender;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;

@CommandAlias("weather")
@Description("Set the weather")
public class WeatherCommand extends PowerBaseCommand {

    public WeatherCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("powercore.weather")
    @CommandCompletion("clear|rain|thunder")
    public void onCommand(CommandSender sender, String weather) {
        switch (weather.toLowerCase()) {
            case "clear":
            case "sun":
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(false));
                plugin.getServer().getWorlds().forEach(world -> world.setThundering(false));
                sendMessage(sender, "Set the weather to sunny!");
                break;
            case "rain":
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(true));
                plugin.getServer().getWorlds().forEach(world -> world.setThundering(false));
                sendMessage(sender, "Set the weather to rain!");
                break;
            case "thunder":
            case "storm":
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(true));
                plugin.getServer().getWorlds().forEach(world -> world.setThundering(true));
                sendMessage(sender, "Set the weather to thunder!");
                break;
            default:
                sendMessage(sender, "Invalid weather type!");
                break;
        }
    }

}
