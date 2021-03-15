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
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;

@Name("ID of Discord entity")
@Description("Return the discord ID of a channel, user, member, role, guild, etc...")
@Examples("set {_id} to discord id of bot named \"test\" # To get bot's ID")
@Since("1.0")
public class ExprIdOf extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprIdOf.class, String.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] [discord] id of [the] [discord] [entity] %string/role/user/member/emote/textchannel/channel/message/guild%");
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
			return new String[] {((Guild) entity).getId()};
		} else if (entity instanceof User) {
			return new String[] {((User) entity).getId()};
		} else if (entity instanceof MessageChannel) {
			return new String[] {((MessageChannel) entity).getId()};
		} else if (entity instanceof Message) {
			return new String[] {((Message) entity).getId()};
		} else if (entity instanceof Channel) {
			return new String[] {((Channel) entity).getChannel().getId()};
		} else if (entity instanceof Role) {
			return new String[] {((Role) entity).getId()};
		} else if (entity instanceof Member) {
			return new String[] {((Member) entity).getId()};
		} else if (entity instanceof MessageReaction.ReactionEmote) {
			return new String[] {((MessageReaction.ReactionEmote) entity).getId()};
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
		return "discord id of " + exprEntity.toString(e, debug);
	}

}