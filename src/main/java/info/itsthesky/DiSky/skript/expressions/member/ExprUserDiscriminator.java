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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("User Custom Status")
@Description("Get the custom status of a specific user")
@Examples("reply with \"I love your %custom status of event-user% !\"")
@Since("1.3")
public class ExprUserDiscriminator extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprUserDiscriminator.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [discord] tag of [the] [user] %user/member%");
	}

	private Expression<Object> exprMember;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprMember = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(final Event e) {
		Object entity = exprMember.getSingle(e);
		if (entity == null) return new String[0];
		User user = null;
		if (entity instanceof Member) {
			user = ((Member) entity).getUser();
		} else if (entity instanceof User) {
			user = (User) entity;
		}
		if (user == null) return new String[0];
		return new String[] {user.getDiscriminator()};
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
		return "discriminator of user  " + exprMember.toString(e, debug);
	}

}