package info.itsthesky.DiSky.skript.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import info.itsthesky.DiSky.tools.DiSkyErrorHandler;
import info.itsthesky.DiSky.tools.Utils;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Search Audio")
@Description("Search, within Youtube, the URL of track according to the input. Use 'last searched urls' to get them back.")
@Examples("search \"sevens deadly sins\" in youtube")
@Since("1.6")
public class EffSearchAudio extends Effect {

    static {
        //Skript.registerEffect(EffSearchAudio.class,
        //        "["+ Utils.getPrefixName() +"] search [the] [input] %string% [in youtube] and store (it|the results) in %-objects%");
    }

    private Expression<String> exprInput;
    private Expression<Object> exprVar;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprInput = (Expression<String>) exprs[0];
        exprVar = (Expression<Object>) exprs[1];
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event e) {
        DiSkyErrorHandler.executeHandleCode(e, Event -> {
            String input = exprInput.getSingle(e);
            if (input == null) return;
            List<String> urls = new ArrayList<>();
            if (Utils.containURL(input)) {
                urls.add(input);
            } else {
                urls.addAll(Arrays.asList(AudioUtils.searchURLs(input)));
            }
            if (exprVar == null) return;
            if (!exprVar.getClass().getName().equalsIgnoreCase("ch.njol.skript.lang.Variable")) return;
            Utils.setSkriptList((Variable) exprVar, e, urls.toArray(new Object[0]));
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "search input " + exprInput.toString(e, debug) + " in youtube and store the results in " + exprVar.toString(e, debug);
    }

}
