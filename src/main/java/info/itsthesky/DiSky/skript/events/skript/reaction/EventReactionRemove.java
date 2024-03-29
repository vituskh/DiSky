package info.itsthesky.DiSky.skript.events.skript.reaction;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.Emote;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Name("Reaction Remove")
@Description("Run when any member remove a reaction from a message")
@Examples("on reaction remove:")
@Since("1.3")
public class EventReactionRemove extends Event {

    static {
        Skript.registerEvent("Reaction Remove", SimpleEvent.class, EventReactionRemove.class, "[discord] [guild] reaction remove");

        EventValues.registerEventValue(EventReactionRemove.class, Message.class, new Getter<Message, EventReactionRemove>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventReactionRemove event) {
                return event.getEvent().getChannel()
                        .retrieveMessageById(event.getEvent().getMessageId()).complete();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, User.class, new Getter<User, EventReactionRemove>() {
            @Nullable
            @Override
            public User get(final @NotNull EventReactionRemove event) {
                return event.getEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, Member.class, new Getter<Member, EventReactionRemove>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventReactionRemove event) {
                return event.getEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, Channel.class, new Getter<Channel, EventReactionRemove>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventReactionRemove event) {
                return new Channel(event.getEvent().getChannel());
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, TextChannel.class, new Getter<TextChannel, EventReactionRemove>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventReactionRemove event) {
                return new Channel(event.getEvent().getChannel()).getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, Guild.class, new Getter<Guild, EventReactionRemove>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventReactionRemove event) {
                return event.getEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventReactionRemove.class, info.itsthesky.DiSky.tools.object.Emote.class, new Getter<info.itsthesky.DiSky.tools.object.Emote, EventReactionRemove>() {
            @Nullable
            @Override
            public info.itsthesky.DiSky.tools.object.Emote get(final @NotNull EventReactionRemove event) {
                return event.getEvent().getReactionEmote().isEmote() ? new Emote(event.getEvent().getReactionEmote().getEmote()) : new Emote(event.getEvent().getReactionEmote().getName());
            }
        }, 0);

    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final GuildMessageReactionRemoveEvent e;

    public EventReactionRemove(
            final GuildMessageReactionRemoveEvent e
            ) {
        super(Utils.areEventAsync());
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

    public GuildMessageReactionRemoveEvent getEvent() {
        return e;
    }
}