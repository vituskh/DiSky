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
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Channel with ID")
@Description("Get and return a channel with the wanted ID")
@Examples("set {_channel} to channel with id \"693461871843606528\" via bot \"MyBot\"")
@Since("1.0")
public class ExprChannelWithID extends SimpleExpression<TextChannel> {

	static {
		Skript.registerExpression(ExprChannelWithID.class, TextChannel.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [text] channel with [the] id %string% [and] with [the] bot [with name|named] %string%");
	}

	private Expression<String> exprName;
	private Expression<String> exprID;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprID = (Expression<String>) exprs[0];
		exprName = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	protected TextChannel[] get(Event e) {
		String name = exprName.getSingle(e);
		String id = exprID.getSingle(e);
		if (name == null || id == null) return new TextChannel[0];
		JDA bot = BotManager.getBot(name);
		if (bot == null) return new TextChannel[0];
		return new TextChannel[] {bot.getTextChannelById(id)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends TextChannel> getReturnType() {
		return TextChannel.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "jda instance of bot named" + exprName.toString(e, debug);
	}

}