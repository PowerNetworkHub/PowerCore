package nl.svenar.powercore.bukkit.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("%powercorecommand")
@Description("Main PowerCore command")
public class MainCommand extends PowerBaseCommand {

    private List<String> lines = Arrays.asList( //
            "██████   █████  %name %version", //
            "██   ██ ██   ██ %author", //
            "██████  ██      %help", //
            "██      ██   ██ %web", //
            "██       █████  %donate" //
    );

    private List<String> linesThin = Arrays.asList( //
            "███   ██  %name %version", //
            "█  █ █  █ %author", //
            "███  █    %help", //
            "█    █  █ %web", //
            "█     ██  %donate" //
    );

    public MainCommand(PowerCore plugin) {
        super(plugin);
    }

    @Default
    public void onDefault(CommandSender sender) {
        for (String line : sender instanceof Player ? linesThin : lines) {
            line = line.replaceAll("█", PowerColor.ChatColor.DARK_PURPLE + "█");
            if (sender instanceof Player) {
                line = line.replaceAll(" ", PowerColor.ChatColor.BLACK + "█");

            }
            line = line.replace("%name", PowerColor.ChatColor.GREEN + plugin.getPluginName());

            line = line.replace("%version",
                    PowerColor.ChatColor.GREEN + "v" + plugin.getPluginVersion());

            line = line.replace("%author", PowerColor.ChatColor.GREEN + "Author"
                    + (this.plugin.getDescription().getAuthors().size() == 1 ? "" : "s") + ": "
                    + PowerColor.ChatColor.DARK_GREEN + String.join(" ", this.plugin.getDescription().getAuthors()));

            line = line.replace("%web", PowerColor.ChatColor.GREEN + "Website: " + PowerColor.ChatColor.DARK_GREEN
                    + "https://svenar.nl");

            line = line.replace("%donate",
                    PowerColor.ChatColor.YELLOW + "Donate: " + PowerColor.ChatColor.GOLD + "https://ko-fi.com/svenar");

            line = line.replace("%help",
                    PowerColor.ChatColor.GREEN + "Available commands: " + PowerColor.ChatColor.DARK_GREEN + "/pc help");

            sender.sendMessage(line);
        }
    }
}