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
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import org.bukkit.event.Event;

@Name("Token of Bot")
@Description("Return the Token used for the bot.")
@Examples("set {_token} to token of bot named \"MyBot\"")
@Since("1.0")
public class ExprToken extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprToken.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] token of [the] [bot] [(named|with name)] %string/bot%");
	}

	private Expression<Object> exprName;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprName = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(Event e) {
		Object name = exprName.getSingle(e);
		if (name == null) return new String[0];
		if (name instanceof JDA) {
			return new String[] {((JDA) name).getToken()};
		} else {
			JDA bot = BotManager.getBot(name.toString());
			if (bot == null) return new String[0];
			return new String[] {bot.getToken()};
		}
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
		return "token of bot named" + exprName.toString(e, debug);
	}

}