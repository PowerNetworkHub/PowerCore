package nl.svenar.powercore.bukkit.modules.general;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PCLocation {

    private String world;
    private double x;
    private double y;
    private double z;

    public PCLocation(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PCLocation(String world, int x, int y, int z) {
        this(world, (double) x, (double) y, (double) z);
    }

    public PCLocation(Location location) {
        this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public int getBlockX() {
        return (int) Math.round(x);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setX(int x) {
        this.x = (double) x;
    }

    public double getY() {
        return y;
    }

    public int getBlockY() {
        return (int) Math.round(y);
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setY(int y) {
        this.y = (double) y;
    }

    public double getZ() {
        return z;
    }

    public int getBlockZ() {
        return (int) Math.round(z);
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setZ(int z) {
        this.z = (double) z;
    }

    @Override
    public String toString() {
        return world + " - " + (int) x + ", " + (int) y + ", " + (int) z;
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public Location toLocation() {
        Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
        return location;
    }

    public Location toLocation(Location location) {
        location.setWorld(Bukkit.getServer().getWorld(world));
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        return location;
    }
}
