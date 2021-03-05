package info.itsthesky.Vixio3.skript.expressions;

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
import info.itsthesky.Vixio3.managers.BotManager;
import info.itsthesky.Vixio3.tools.Utils;
import net.dv8tion.jda.api.JDA;
import org.bukkit.event.Event;

import java.awt.image.BufferedImage;

@Name("JDA of Bot")
@Description("Return the JDA instance of the bot, tu use it with skript-reflect for example.")
@Examples("set {_jda} to jda instance of bot named \"MyBot\"")
@Since("1.0")
public class ExprJDA extends SimpleExpression<JDA> {

	static {
		Skript.registerExpression(ExprJDA.class, JDA.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] jda [instance] of [the] [bot] [(named|with name)] %string%");
	}

	private Expression<String> exprName;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprName = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected JDA[] get(Event e) {
		String name = exprName.getSingle(e);
		if (name == null) return new JDA[0];
		JDA bot = BotManager.getBot(name);
		if (bot == null) return new JDA[0];
		return new JDA[] {bot};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends JDA> getReturnType() {
		return JDA.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "jda instance of bot named" + exprName.toString(e, debug);
	}

}