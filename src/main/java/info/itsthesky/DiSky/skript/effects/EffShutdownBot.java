package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.Utils;
import org.bukkit.event.Event;

@Name("Shutdown Discord Bot")
@Description("Close JDA instance and shutdown the specific bot." +
        "\n You can't use it as discord bot after shutdown it!")
@Examples("shutdown the bot named \"MyBot\"")
@Since("1.0")
public class EffShutdownBot extends Effect {

    static {
        Skript.registerEffect(EffShutdownBot.class,
                "["+ Utils.getPrefixName() +"] (stop|shutdown|close instance of) [the] bot (named|with name) %string%");
    }

    private Expression<String> exprName;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        if (name == null) return;
        BotManager.removeAndShutdown(name);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "shutdown the bot named " + exprName.toString(e, debug);
    }

}
