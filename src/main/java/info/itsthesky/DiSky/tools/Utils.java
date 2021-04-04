package info.itsthesky.DiSky.tools;

import ch.njol.skript.lang.Variable;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Utils extends ListenerAdapter {

    private static final String defaultString = "";
    private static final Number defaultNumber = 0;
    private static final Boolean defaultBoolean = false;
    private static final Object defaultObject = "";

    private static  <T> Object getDefaultValue(T object) {
        if (object instanceof String) {
            return defaultString;
        } else if (object instanceof Number) {
            return defaultNumber;
        } else if (object instanceof Boolean) {
            return defaultBoolean;
        } else {
            return defaultObject;
        }
    }

    public static boolean containURL(String input) {
        return input.contains("http://") ||
                input.contains("https://") ||
                input.contains(".com") ||
                input.contains(".org") ||
                input.contains(".html") ||
                input.contains(".php");
    }

    public static LinkedHashMap<String, Integer> sortHashMapByValues(
            HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
                new LinkedHashMap<>();

        for (Integer val : mapValues) {
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    public static <T, Q> LinkedHashMap<T, Q> reverseMap(LinkedHashMap<T, Q> toReverse) {
        LinkedHashMap<T, Q> reversedMap = new LinkedHashMap<>();
        List<T> reverseOrderedKeys = new ArrayList<>(toReverse.keySet());
        Collections.reverse(reverseOrderedKeys);
        reverseOrderedKeys.forEach((key)->reversedMap.put(key,toReverse.get(key)));
        return reversedMap;
    }

    public static List<Permission> convertPerms(String... perms) {
        List<Permission> permissions = new ArrayList<>();
        for (String s : perms) {
            try {
                permissions.add(Permission.valueOf(s.replace(" ", "_").toUpperCase()));
            } catch (IllegalArgumentException ignored) {}
        }
        return permissions;
    }

    @Nullable
    public static Long parseLong(@NotNull String s, boolean shouldPrintError, boolean manageDiscordValue) {
        Long id = null;
        if (manageDiscordValue) {
            s = s
                    .replaceAll("&", "")
                    .replaceAll("<", "")
                    .replaceAll(">", "")
                    .replaceAll("#", "")
                    .replaceAll("!", "")
                    .replaceAll("@", "");
        }
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException e) {
            if (shouldPrintError) e.printStackTrace();
            return null;
        }
        return id;
    }

    public static boolean equalsAnyIgnoreCase(String toMatch, String... potentialMatches) {
        return Arrays.asList(potentialMatches).contains(toMatch);
    }

    public static boolean areEventAsync() {
        return false;
    }

    public static String getPrefixName() {
        return "disky";
    }

    public static TextChannel checkChannel(Object original) {
        if (original instanceof Channel) {
            return ((Channel) original).getTextChannel();
        } else if (original instanceof TextChannel) {
            return (TextChannel) original;
        } else {
            return null;
        }
    }

    public static String replaceFirst(String s, String pattern, String replacement) {
        int idx = s.indexOf(pattern);
        return s.substring(0, idx) + replacement + s.substring(idx + pattern.length());
    }

    public static int hexToInt(String hex) {
        hex = hex.replace("#", "");
        return Integer.parseInt(hex, 16);
    }

    public static Integer round(Double number) {
        String t = number.toString().split("\\.")[0];
        return Integer.valueOf(t);
    }

    public static <T> void setSkriptVariable(Variable<T> variable, Object value, Event event) {
        String name = variable.getName().toString(event);
        Variables.setVariable(name, value, event, variable.isLocal());
    }

    public static <T> void setSkriptList(Variable<T> variable, Event event, Object... value) {
        String name = variable.getName().toString(event);
        setList(name, event, variable.isLocal(), value);
    }

    /**
     * Small edition for DiSky
     * @author Blitz
     */
    public static void setList(String name, Event e, boolean isLocal, Object... objects) {
        if (objects == null || name == null) return;
        List<Object> list = Arrays.asList(objects.clone());

        int separatorLength = Variable.SEPARATOR.length() + 1;
        name = name.substring(0, (name.length() - separatorLength));
        name = name.toLowerCase() + Variable.SEPARATOR;
        for (int i = 1; i < list.size()+1; i++){
            Variables.setVariable(name + i, list.get(i-1), e, isLocal);
        }
    }

    public static String colored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void onReady(ReadyEvent e) {
        timeHashMap.remove(e.getJDA());
        timeHashMap.put(e.getJDA(), Date.now());
    }
    public static HashMap<JDA, Date> timeHashMap = new HashMap<>();
    public static Timespan getUpTime(JDA jda) {
        return timeHashMap.get(jda).difference(Date.now());
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(DiSky.getInstance(), runnable);
    }

    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(DiSky.getInstance(), runnable);
    }

    public static boolean areSlashCommandsEnabled() {
        File file = new File(DiSky.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) return false;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.contains("SlashCommand.Enabled")) return false;
        if (!config.getBoolean("SlashCommand.Enabled")) {
            DiSky.getInstance().getLogger().warning("Slash Commands are disabled by default, but you're trying to use them somewhere.");
            DiSky.getInstance().getLogger().warning("See the Wiki (https://github.com/SkyCraft78/DiSky/wiki/Slash-Commands) to know how enabled and use them!");
        }
        return config.getBoolean("SlashCommand.Enabled");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOrSetDefault(String file, String path, T defaultValue) {
        File f = new File(DiSky.getInstance().getDataFolder(), file);
        if (!f.exists()) return (T) getDefaultValue(defaultValue);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(f);
        if (!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            try {
                configuration.save(f);
            } catch (IOException e) {
                DiSky.getInstance().getLogger().warning("Unable to save the default DiSky configuration file :c Error: " + e.getMessage());
            }
            return defaultValue;
        }
        return (T) configuration.get(path, defaultValue);
    }

    public static String[] valuesToString(Object... values) {
        List<String> s = new ArrayList<>();
        for (Object o : values) {
            s.add(o.toString());
        }
        return s.toArray(new String[0]);
    }

    public static void saveResourceAs(String inPath) {
        if (inPath == null || inPath.isEmpty()) {
            throw new IllegalArgumentException("The input path cannot be null or empty");
        }
        InputStream in = DiSky.getInstance().getResource(inPath);
        if (in == null) {
            throw new IllegalArgumentException("The file "+inPath+" cannot be found in plugin's resources");
        }
        if (!DiSky.getInstance().getDataFolder().exists() && !DiSky.getInstance().getDataFolder().mkdir()) {
            DiSky.getInstance().getLogger().severe("Failed to make the main directory !");
        }
        File inFile = new File(DiSky.getInstance().getDataFolder(), inPath);
        if (!inFile.exists()) {
            Bukkit.getConsoleSender().sendMessage("§cThe file "+inFile.getName()+" cannot be found, create one for you ...");

            DiSky.getInstance().saveResource(inPath, false);

            if (!inFile.exists()) {
                DiSky.getInstance().getLogger().severe("Unable to copy file !");
            } else {
                Bukkit.getConsoleSender().sendMessage("§aThe file "+inFile.getName()+" has been created!");
            }
        }
    }
}
