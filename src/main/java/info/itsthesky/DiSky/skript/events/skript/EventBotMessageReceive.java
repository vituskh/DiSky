package info.itsthesky.DiSky.skript.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Message Bot Receive")
@Description("Run when any message is sent by a bot and the bot is able to see it.")
@Examples({"on bot message receive:",
            "\tsend message (discord id of event-user) to event-channel with event-bot"})
@Since("1.0")
public class EventBotMessageReceive extends EventMessageReceive {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Message Receive", SimpleEvent.class, EventBotMessageReceive.class, "[discord] bot message receive");

        EventValues.registerEventValue(EventBotMessageReceive.class, TextChannel.class, new Getter<TextChannel, EventBotMessageReceive>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventBotMessageReceive event) {
                return event.getChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventBotMessageReceive.class, Message.class, new Getter<Message, EventBotMessageReceive>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventBotMessageReceive event) {
                return event.getMessage();
            }
        }, 0);

        EventValues.registerEventValue(EventBotMessageReceive.class, User.class, new Getter<User, EventBotMessageReceive>() {
            @Nullable
            @Override
            public User get(final @NotNull EventBotMessageReceive event) {
                return event.getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventBotMessageReceive.class, Guild.class, new Getter<Guild, EventBotMessageReceive>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventBotMessageReceive event) {
                return event.getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventBotMessageReceive.class, Member.class, new Getter<Member, EventBotMessageReceive>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventBotMessageReceive event) {
                return event.getMember();
            }
        }, 0);

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

    private static final HandlerList HANDLERS = new HandlerList();

    private final TextChannel channel;
    private final Guild guild;
    private final User user;
    private final Message message;
    private final Member member;

    public EventBotMessageReceive(
            final TextChannel channel,
            final Member member,
            final Message message,
            final Guild guild
            ) {
        super(channel,
                member,
                message,
                guild);
        this.channel = channel;
        this.guild = guild;
        this.user = member.getUser();
        this.member = member;
        this.message = message;
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

    public TextChannel getChannel() {
        return channel;
    }
}