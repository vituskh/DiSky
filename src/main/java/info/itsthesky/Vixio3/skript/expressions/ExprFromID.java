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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("ID of Discord entity")
@Description("Return the discord ID of a channel, user, member, role, guild, etc...")
@Examples("set {_channel} to channel with id \"731885527762075648\" in guild with id \"731502122662625310\"")
@Since("1.0")
public class ExprFromID extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprFromID.class, Object.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [text][ ][-][ ]channel with [the] id %string%",
				"["+ Utils.getPrefixName() +"] (user|member) with [the] id %string%",
				"["+ Utils.getPrefixName() +"] (guild|server) with [the] id %string%"
		);
	}

	private Expression<String> exprID;
	private Expression<Guild> exprGuild;
	private int pattern;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		pattern = matchedPattern;
		exprID = (Expression<String>) exprs[0];
		/* if (!(exprs.length == 1)) {
			exprGuild = (Expression<Guild>) exprs[1];
		} */
		return true;
	}

	@Override
	protected Object[] get(Event e) {
		String id = exprID.getSingle(e);
		JDA bot = BotManager.getFirstBot();
		Guild guild = null;
		if (!(exprGuild == null)) guild = exprGuild.getSingle(e);
		if (bot == null || id == null) return new Object[0];
		switch (pattern) {
			case 0:
				/* if (guild == null) {
					return new TextChannel[] {bot.getTextChannelById(Long.parseLong(id))};
				} else {
					return new TextChannel[] {guild.getTextChannelById(Long.parseLong(id))};
				} */
				return new TextChannel[] {bot.getTextChannelById(Long.parseLong(id))};
			case 1:
				return new User[] {bot.getUserById(Long.parseLong(id))};
			case 2:
				return new Guild[] {bot.getGuildById(Long.parseLong(id))};
		}
		return new String[0];
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "discord entity from id " + exprID.toString(e, debug);
	}

}