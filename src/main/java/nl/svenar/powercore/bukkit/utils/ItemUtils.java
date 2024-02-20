package nl.svenar.powercore.bukkit.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    
    public static ConfigurationSection serializeItemStack(ItemStack item) {
        if (item == null) {
            return null;
        }

        YamlConfiguration config = new YamlConfiguration();
        ConfigurationSection section = config.createSection("item");
        section.set("content", item);
        return section;
    }

    public static ItemStack deserializeItemStack(ConfigurationSection section) {
        if (section == null) {
            return null;
        }

        return section.getItemStack("content");
    }
}
