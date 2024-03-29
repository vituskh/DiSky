package info.itsthesky.DiSky.skript.expressions.messages;

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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.event.Event;

@Name("Mentioned Users")
@Description("Return all users who are mentioned in a message.")
@Examples("set {_users::*} to mentioned users in event-message")
@Since("1.4.2")
public class ExprMessageMentionedUsers extends SimpleExpression<Member> {

	static {
		Skript.registerExpression(ExprMessageMentionedUsers.class, Member.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] mentioned (users|members) in [the] [message] %message%"
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
	protected Member[] get(Event e) {
		Message message = exprMessage.getSingle(e);
		if (message == null) return new Member[0];
		return message.getMentionedMembers().toArray(new Member[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Member> getReturnType() {
		return Member.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "mentioned users of message " + exprMessage.toString(e, debug);
	}

}