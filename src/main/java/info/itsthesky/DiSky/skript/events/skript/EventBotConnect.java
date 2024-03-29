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
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Bot Connect")
@Description("Fired when a bot is loading using the 'login to ...' effect.")
@Examples("on bot load:")
@Since("1.6")
public class EventBotConnect extends Event {

    static {
        Skript.registerEvent("Bot Load", SimpleEvent.class, EventBotConnect.class, "[discord] bot (load|connect)");

        EventValues.registerEventValue(EventBotConnect.class, JDA.class, new Getter<JDA, EventBotConnect>() {
            @Nullable
            @Override
            public JDA get(final @NotNull EventBotConnect event) {
                return event.getBot();
            }
        }, 0);
    }

    public JDA getBot() {
        return bot;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final JDA bot;

    public EventBotConnect(
            final JDA bot) {
        super(Utils.areEventAsync());
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