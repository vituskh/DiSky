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
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Name("Message Delete")
@Description("Run when any message is deleted")
@Examples("on message delete:")
@Since("1.3")
public class EventMessageDelete extends Event {

    static {
        Skript.registerEvent("Message Delete", SimpleEvent.class, EventMessageDelete.class, "[discord] [guild] message delete");

        EventValues.registerEventValue(EventMessageDelete.class, Message.class, new Getter<Message, EventMessageDelete>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventMessageDelete event) {
                return event.getEvent().getChannel()
                        .getHistory().getMessageById(event.getEvent().getMessageIdLong());
            }
        }, 0);

        EventValues.registerEventValue(EventMessageDelete.class, User.class, new Getter<User, EventMessageDelete>() {
            @Nullable
            @Override
            public User get(final @NotNull EventMessageDelete event) {
                return event.getEvent().getChannel()
                        .getHistory().getMessageById(event.getEvent().getMessageIdLong()).getAuthor();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageDelete.class, Member.class, new Getter<Member, EventMessageDelete>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventMessageDelete event) {
                return event.getEvent().getGuild().getMember(
                        event.getEvent().getChannel()
                                .getHistory().getMessageById(event.getEvent().getMessageIdLong()).getAuthor());
            }
        }, 0);

        EventValues.registerEventValue(EventMessageDelete.class, Channel.class, new Getter<Channel, EventMessageDelete>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventMessageDelete event) {
                return new Channel(event.getEvent().getChannel());
            }
        }, 0);

        EventValues.registerEventValue(EventMessageDelete.class, TextChannel.class, new Getter<TextChannel, EventMessageDelete>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventMessageDelete event) {
                return event.getEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventMessageDelete.class, Guild.class, new Getter<Guild, EventMessageDelete>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventMessageDelete event) {
                return event.getEvent().getGuild();
            }
        }, 0);

    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final GuildMessageDeleteEvent e;

    public EventMessageDelete(
            final GuildMessageDeleteEvent e
            ) {
        super(true);
        this.e = e;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GuildMessageDeleteEvent getEvent() {
        return e;
    }
}