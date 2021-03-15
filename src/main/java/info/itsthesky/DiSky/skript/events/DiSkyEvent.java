/*package info.itsthesky.DiSky.skript.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class DiSkyEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final GenericEvent event;

    public DiSkyEvent(GenericEvent e) {
        super(true);
        this.event = e;
    }

    public <T> void registerEventValue(final T object, final Function<GenericEvent, T> function) {
        EventValues.registerEventValue(DiSkyEvent.class, object.getClass(), new Getter<T, GenericEvent>() {
            @Nullable
            @Override
            public T get(final @NotNull GenericEvent event) {
                return function.apply(event);
            }
        }, 0);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public E getEvent() {
        return event;
    }
}*/