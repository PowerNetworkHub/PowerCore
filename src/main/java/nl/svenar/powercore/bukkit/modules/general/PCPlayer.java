package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

public class PCPlayer {
    
    // Stored on FS
    private UUID uuid;
    private String name;
    private List<Waypoint> waypoints;
    private boolean compassEnabled;
    private PCLocation logoutLocation;
    private PCLocation lastLocation;
    private boolean banned;
    private String banReason;
    private Instant lastSeen;
    private boolean muted;
    private Map<String, PCLocation> homes;
    private List<PCMail> mail;

    // Not stored on FS
    private boolean online;
    private String lastDirectMessageSender;
    private boolean afk;
    private Map<UUID, Instant> tpaBuffer;

    public PCPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.waypoints = new ArrayList<>();
        this.homes = new HashMap<>();
        this.mail = new ArrayList<>();
        this.tpaBuffer = new HashMap<>();
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void removeWaypoint(Waypoint waypoint) {
        waypoints.remove(waypoint);
    }

    public void removeWaypoint(String name) {
        waypoints.removeIf(waypoint -> waypoint.getName().equalsIgnoreCase(name));
    }

    public Waypoint getWaypoint(String name) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getName().equalsIgnoreCase(name)) {
                return waypoint;
            }
        }
        return null;
    }

    public boolean isCompassEnabled() {
        return compassEnabled;
    }

    public void setCompassEnabled(boolean compassEnabled) {
        this.compassEnabled = compassEnabled;
    }

    public void setLogoutLocation(PCLocation location) {
        this.logoutLocation = location;
    }

    public void setLogoutLocation(Location location) {
        setLogoutLocation(new PCLocation(location));
    }

    public PCLocation getLogoutLocation() {
        return logoutLocation;
    }

    public void setLastLocation(PCLocation location) {
        this.lastLocation = location;
    }

    public void setLastLocation(Location location) {
        setLastLocation(new PCLocation(location));
    }

    public PCLocation getLastLocation() {
        return lastLocation;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanReason(String reason) {
        this.banReason = reason;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setLastDirectMessageSender(String name) {
        lastDirectMessageSender = name;
    }

    public String getLastDirectMessageSender() {
        return lastDirectMessageSender;
    }

    public void addHome(String name, PCLocation location) {
        if (homes == null) {
            homes = new HashMap<String, PCLocation>();
        }

        homes.put(name, location);
    }

    public void removeHome(String homeName) {
        if (homes == null) {
            homes = new HashMap<String, PCLocation>();
        }

        for (String name : homes.keySet()) {
            if (name.equalsIgnoreCase(homeName)) {
                homes.remove(name);
                return;
            }
        }
    }

    public void setHomes(Map<String, PCLocation> homes) {
        this.homes = homes;
    }

    public Map<String, PCLocation> getHomes() {
        if (homes == null) {
            homes = new HashMap<String, PCLocation>();
        }

        return homes;
    }

    public boolean hasHome(String homeName) {
        if (homes == null) {
            homes = new HashMap<String, PCLocation>();
        }

        for (String name : homes.keySet()) {
            if (name.equalsIgnoreCase(homeName)) {
                return true;
            }
        }
        return false;
    }

    public void addMail(PCMail mail) {
        if (this.mail == null) {
            this.mail = new ArrayList<PCMail>();
        }

        this.mail.add(mail);
    }

    public void removeMail(PCMail mail) {
        if (this.mail == null) {
            this.mail = new ArrayList<PCMail>();
        }

        this.mail.remove(mail);
    }

    public void setMail(List<PCMail> mail) {
        this.mail = mail;
    }

    public List<PCMail> getMail() {
        if (mail == null) {
            mail = new ArrayList<PCMail>();
        }

        return mail;
    }

    public PCMail getMail(String title) {
        if (mail == null) {
            mail = new ArrayList<PCMail>();
        }

        for (PCMail mail : mail) {
            if (mail.getTitle().equalsIgnoreCase(title)) {
                return mail;
            }
        }
        return null;
    }

    public void setAFK(boolean afk) {
        this.afk = afk;
    }

    public boolean isAFK() {
        return afk;
    }

    public void addTpaBuffer(UUID uuid, Instant time) {
        tpaBuffer.put(uuid, time);
    }

    public void removeTpaBuffer(UUID uuid) {
        tpaBuffer.remove(uuid);
    }

    public void setTpaBuffer(Map<UUID, Instant> tpaBuffer) {
        this.tpaBuffer = tpaBuffer;
    }

    public Map<UUID, Instant> getTpaBuffer() {
        return tpaBuffer;
    }
}
