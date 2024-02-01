package nl.svenar.powercore.bukkit.modules.general;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Waypoint {
    
    private String name;
    private PCLocation targetLocation;
    private OfflinePlayer targetPlayer;

    public Waypoint(String name, PCLocation targetLocation) {
        this.name = name;
        this.targetLocation = targetLocation;
    }

    public Waypoint(String name, OfflinePlayer targetPlayer) {
        this.name = name;
        this.targetPlayer = targetPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PCLocation getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(PCLocation targetLocation) {
        this.targetLocation = targetLocation;
    }

    public OfflinePlayer getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(OfflinePlayer targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    @Override
    public String toString() {
        if (targetLocation != null) {
            return "name=" + name + ";target=" + targetLocation.getWorld() + "," + targetLocation.getX() + "," + targetLocation.getY() + "," + targetLocation.getZ();
        } else if (targetPlayer != null) {
            return "name=" + name + ";target=" + targetPlayer.getUniqueId();
        }
        return null;
    }

    public static Waypoint fromString(String string) {
        String[] waypointData = string.split(";");

        // name={name};target={UUID};
        // name={name};target={world},{x},{y},{z};

        String name = null;
        PCLocation targetLocation = null;
        OfflinePlayer targetPlayer = null;

        for (String data : waypointData) {
            String[] dataSplit = data.split("=");
            if (dataSplit.length == 2) {
                switch (dataSplit[0]) {
                    case "name":
                        name = dataSplit[1];
                        break;
                    case "target":
                        if (dataSplit[1].contains(",")) {
                            String[] locationData = dataSplit[1].split(",");
                            if (locationData.length == 4) {
                                targetLocation = new PCLocation(locationData[0], Double.parseDouble(locationData[1]),
                                        Double.parseDouble(locationData[2]), Double.parseDouble(locationData[3]));
                            }
                        } else {
                            targetPlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataSplit[1]));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        if (name != null) {
            if (targetLocation != null) {
                return new Waypoint(name, targetLocation);
            } else if (targetPlayer != null) {
                return new Waypoint(name, targetPlayer);
            }
        }
        
        return null;
    }
}
