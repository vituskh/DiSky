package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.skript.events.skript.EventMessageReceive;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;

@Name("Reply with Message")
@Description("Reply with a message to channel-based events.")
@Examples("reply with \"Hello World :globe_with_meridians:\"")
@Since("1.0")
public class EffReplyWith extends Effect {

    private static final List<String> allowedEvents = Arrays.asList(
            "EventMessageReceive",
            "EventBotMessageReceive",
            "EventCommand"
    );

    static {
        Skript.registerEffect(EffReplyWith.class,
                "["+ Utils.getPrefixName() +"] reply with [the] [message] %string/message/embed%");
    }

    private Expression<Object> exprMessage;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprMessage = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Object message = exprMessage.getSingle(e);
        if (message == null) return;
        if (!allowedEvents.contains(e.getEventName())) {
            DiSky.getInstance().getLogger().severe("You can't use 'reply with' effect without a discord-based event!");
            return;
        }
        JDA bot = ((EventMessageReceive) e).getChannel().getJDA();
        TextChannel channel = ((EventMessageReceive) e).getChannel();
        if (message instanceof Message) {
            bot.getTextChannelById(channel.getId()).sendMessage((Message) message).queue();
        } else if (message instanceof EmbedBuilder) {
            bot.getTextChannelById(channel.getId()).sendMessage(((EmbedBuilder) message).build()).queue();
        } else {
            bot.getTextChannelById(channel.getId()).sendMessage(message.toString()).queue();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "reply with the message " + exprMessage.toString(e, debug);
    }

}