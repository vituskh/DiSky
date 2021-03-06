package info.itsthesky.Vixio3.skript.expressions.messages;

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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Content of Message")
@Description("Return the content (message itself) of a message instance.")
@Examples("set {_content} to message content of event-message")
@Since("1.0")
public class ExprMessageContent extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprMessageContent.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] message content of [the] [message] %message%"
		);
	}

	private Expression<Message> exprMessage;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprMessage = (Expression<Message>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(Event e) {
		Message message = exprMessage.getSingle(e);
		if (message == null) return new String[0];
		return new String[] {message.getContentDisplay()};
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
		return "content of message " + exprMessage.toString(e, debug);
	}

}