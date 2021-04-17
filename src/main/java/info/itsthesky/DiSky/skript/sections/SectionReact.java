package info.itsthesky.DiSky.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import ch.njol.util.Pair;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.skript.events.skript.reaction.EventReactionAdd;
import info.itsthesky.DiSky.tools.DiSkyErrorHandler;
import info.itsthesky.DiSky.tools.EffectSection;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.Emote;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.bukkit.event.Event;

@Name("React to Message")
@Description("React to a message with an emote like the 'add reaction' effect. However, this section will be fired when someone react to this emote too.")
@Examples("react to event-message with reaction \"smile\"")
@Since("1.8")
public class SectionReact extends EffectSection {

	static {
		Skript.registerCondition(SectionReact.class,
				"["+ Utils.getPrefixName() +"] react to [the] [message] %message% with [emote] %emote% [using [the] [bot] [(named|with name)] %-string%] [to run]"
		);
	}

	private Expression<Emote> exprReact;
	private Expression<Message> exprMessage;
	private Expression<String> exprName;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		exprMessage = (Expression<Message>) exprs[0];
		exprReact = (Expression<Emote>) exprs[1];
		if (exprs.length != 2) exprName = (Expression<String>) exprs[2];
		if (checkIfCondition()) return false;
		if (hasSection()) loadSection("react effect", false, EventReactionAdd.class);
		return true;
	}

	@Override
	public void execute(Event event) {
		DiSkyErrorHandler.executeHandleCode(event, e -> {
			Message message = exprMessage.getSingle(event);
			Emote emote = exprReact.getSingle(event);
			if (message == null || emote == null) return;
			if (exprName != null) {
				JDA msgJDA = message.getJDA();
				JDA botJDA = BotManager.getBot(exprName.getSingle(event));
				if (botJDA == null) return;
				if (msgJDA != botJDA) return;
			}

			Object map = Variables.removeLocals(event);
			VariablesMaps.map.put(event, map);
			Variables.setLocalVariables(event, map);

			if (emote.isEmote()) {
				message.addReaction(emote.getEmote()).queue(null, DiSkyErrorHandler::logException);
			} else {
				message.addReaction(emote.getName()).queue(null, DiSkyErrorHandler::logException);
			}

			JDA botJDA = message.getJDA();

			final Long msgID = message.getIdLong();
			final TextChannel channel = message.getTextChannel();
			ReactListener.events.add(new ReactListener.ReactWaitingEvent(
					ev -> msgID.equals(ev.getMessageIdLong())
							&& ev.getChannel().equals(channel)
							&& !botJDA.getSelfUser().getId().equals(ev.getUser().getId())
							&& Utils.areEmojiSimilar(ev.getReactionEmote(), emote),
					ev -> {
						if (VariablesMaps.map.get(event) != null) Variables.setLocalVariables(event, VariablesMaps.map.get(event));
						runSection(event);
						Variables.removeLocals(event);
					}
			));
		});
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "react to message" + exprMessage.toString(e, debug) + " with reaction " + exprReact.toString(e, debug);
	}
}