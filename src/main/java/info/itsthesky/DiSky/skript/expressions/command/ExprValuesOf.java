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
import info.itsthesky.DiSky.tools.object.command.Arguments;
import org.bukkit.event.Event;

@Name("Values of Arguments")
@Description("Return the list with all command's arguments")
@Examples("# Don't have any idea :c")
@Since("1.0")
public class ExprValuesOf extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprValuesOf.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [discord] values of [the] [arguments] [entity] %argument%");
	}

	private Expression<Arguments> exprEntity;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprEntity = (Expression<Arguments>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(final Event e) {
		Arguments entity = exprEntity.getSingle(e);
		if (entity == null) return new String[0];
		if (entity.getArgs() == null) return new String[0];
		return entity.getArgs().toArray(new String[0]);
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
		return "discord values of command entity " + exprEntity.toString(e, debug);
	}

}