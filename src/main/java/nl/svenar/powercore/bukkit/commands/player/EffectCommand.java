package nl.svenar.powercore.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.commands.PowerBaseCommand;
import nl.svenar.powercore.bukkit.utils.Util;
import nl.svenar.powercore.common.utils.PowerColor;
import nl.svenar.powercore.common.utils.PowerMath;

@CommandAlias("effect")
@Description("Give a player an effect")
public class EffectCommand extends PowerBaseCommand {

    public EffectCommand(PowerCore plugin) {
        super(plugin);
    }

    @Subcommand("give")
    @CommandPermission("powercore.effect")
    @CommandCompletion("@players @effects|all strength|@range:1-100 duration|@range:1-100 hideParticles|true|false @nothing")
    public void onGiveCommand(CommandSender sender, String targetPlayer, String effect, int strength, int duration,
            @Optional String hideParticlesString) {
        Player player = plugin.getServer().getPlayer(targetPlayer);
        if (player == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            return;
        }

        boolean hideParticles = false;
        if (hideParticlesString != null) {
            hideParticles = hideParticlesString.equalsIgnoreCase("true");
        }

        if (strength < 1 || strength > 100) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Strength must be between 1 and 100!");
            return;
        }

        int newStrength = (int) PowerMath.map(strength, 0, 100, 0, 255);

        if (duration < 1) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Duration must be at least 1 second");
            return;
        }

        if (effect.equals("all")) {
            for (PotionType potionType : PotionType.values()) {
                for (PotionEffect potionEffect : potionType.getPotionEffects()) {
                    PotionEffect modifiedEffect = new PotionEffect(potionEffect.getType(), duration * Util.TASK_TPS,
                            newStrength, false, !hideParticles);
                    player.addPotionEffect(modifiedEffect);
                }
            }

            sendMessage(sender,
                    PowerColor.ChatColor.GREEN + "Player " + player.getName() + " has been given all effects with "
                            + strength + "% strength for " + duration + " seconds!");
            return;
        }

        try {
            PotionType potionType = PotionType.valueOf(effect.toUpperCase());

            for (PotionEffect potionEffect : potionType.getPotionEffects()) {
                PotionEffect modifiedEffect = new PotionEffect(potionEffect.getType(), duration * Util.TASK_TPS,
                        newStrength, false, !hideParticles);
                player.addPotionEffect(modifiedEffect);
            }

            sendMessage(sender,
                    PowerColor.ChatColor.GREEN + "Player " + player.getName() + " has been given the effect " + effect
                            + " with " + strength + "% strength for " + duration + " seconds!");
        } catch (IllegalArgumentException e) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Effect not found!");
        }
    }

    @Subcommand("clear")
    @CommandPermission("powercore.effect")
    @CommandCompletion("@players @nothing")
    public void onClearCommand(CommandSender sender, String targetPlayer) {
        Player player = plugin.getServer().getPlayer(targetPlayer);
        if (player == null) {
            sendMessage(sender, PowerColor.ChatColor.DARK_RED + "Player not found!");
            return;
        }

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        sendMessage(sender,
                PowerColor.ChatColor.GREEN + "Player " + player.getName() + " has been cleared of effects!");
    }
}
