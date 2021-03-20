package info.itsthesky.DiSky.skript.events.skript.messages;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Name("Message Receive")
@Description("Run when any message is sent and the bot is able to see it.")
@Examples({"on message receive:",
            "\tsend message (discord id of event-user) to event-channel with event-bot"})
@Since("1.0")
public class EventMessageReceive extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Message Receive", SimpleEvent.class, EventMessageReceive.class, "[discord] message receive");

        EventValues.registerEventValue(EventMessageReceive.class, TextChannel.class, new Getter<TextChannel, EventMessageReceive>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventMessageReceive event) {
                return event.getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageReceive.class, Channel.class, new Getter<Channel, EventMessageReceive>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventMessageReceive event) {
                return event.getChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageReceive.class, Message.class, new Getter<Message, EventMessageReceive>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventMessageReceive event) {
                return event.getMessage();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageReceive.class, User.class, new Getter<User, EventMessageReceive>() {
            @Nullable
            @Override
            public User get(final @NotNull EventMessageReceive event) {
                return event.getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageReceive.class, Guild.class, new Getter<Guild, EventMessageReceive>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventMessageReceive event) {
                return event.getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageReceive.class, Member.class, new Getter<Member, EventMessageReceive>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventMessageReceive event) {
                return event.getMember();
            }
        }, 0);

    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final Channel channel;
    private final Guild guild;
    private final User user;
    private final Member member;
    private final Message message;
    private final MessageReceivedEvent e;

    public EventMessageReceive(
            final MessageReceivedEvent e
            ) {
        super(true);
        this.channel = new Channel(e.getTextChannel());
        this.guild = e.getGuild();
        if (!e.isWebhookMessage()) {
            this.user = e.getMember().getUser();
            this.member = e.getMember();
        } else {
            this.user = null;
            this.member = null;
        }
        this.message = e.getMessage();
        this.e = e;
    }

    public Member getMember() {
        return member;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public MessageReceivedEvent getEvent() {
        return e;
    }

    public TextChannel getTextChannel() {
        return channel.getTextChannel();
    }

    public Channel getChannel() {
        return channel;
    }

    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }
}