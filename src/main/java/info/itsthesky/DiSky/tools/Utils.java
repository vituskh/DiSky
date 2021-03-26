package info.itsthesky.DiSky.tools;

import ch.njol.skript.lang.Variable;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Time;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

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
}
