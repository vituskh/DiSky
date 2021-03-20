package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.skript.events.skript.command.EventCommand;
import info.itsthesky.DiSky.skript.events.skript.messages.EventMessageReceive;
import info.itsthesky.DiSky.skript.events.skript.messages.EventPrivateMessage;
import info.itsthesky.DiSky.skript.expressions.messages.ExprLastMessage;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;

@Name("Reply with Message")
@Description("Reply with a message to channel-based events (work with private message too!). You can get the sent message using 'and store it in {_var}' pattern!")
@Examples("reply with \"Hello World :globe_with_meridians:\"")
@Since("1.0")
public class EffReplyWith extends Effect {

    private static final List<String> allowedEvents = Arrays.asList(
            "EventMessageReceive",
            "EventBotMessageReceive",
            "EventCommand",
            "EventPrivateMessage"
    );

    static {
        Skript.registerEffect(EffReplyWith.class,
                "["+ Utils.getPrefixName() +"] reply with [the] [message] %string/message/messagebuilder/embed% [and store it in %-object%]");
    }

    private Expression<Object> exprMessage;
    private Expression<Object> exprVar;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (exprs.length == 1) {
            exprMessage = (Expression<Object>) exprs[0];
        } else {
            exprMessage = (Expression<Object>) exprs[0];
            exprVar = (Expression<Object>) exprs[1];
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        Object message = exprMessage.getSingle(e);
        if (message == null) return;
        if (!allowedEvents.contains(e.getEventName())) {
            DiSky.getInstance().getLogger().severe("You can't use 'reply with' effect without a discord guild based event!");
            return;
        }
        TextChannel channel = null;
        Message storedMessage = null;

        EventPrivateMessage eventPrivate = null;
        if (e.getEventName().equalsIgnoreCase("EventPrivateMessage")) {
            eventPrivate = (EventPrivateMessage) e;
        } else if (e instanceof EventMessageReceive) {
            channel = ((EventMessageReceive) e).getTextChannel();
        } else if (e instanceof EventCommand) {
            channel = (TextChannel) ((EventCommand) e).getEvent().getChannel();
        }

        boolean isFromPrivate = false;
        if (eventPrivate != null) isFromPrivate = true;

        
        if (message instanceof Message) {
            if (isFromPrivate) {
                eventPrivate
                        .getEvent()
                        .getPrivateChannel()
                        .sendMessage((Message) message).queue();
            } else {
                channel.getJDA()
                        .getTextChannelById(
                                channel.getId()
                        ).sendMessage((Message) message).queue();
            }
        } else if (message instanceof EmbedBuilder) {
            if (isFromPrivate) {
                eventPrivate
                        .getEvent()
                        .getPrivateChannel()
                        .sendMessage(((EmbedBuilder) message).build()).queue();
            } else {
                channel.getJDA()
                        .getTextChannelById(
                                channel.getId()
                        ).sendMessage(((EmbedBuilder) message).build()).queue();
            }
        }  else if (message instanceof MessageBuilder) {
            if (isFromPrivate) {
                eventPrivate
                        .getEvent()
                        .getPrivateChannel()
                        .sendMessage(((MessageBuilder) message).build()).queue();
            } else {
                channel.getJDA()
                        .getTextChannelById(
                                channel.getId()
                        ).sendMessage(((MessageBuilder) message).build()).queue();
            }
        } else {
            if (isFromPrivate) {
                storedMessage = eventPrivate
                        .getEvent()
                        .getPrivateChannel()
                        .sendMessage(message.toString()).complete();
            } else {
                storedMessage = channel.getJDA()
                        .getTextChannelById(
                                channel.getId()
                        ).sendMessage(message.toString()).complete();
            }
        }
        if (exprVar == null) return;
        if (!exprVar.getClass().getName().equalsIgnoreCase("ch.njol.skript.lang.Variable")) return;
        Variable var = (Variable) exprVar;
        Utils.setSkriptVariable(var, storedMessage, e);
        ExprLastMessage.lastMessage = storedMessage;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "reply with the message " + exprMessage.toString(e, debug);
    }

}
