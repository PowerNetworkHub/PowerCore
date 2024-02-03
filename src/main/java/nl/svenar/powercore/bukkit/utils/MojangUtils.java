package nl.svenar.powercore.bukkit.utils;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import com.google.common.base.Charsets;

public class MojangUtils {

    public static UUID getUUIDFromAPI(String playerName) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            String UUIDJson = IOUtils.toString(new URL(url), Charsets.UTF_8);
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            String uuid = UUIDObject.get("id").toString();
            if (!uuid.contains("-")) {
                uuid = uuid.replaceFirst(
                        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                        "$1-$2-$3-$4-$5");
            }
            return UUID.fromString(uuid);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNameFromAPI(String uuid) {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.replaceAll("-", "");
        try {
            String nameJson = IOUtils.toString(new URL(url), Charsets.UTF_8);
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(nameJson);
            return nameObject.get("name").toString();
        } catch (IOException | ParseException e) {
            return null;
        }
    }
}
