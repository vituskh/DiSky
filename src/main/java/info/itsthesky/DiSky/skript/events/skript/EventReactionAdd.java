package info.itsthesky.DiSky.skript.events.skript;

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
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Name("Reaction Add")
@Description("Run when any message receive a reaction, as long as the bot can see it.")
@Examples({"on reaction add:",
            "\treply with mention tag of event-user"})
@Since("1.0")
public class EventReactionAdd extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Reaction Add", SimpleEvent.class, EventReactionAdd.class, "[discord] reaction add [to message]");

        EventValues.registerEventValue(EventReactionAdd.class, TextChannel.class, new Getter<TextChannel, EventReactionAdd>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventReactionAdd event) {
                return event.getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, Channel.class, new Getter<Channel, EventReactionAdd>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventReactionAdd event) {
                return event.getChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, String.class, new Getter<String, EventReactionAdd>() {
            @Nullable
            @Override
            public String get(final @NotNull EventReactionAdd event) {
                return event.getReaction();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, User.class, new Getter<User, EventReactionAdd>() {
            @Nullable
            @Override
            public User get(final @NotNull EventReactionAdd event) {
                return event.getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, Guild.class, new Getter<Guild, EventReactionAdd>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventReactionAdd event) {
                return event.getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, Member.class, new Getter<Member, EventReactionAdd>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventReactionAdd event) {
                return event.getMember();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, MessageReaction.ReactionEmote.class, new Getter<MessageReaction.ReactionEmote, EventReactionAdd>() {
            @Nullable
            @Override
            public MessageReaction.ReactionEmote get(final @NotNull EventReactionAdd event) {
                return event.e.getReactionEmote();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionAdd.class, Message.class, new Getter<Message, EventReactionAdd>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventReactionAdd event) {
                return event.getMessage();
            }
        }, 0);

    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final Channel channel;
    private final Guild guild;
    private final User user;
    private final Member member;
    private final String reaction;
    private final Message message;
    private final GuildMessageReactionAddEvent e;

    public EventReactionAdd(
            final GuildMessageReactionAddEvent e
            ) {
        super(true);
        this.channel = new Channel(e.getChannel());
        this.guild = e.getGuild();
        this.user = e.getMember().getUser();
        this.member = e.getMember();
        this.message = e.getChannel().getHistory().getMessageById(e.getMessageId());
        this.reaction = e.getReactionEmote().getName();
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

    public Message getMessage() {
        return message;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
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
    public GuildMessageReactionAddEvent getEvent() {
        return e;
    }

    public String getReaction() {
        return reaction;
    }

    public User getUser() {
        return user;
    }
}