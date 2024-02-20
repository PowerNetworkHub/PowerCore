package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class PCKit {
    
    // Stored on FS
    private String name;
    private List<ItemStack> items;
    private int cooldown;

    // Not stored on FS
    private Map<UUID, Instant> cooldownBuffer;

    public PCKit() {
        this.name = "";
        this.items = new ArrayList<>();
        this.cooldown = 300;
        this.cooldownBuffer = new HashMap<>();
    }

    public PCKit(String name) {
        this();
        this.name = name;
    }

    public PCKit(String name, List<ItemStack> items) {
        this(name);
        this.items = items;
    }

    public PCKit(String name, List<ItemStack> items, int cooldown) {
        this(name, items);
        this.name = name;
        this.items = items;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
    }

    public void putCooldown(UUID uuid, Instant instant) {
        cooldownBuffer.put(uuid, instant);
    }

    public void removeCooldown(UUID uuid) {
        cooldownBuffer.remove(uuid);
    }

    public boolean isOnCooldown(UUID uuid) {
        if (!cooldownBuffer.containsKey(uuid)) {
            return false;
        }

        if (Instant.now().getEpochSecond() - cooldownBuffer.get(uuid).getEpochSecond() > cooldown) {
            cooldownBuffer.remove(uuid);
            return false;
        }

        return true;
    }
}
