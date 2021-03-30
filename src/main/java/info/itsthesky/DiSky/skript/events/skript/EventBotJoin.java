package info.itsthesky.DiSky.skript.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Bot Join Guild")
@Description("Fired when a bot join any guild where the bot is in.")
@Examples({"on bot join guild:",
            "\tsend message \"I'm the better bot ever, made with Vixio3 by Sky!\" to channel with id \"750449611302371469\""})
@Since("1.0")
public class EventBotJoin extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Bot Join", SimpleEvent.class, EventBotJoin.class, "[discord] bot join (guild|server)");

        EventValues.registerEventValue(EventBotJoin.class, String.class, new Getter<String, EventBotJoin>() {
            @Nullable
            @Override
            public String get(final @NotNull EventBotJoin event) {
                return event.getBot();
            }
        }, 0);

        EventValues.registerEventValue(EventBotJoin.class, Guild.class, new Getter<Guild, EventBotJoin>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventBotJoin event) {
                return event.getGuild();
            }
        }, 0);

    }

    public Guild getGuild() {
        return guild;
    }

    public String getBot() {
        return bot;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final Guild guild;
    private final String bot;

    public EventBotJoin(
            final String bot,
            final Guild guild
            ) {
        super(Utils.areEventAsync());
        this.guild = guild;
        this.bot = bot;
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