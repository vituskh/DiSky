package info.itsthesky.DiSky.skript.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Member Join Guild")
@Description("Fired when a user join any guild where the bot is in.")
@Examples({"on member join guild:",
            "\tsend message \"**Welcome to the server, `%name of event-user%`!**\" to channel with id \"750449611302371469\""})
@Since("1.0")
public class EventMemberJoin extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Member Join", SimpleEvent.class, EventMemberJoin.class, "[discord] (user|member) join (guild|server)");

        EventValues.registerEventValue(EventMemberJoin.class, User.class, new Getter<User, EventMemberJoin>() {
            @Nullable
            @Override
            public User get(final @NotNull EventMemberJoin event) {
                return event.getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventMemberJoin.class, Guild.class, new Getter<Guild, EventMemberJoin>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventMemberJoin event) {
                return event.getGuild();
            }
        }, 0);

    }

    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final Guild guild;
    private final User user;

    public EventMemberJoin(
            final User user,
            final Guild guild
            ) {
        super(true);
        this.guild = guild;
        this.user = user;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}