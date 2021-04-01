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
import info.itsthesky.DiSky.skript.commands.CommandEvent;
import info.itsthesky.DiSky.skript.events.skript.command.EventCommand;
import info.itsthesky.DiSky.skript.events.skript.messages.EventMessageReceive;
import info.itsthesky.DiSky.skript.events.skript.messages.EventPrivateMessage;
import info.itsthesky.DiSky.skript.events.skript.slashcommand.EventSlashCommand;
import info.itsthesky.DiSky.skript.expressions.messages.ExprLastMessage;
import info.itsthesky.DiSky.tools.DiSkyErrorHandler;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.commands.CommandHook;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.apache.commons.lang.Validate;
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
            "EventSlashCommand",
            "EventPrivateMessage",
            "CommandEvent"
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
        DiSkyErrorHandler.executeHandleCode(e, Event -> {
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
            } else if (e instanceof CommandEvent) {
                channel = (TextChannel) ((CommandEvent) e).getMessageChannel();
            } else if (e instanceof EventSlashCommand) {
            /* Slash command have their own reply system
            They're using webhook instead of user, and we
            need to use that, however Discord will wait forever for an answer :)
             */
                SlashCommandEvent event = ((EventSlashCommand) e).getEvent();
                CommandHook hook = event.getHook();
                hook.setEphemeral(true);
                if (message instanceof Message) {
                    hook.sendMessage((Message) message).queue();
                    return;
                } else if (message instanceof EmbedBuilder) {
                    // Because of a JDA's bug, we can't send embed currently via ephemeral message :c
                /* MessageEmbed embed = ((EmbedBuilder) message).build();
                System.out.println(embed);
                hook.sendMessage(embed).queue(); */
                    DiSky.getInstance().getLogger()
                            .warning("Replying with Embed in slash command are currently not supported! See our discord for more information :)");
                    hook.sendMessage(":warning: Error, see console for more information!").queue();
                    return;
                } else if (message instanceof MessageBuilder) {
                    hook.sendMessage(((MessageBuilder) message).build()).queue();
                    return;
                } else {
                    hook.sendMessage(message.toString()).queue();
                    return;
                }
            }

            boolean isFromPrivate = false;
            if (eventPrivate != null) isFromPrivate = true;

            Validate.notNull(channel, "Le channel est null");


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
            ExprLastMessage.lastMessage = storedMessage;
            if (exprVar == null) return;
            if (!exprVar.getClass().getName().equalsIgnoreCase("ch.njol.skript.lang.Variable")) return;
            Variable var = (Variable) exprVar;
            Utils.setSkriptVariable(var, storedMessage, e);
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "reply with the message " + exprMessage.toString(e, debug);
    }

}
