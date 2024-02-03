package nl.svenar.powercore.bukkit.commands.admin;

import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.common.utils.PowerColor;

@CommandAlias("killall")
@Description("Kill all players / mobs / entities")
public class KillallCommand extends PowerBaseCommand {

    public KillallCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("all")
    @CommandPermission("powercore.killall")
    public void onAllCommand(CommandSender sender) {
        int countPlayers = 0;
        int countMobs = 0;
        int countEntities = 0;
        for (Entity entity : plugin.getServer().getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .collect(Collectors.toList())) {

            if (entity instanceof Player) {
                countPlayers++;
                ((Player) entity).setHealth(0);

            } else if (entity instanceof Mob) {
                countMobs++;
                ((Mob) entity).setHealth(0);

            } else {
                countEntities++;
                entity.remove();
            }
        }

        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Killed " + countPlayers + " player" + (countPlayers == 1 ? "" : "s")
                        + ", " + countMobs
                        + " mob" + (countMobs == 1 ? "" : "s") + " and " + countEntities + " entit"
                        + (countEntities == 1 ? "y" : "ies") + "!");
    }

    @Subcommand("players")
    @CommandPermission("powercore.killall")
    public void onPlayersCommand(CommandSender sender) {
        int count = 0;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            count++;
            player.setHealth(0);
        }

        sendMessage(sender, PowerColor.ChatColor.GREEN + "Killed " + count + " player" + (count == 1 ? "" : "s") + "!");
    }

    @Subcommand("mobs")
    @CommandPermission("powercore.killall")
    public void onMobsCommand(CommandSender sender) {
        int count = 0;
        for (Entity entity : plugin.getServer().getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .filter(entity -> entity instanceof Mob)
                .collect(Collectors.toList())) {
            count++;
            ((Mob) entity).setHealth(0);
        }

        sendMessage(sender, PowerColor.ChatColor.GREEN + "Killed " + count + " mob" + (count == 1 ? "" : "s") + "!");
    }

    @Subcommand("entities")
    @CommandPermission("powercore.killall")
    public void onEntitiesCommand(CommandSender sender) {
        int count = 0;
        for (Entity entity : plugin.getServer().getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .filter(entity -> !(entity instanceof Player))
                .filter(entity -> !(entity instanceof Mob))
                .collect(Collectors.toList())) {
            count++;
            entity.remove();
        }

        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Killed " + count + " entit" + (count == 1 ? "y" : "ies") + "!");
    }

}