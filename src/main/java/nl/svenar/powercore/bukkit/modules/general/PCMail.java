package nl.svenar.powercore.bukkit.modules.general;

import java.time.Instant;
import java.util.UUID;

public class PCMail {

    private UUID senderUUID;
    private String title;
    private String message;
    private Instant timestamp;
    private boolean read;

    public PCMail(UUID senderUUID, String title, String message) {
        this(senderUUID, title, message, Instant.now(), false);
    }

    public PCMail(UUID senderUUID, String title, String message, Instant timestamp, boolean read) {
        this.senderUUID = senderUUID;
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
