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

@Name("Register new Discord Bot")
@Description("Register and load a new discord bot from a token and with a specific Name." +
        "\nYou need to follow Discord's developer instruction in order to generate a new bot with a token")
@Examples("login to \"TOKEN\" with name \"MyBot\"")
@Since("1.0")
public class EffRegisterBot extends Effect {

    static {
        Skript.registerEffect(EffRegisterBot.class,
                "["+ Utils.getPrefixName() +"] register [new] [discord] bot with [the] [token] %string% and with [the] (name|id) %string%",
                "["+ Utils.getPrefixName() +"] login to [token] %string% with [the] (name|id) %string%");
    }

    private Expression<String> exprName;
    private Expression<String> exprToken;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprToken = (Expression<String>) exprs[0];
        exprName = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        String token = exprToken.getSingle(e);
        if (name == null || token == null) return;
        BotManager.addBot(name, token);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "register new discord bot from token " + exprToken.toString(e, debug) + " with name " + exprName.toString(e, debug);
    }

}
