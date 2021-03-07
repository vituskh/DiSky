package info.itsthesky.DiSky.skript.expressions;

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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Name of Discord entity")
@Description("Return the discord name of a channel, user, member, role, guild, etc...")
@Examples("set {_name} to discord name of channel with id \"731885527762075648\"")
@Since("1.0")
public class ExprNameOf extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprNameOf.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [discord] name of [the] [discord] [entity] %string/user/textchannel/guild%");
	}

	private Expression<Object> exprEntity;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		exprEntity = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	protected String[] get(final Event e) {
		Object entity = exprEntity.getSingle(e);
		if (entity instanceof JDA) {
			final JDA bot = BotManager.getBot(entity.toString());
			if (bot == null)
				return new String[0];
			entity = bot.getSelfUser();
		}

		if (entity instanceof Guild) {
			return new String[] {((Guild) entity).getName()};
		} else if (entity instanceof User) {
			return new String[] {((User) entity).getName()};
		} else if (entity instanceof MessageChannel) {
			return new String[] {((MessageChannel) entity).getName()};
		}

		return new String[0];
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
		return "discord if of " + exprEntity.toString(e, debug);
	}

}