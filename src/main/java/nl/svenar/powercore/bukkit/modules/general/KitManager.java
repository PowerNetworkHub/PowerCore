package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import nl.svenar.powercore.bukkit.PowerCore;
import nl.svenar.powercore.bukkit.storage.ConfigManager;

public class KitManager {

    private PowerCore plugin;
    private List<PCKit> kits;

    public enum KitResult {
        KIT_NOT_FOUND, KIT_COOLDOWN, KIT_NO_SPACE, KIT_GIVEN
    }

    public KitManager(PowerCore plugin) {
        this.plugin = plugin;
        this.kits = new ArrayList<>();
    }

    public List<PCKit> getKits() {
        return kits;
    }

    public boolean kitExists(String kitName) {
        for (PCKit kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                return true;
            }
        }
        return false;
    }

    public void saveKits() {
        ConfigManager kitConfig = plugin.getKitConfigManager();
        kitConfig.getConfig().set("kits", new String[] {});

        for (PCKit kit : kits) {
            String kitName = kit.getName();
            int cooldown = kit.getCooldown();
            List<ItemStack> items = kit.getItems();

            kitConfig.getConfig().set("kits." + kitName + ".cooldown", cooldown);
            kitConfig.getConfig().set("kits." + kitName + ".items", items);
        }
        kitConfig.saveConfig();
    }

    public void loadKits() {
        ConfigManager kitConfig = plugin.getKitConfigManager();

        if (!kitConfig.getConfig().contains("kits")) {
            return;
        }

        if (!kitConfig.getConfig().isConfigurationSection("kits")) {
            return;
        }

        for (String kitName : kitConfig.getConfig().getConfigurationSection("kits").getKeys(false)) {
            List<ItemStack> items = new ArrayList<>();
            int cooldown = kitConfig.getConfig().getInt("kits." + kitName + ".cooldown", 0);
            for (Object item : kitConfig.getConfig().getList("kits." + kitName + ".items")) {
                items.add((ItemStack) item);
            }
            PCKit kit = new PCKit(kitName, items, cooldown);
            kits.add(kit);
        }
    }

    public void createKit(String kitName, ItemStack[] contents) {
        PCKit kit = new PCKit(kitName);
        for (ItemStack item : contents) {
            if (item == null) {
                continue;
            }
            kit.addItem(item);
        }
        kits.add(kit);
    }

    public void setKitCooldown(String kitName, int cooldown) {
        for (PCKit kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                kit.setCooldown(cooldown);
                break;
            }
        }
    }

    public void deleteKit(String kitName) {
        for (PCKit kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                kits.remove(kit);
                break;
            }
        }
    }

    public KitResult giveKit(Player player, String kitName) {
        PCKit kit = null;
        for (PCKit availableKit : kits) {
            if (availableKit.getName().equalsIgnoreCase(kitName)) {
                kit = availableKit;
                break;
            }
        }
        if (kit == null) {
            return KitResult.KIT_NOT_FOUND;
        }

        int emptySlots = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                emptySlots++;
            }
        }

        if (emptySlots < kit.getItems().size()) {
            return KitResult.KIT_NO_SPACE;
        }

        if (kit.isOnCooldown(player.getUniqueId())) {
            return KitResult.KIT_COOLDOWN;
        }

        for (ItemStack item : kit.getItems()) {
            player.getInventory().addItem(item);
        }

        player.updateInventory();
        kit.putCooldown(player.getUniqueId(), Instant.now());
        return KitResult.KIT_GIVEN;
    }
}
