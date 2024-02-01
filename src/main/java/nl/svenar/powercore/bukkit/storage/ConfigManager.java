package nl.svenar.powercore.bukkit.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.svenar.powercore.bukkit.PowerCore;

public class ConfigManager {

    private File configFile;
    private FileConfiguration config;

    private PowerCore plugin;

    public ConfigManager(PowerCore plugin, String configName, boolean copyFromjar) {
        this.plugin = plugin;

        createConfigFile(configName, copyFromjar);
    }

    private void createConfigFile(String configName, boolean copyFromjar) {
        configFile = new File(plugin.getDataFolder(), configName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            if (copyFromjar) {
                plugin.saveResource(configName, false);
            } else {
                try {
                    configFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Error saving " + configFile.getName());
        }
    }

    public boolean addDefault(String key, Object value) {
        if (!this.config.contains(key)) {
            this.config.set(key, value);
            return true;
        }
        return false;
    }
}