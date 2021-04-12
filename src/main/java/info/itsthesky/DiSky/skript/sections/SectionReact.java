package info.itsthesky.DiSky.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.skript.events.skript.reaction.EventReactionAdd;
import info.itsthesky.DiSky.tools.EffectSection;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

@Name("React to Message")
@Description("React to a message with an emote like the 'add reaction' effect. However, this section will be fired when someone react to this emote too.")
@Examples("react to event-message with \"\uD83D\uDE00\"")
@Since("1.8")
public class SectionReact extends EffectSection {

	static {
		Skript.registerCondition(SectionReact.class,
				"["+ Utils.getPrefixName() +"] react to [the] [message] %message% with [emote] %string% [using [the] [bot] [(named|with name)] %-string%] [to run]"
		);
	}
	private Expression<String> exprReact;
	private Expression<Message> exprMessage;
	private Expression<String> exprName;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		exprMessage = (Expression<Message>) exprs[0];
		exprReact = (Expression<String>) exprs[1];
		if (exprs.length != 2) exprName = (Expression<String>) exprs[2];
		if (checkIfCondition()) return false;
		if (hasSection()) loadSection("react effect", false, EventReactionAdd.class);
		return true;
	}

	@Override
	public void execute(Event event) {
		Message message = exprMessage.getSingle(event);
		String react = exprReact.getSingle(event);
		if (message == null || react == null) return;
		if (exprName != null) {
			JDA msgJDA = message.getJDA();
			JDA botJDA = BotManager.getBot(exprName.getSingle(event));
			if (botJDA == null) return;
			if (msgJDA != botJDA) return;
		}
		message.addReaction(react).queue();
		final Long msgID = message.getIdLong();
		final TextChannel channel = message.getTextChannel();
		ReactListener.events.add(new ReactListener.ReactWaitingEvent(
				e -> msgID.equals(e.getMessageIdLong())
						&& e.getChannel().equals(channel)
						&& !e.getUser().isBot()
						&& e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete().getAuthor().equals(message.getAuthor())
						&& e.getReaction().getReactionEmote().getAsReactionCode().equalsIgnoreCase(react),
				() -> runSection(event)
		));
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "react to message" + exprMessage.toString(e, debug) + " with reaction " + exprReact.toString(e, debug);
	}
}