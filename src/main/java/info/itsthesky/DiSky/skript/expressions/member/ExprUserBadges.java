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

@Name("User Badges")
@Description("Get all badges the user have. It does NOT contain the nitro badge! Available badge are:\n"+
		"STAFF\n" +
		"PARTNER\n" +
		"HYPESQUAD\n" +
		"BUG_HUNTER_LEVEL_1\n" +
		"HYPESQUAD_BRAVERY\n" +
		"HYPESQUAD_BRILLIANCE\n" +
		"HYPESQUAD_BALANCE\n" +
		"EARLY_SUPPORTER\n" +
		"TEAM_USER\n" +
		"SYSTEM\n" +
		"BUG_HUNTER_LEVEL_2\n" +
		"VERIFIED_BOT\n" +
		"VERIFIED_DEVELOPER")
@Examples("discord command badge <user>:\n" +
		"\tprefixes: !\n" +
		"\ttrigger:\n" +
		"\t\treply with \"%mention tag of arg-1% have %size of badges of arg-1% badge(s)!\"")
@Since("1.5.4")
public class ExprUserBadges extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprUserBadges.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [discord] badges of [the] [user] %user/member%");
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
		return Utils.valuesToString(user.getFlags());
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "badges of user  " + exprMember.toString(e, debug);
	}

}