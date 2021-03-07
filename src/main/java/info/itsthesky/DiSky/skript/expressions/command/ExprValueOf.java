package info.itsthesky.DiSky.skript.expressions.command;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.command.Command;
import info.itsthesky.DiSky.tools.object.command.Prefix;
import org.bukkit.event.Event;

@Name("Value of Command entity")
@Description("Return the value of command and prefix in the command event.")
@Examples("# Don't have any idea :c")
@Since("1.0")
public class ExprValueOf extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprValueOf.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [discord] value of [the] [command] [entity] %command/prefix%");
	}

	private Expression<Object> exprEntity;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprEntity = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(final Event e) {
		Object entity = exprEntity.getSingle(e);
		if (entity instanceof Command) {
			return new String[] {((Command) entity).getValue()};
		} else if (entity instanceof Prefix) {
			return new String[] {((Prefix) entity).getValue()};
		}
		return new String[0];
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "discord value of command entity " + exprEntity.toString(e, debug);
	}

}