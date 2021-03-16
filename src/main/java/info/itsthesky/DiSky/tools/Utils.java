package info.itsthesky.DiSky.tools;

import ch.njol.skript.lang.Variable;
import ch.njol.skript.variables.Variables;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Utils {

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

    public static int hexToInt(String hex) {
        hex = hex.replace("#", "");
        return Integer.parseInt(hex, 16);
    }

    public static Integer round(Double number) {
        String t = number.toString().split("\\.")[0];
        return Integer.valueOf(t);
    }

    public static void setSkriptVariable(Variable variable, Object value, Event event) {
        String name = variable.getName().toString(event);
        Variables.setVariable(name, value, event, variable.isLocal());
    }

    public static Number[] toNumeric(final Object ...array) {
        if (array == null || array.length == 0) return null;
        final List<Number> result = new ArrayList<>();
        for (Object o : array) {
            if (o instanceof Number) {
                result.add((Number) o);
                continue;
            }
            return null;
        }
        return result.toArray(result.toArray(new Number[0]));
    }

    public static String colored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
