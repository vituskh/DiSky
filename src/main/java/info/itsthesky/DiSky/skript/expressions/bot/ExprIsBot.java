package info.itsthesky.DiSky.skript.expressions.bot;

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
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Is User a Bot")
@Description("See if a specific user is a bot or not.")
@Examples({
		"if event-user is a bot:",
				"\treply with \"Sorry, I don't speak to non-human!\""
})
@Since("1.0")
public class ExprIsBot extends SimpleExpression<Boolean> {

	static {
		//Skript.registerExpression(ExprIsBot.class, Boolean.class, ExpressionType.SIMPLE,
		//		"["+ Utils.getPrefixName() +"] %user% (is|was) a [discord] bot",
		//"["+ Utils.getPrefixName() +"] %user% (is not|isn't) a [discord] bot");
	}

	private Expression<User> exprUser;
	private int pattern;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprUser = (Expression<User>) exprs[0];
		pattern = matchedPattern;
		return true;
	}

	@Override
	protected Boolean[] get(Event e) {
		User user = exprUser.getSingle(e);
		if (user == null) return new Boolean[] {false};
		if (pattern == 0) {
			return new Boolean[] {user.isBot()};
		} else {
			return new Boolean[] {!user.isBot()};
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "bot state of user " +exprUser.toString(e, debug);
	}

}