package info.itsthesky.DiSky.skript.events.skript.command;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Command Prefix")
@Description("Get the prefix of a discord command event")
@Since("1.3")
public class ExprCommandPrefix extends SimpleExpression<String> {

    public static String commandPrefix;

    static {
        Skript.registerExpression(ExprCommandPrefix.class, String.class, ExpressionType.SIMPLE,
                "[the] [command] prefix"
        );
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Nullable
    @Override
    protected String[] get(Event e) { return new String[]{commandPrefix}; }
    @Override
    public Class<? extends String> getReturnType() { return String.class; }
    @Override
    public boolean isSingle() { return true; }
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the command prefix";
    }
}
