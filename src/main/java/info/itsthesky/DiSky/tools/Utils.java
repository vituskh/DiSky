package info.itsthesky.DiSky.tools;

import ch.njol.skript.lang.Variable;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Time;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class Utils extends ListenerAdapter {

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
