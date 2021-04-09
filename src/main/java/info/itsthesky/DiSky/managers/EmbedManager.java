package info.itsthesky.DiSky.managers;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashMap;

public class EmbedManager {

    private static final HashMap<String, EmbedBuilder> templates = new HashMap<>();
    public static void registerTemplate(String id, EmbedBuilder embed) {
        templates.put(id, embed);
    }
    public static EmbedBuilder getTemplate(String id) {
        if (!templates.containsKey(id)) return new EmbedBuilder();
        return templates.get(id);
    }

}
