package info.itsthesky.DiSky.skript.expressions.member;

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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Event;

@Name("Member Nickname")
@Description("Return the nickname of specific member, and if not set return the default user name")
@Examples("reply with \"Hello %discord nickname of event-member% !\"")
@Since("1.3")
public class ExprMemberNickname extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprMemberNickname.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] discord nick([-]|[ ])name of [the] [member] %member%");
	}

	private Expression<Member> exprMember;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprMember = (Expression<Member>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(final Event e) {
		Member member = exprMember.getSingle(e);
		if (member == null) return new String[0];
		return new String[] {member.getNickname() == null ? member.getUser().getName() : member.getNickname()};
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
		return "discord nickname of member  " + exprMember.toString(e, debug);
	}

}