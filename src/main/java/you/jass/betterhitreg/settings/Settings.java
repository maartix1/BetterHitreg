package you.jass.betterhitreg.settings;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Settings {
    private static final Path settings = FabricLoader.getInstance().getConfigDir().resolve("hitreg.properties");
    private static final Properties properties = new Properties();
    private static final Properties defaults = new Properties();
    private static final Map<String, String> categories = new HashMap<>();

    static {
        for (Toggle toggle : Toggle.values()) {
            categories.put(toggle.key(), "toggle");
            defaults.setProperty(toggle.key(), String.valueOf(toggle.defaultValue()));
        }

        categories.put("tutorial", "toggle");

        categories.put("total_fights", "tracked");
        categories.put("fight_playtime_(seconds)", "tracked");

        categories.put("hitreg", "configure");

        categories.put("muffle_amount", "configure");
        categories.put("sharpen_amount", "configure");
        categories.put("metronome", "configure");

        categories.put("cross_far_color", "render");
        categories.put("cross_near_color", "render");
        categories.put("cross_far_color_with_hitbox", "render");
        categories.put("cross_near_color_with_hitbox", "render");
        categories.put("hitbox_far_color", "render");
        categories.put("hitbox_near_color", "render");
        categories.put("server_hitbox_color", "render");
        categories.put("your_reach_far_color", "render");
        categories.put("your_reach_near_color", "render");
        categories.put("their_reach_far_color", "render");
        categories.put("their_reach_near_color", "render");
        categories.put("your_jump_near_color", "render");
        categories.put("your_jump_far_color", "render");
        categories.put("their_jump_near_color", "render");
        categories.put("their_jump_far_color", "render");
        categories.put("jump_reset_color", "render");
        categories.put("perfect_hit_color", "render");

        categories.put("cross_opacity", "render");
        categories.put("hitbox_opacity", "render");
        categories.put("server_hitbox_opacity", "render");
        categories.put("your_reach_opacity", "render");
        categories.put("their_reach_opacity", "render");
        categories.put("your_jump_range_opacity", "render");
        categories.put("their_jump_range_opacity", "render");
        categories.put("jump_reset_opacity", "render");
        categories.put("perfect_hit_opacity", "render");

        categories.put("background_color", "ui");
        categories.put("background_opacity", "ui");
        categories.put("border_color", "ui");
        categories.put("border_opacity", "ui");
        categories.put("text_color", "ui");
        categories.put("text_opacity", "ui");
        categories.put("hovered_color", "ui");
        categories.put("hovered_opacity", "ui");
        categories.put("highlighted_color", "ui");
        categories.put("highlighted_opacity", "ui");

        defaults.setProperty("hitreg", "0");
        defaults.setProperty("tutorial", "true");

        defaults.setProperty("muffle_amount", "0");
        defaults.setProperty("sharpen_amount", "0");
        defaults.setProperty("metronome", "0");

        defaults.setProperty("total_fights", "0");
        defaults.setProperty("fight_playtime_(seconds)", "0");

        defaults.setProperty("hitbox_far_color", "FFFFFF");
        defaults.setProperty("hitbox_near_color", "FF0000");
        defaults.setProperty("cross_far_color", "FFFFFF");
        defaults.setProperty("cross_near_color", "FF0000");
        defaults.setProperty("cross_far_color_with_hitbox", "0000FF");
        defaults.setProperty("cross_near_color_with_hitbox", "0000FF");
        defaults.setProperty("server_hitbox_near_color", "7f00FF");
        defaults.setProperty("server_hitbox_far_color", "7f00FF");
        defaults.setProperty("your_reach_near_color", "FF0000");
        defaults.setProperty("your_reach_far_color", "FFFFFF");
        defaults.setProperty("their_reach_near_color", "FF0000");
        defaults.setProperty("their_reach_far_color", "FFFFFF");
        defaults.setProperty("your_jump_near_color", "007FFF");
        defaults.setProperty("your_jump_far_color", "007FFF");
        defaults.setProperty("their_jump_near_color", "007FFF");
        defaults.setProperty("their_jump_far_color", "007FFF");
        defaults.setProperty("ring_far_color", "FFFFFF");
        defaults.setProperty("ring_near_color", "FF0000");
        defaults.setProperty("jump_reset_color", "FFFF00");
        defaults.setProperty("perfect_hit_color", "00FF00");

        defaults.setProperty("cross_opacity", "255");
        defaults.setProperty("hitbox_opacity", "255");
        defaults.setProperty("server_hitbox_opacity", "125");
        defaults.setProperty("your_reach_opacity", "255");
        defaults.setProperty("their_reach_opacity", "255");
        defaults.setProperty("your_jump_range_opacity", "255");
        defaults.setProperty("their_jump_range_opacity", "255");
        defaults.setProperty("jump_reset_opacity", "255");
        defaults.setProperty("perfect_hit_opacity", "255");

        defaults.setProperty("background_color", "000000");
        defaults.setProperty("background_opacity", "230");
        defaults.setProperty("border_color", "646464");
        defaults.setProperty("border_opacity", "255");
        defaults.setProperty("text_color", "DEDEDE");
        defaults.setProperty("text_opacity", "255");
        defaults.setProperty("hovered_color", "FFF3A6");
        defaults.setProperty("hovered_opacity", "255");
        defaults.setProperty("highlighted_color", "FFE350");
        defaults.setProperty("highlighted_opacity", "255");

        properties.putAll(defaults);
        load();
    }

    public static int getHitreg() {
        return Integer.parseInt(get("hitreg"));
    }
    public static boolean isTutorial() {
        return Boolean.parseBoolean(get("tutorial"));
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, String value) {
        properties.setProperty(key, value);
        save();
    }

    public static boolean toggle(String key) {
        boolean toggled = !getBoolean(key);
        setBoolean(key, toggled);
        return toggled;
    }

    public static void addFight(long duration) {
        setInt("total_fights", getInt("total_fights") + 1);
        setLong("fight_playtime_(seconds)", getLong("fight_playtime_(seconds)") + duration);
    }

    public static void load() {
        if (!Files.exists(settings)) {
            save();
            return;
        }

        try (InputStream input = Files.newInputStream(settings)) {
            properties.load(input);
            for (String key : defaults.stringPropertyNames()) {
                if (!properties.containsKey(key)) properties.setProperty(key, defaults.getProperty(key));
            }
        } catch (IOException e) {
            System.err.println("Couldn't load file: " + e.getMessage());
            properties.putAll(defaults);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(settings.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(settings)) {
                writer.write("#Hitreg Settings");
                writer.newLine();
                writer.newLine();

                Map<String, List<String>> grouped = new HashMap<>();
                for (String key : properties.stringPropertyNames()) {
                    String cat = categories.get(key);
                    if (cat == null) cat = "Other";
                    grouped.computeIfAbsent(cat, k -> new ArrayList<>()).add(key);
                }

                for (List<String> list : grouped.values()) {
                    list.sort(String.CASE_INSENSITIVE_ORDER);
                }

                writeCategory(writer, "Configure", grouped.get("configure"));
                writeCategory(writer, "Render", grouped.get("render"));
                writeCategory(writer, "UI", grouped.get("ui"));
                writeCategory(writer, "Tracked", grouped.get("tracked"));
                writeCategory(writer, "Toggle", grouped.get("toggle"));
            }
        } catch (IOException e) {
            System.err.println("Couldn't save file: " + e.getMessage());
        }
    }

    public static void writeCategory(BufferedWriter writer, String name, List<String> keys) throws IOException {
        if (keys == null || keys.isEmpty()) return;
        writer.write("#" + name);
        writer.newLine();
        for (String key : keys) {
            writer.write(key + "=" + properties.getProperty(key));
            writer.newLine();
        }
        writer.newLine();
    }

    public static String getString(String key) {
        String v = get(key);
        if (v == null || v.trim().isEmpty()) {
            String d = defaults.getProperty(key);
            if (d != null && !d.trim().isEmpty()) v = d;
            else v = "0";
            set(key, d);
        } return v;
    }

    public static boolean getBoolean(String key) {
        String v = get(key);
        if (v == null || v.trim().isEmpty()) { String d = defaults.getProperty(key);
            if (d != null && !d.trim().isEmpty()) {
                set(key, d);
                return Boolean.parseBoolean(d.trim());
            }

            set(key, "false");
            return false;
        }
        return Boolean.parseBoolean(v.trim());
    }

    public static int getInt(String key) {
        String v = get(key);
        if (v != null) try { return Integer.parseInt(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Integer.parseInt(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return 0;
    }

    public static long getLong(String key) {
        String v = get(key);
        if (v != null) try { return Long.parseLong(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Long.parseLong(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return 0L;
    }

    public static double getDouble(String key) {
        String v = get(key);
        if (v != null) try { return Double.parseDouble(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Double.parseDouble(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return 0d;
    }

    public static float getFloat(String key) {
        String v = get(key);
        if (v != null) try { return Float.parseFloat(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Float.parseFloat(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return 0f;
    }

    public static short getShort(String key) {
        String v = get(key);
        if (v != null) try { return Short.parseShort(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Short.parseShort(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return (short) 0;
    }

    public static byte getByte(String key) {
        String v = get(key);
        if (v != null) try { return Byte.parseByte(v.trim()); } catch (NumberFormatException ignored) {}
        String d = defaults.getProperty(key);
        if (d != null) try {
            set(key, d);
            return Byte.parseByte(d.trim());
        } catch (NumberFormatException ignored) {}

        set(key, "0");
        return (byte) 0;
    }

    public static void setString(String key, String value) {
        set(key, String.valueOf(value));
    }

    public static void setBoolean(String key, boolean value) {
        set(key, String.valueOf(value));
    }

    public static void setInt(String key, int value) {
        set(key, String.valueOf(value));
    }

    public static void setLong(String key, long value) {
        set(key, String.valueOf(value));
    }

    public static void setDouble(String key, double value) {
        set(key, String.valueOf(value));
    }

    public static void setFloat(String key, float value) {
        set(key, String.valueOf(value));
    }

    public static void setShort(String key, short value) {
        set(key, String.valueOf(value));
    }

    public static void setByte(String key, byte value) {
        set(key, String.valueOf(value));
    }
}
