package info.itsthesky.DiSky.skript.events.skript.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Track Start")
@Description("Fired when a track just started in a guild.")
@Examples("on track start:")
@Since("1.6")
public class EventTrackStart extends Event {

    static {
        Skript.registerEvent("Track Start", SimpleEvent.class, EventTrackStart.class, "[discord] (audio|track) start");

        EventValues.registerEventValue(EventTrackStart.class, AudioTrack.class, new Getter<AudioTrack, EventTrackStart>() {
            @Nullable
            @Override
            public AudioTrack get(final @NotNull EventTrackStart event) {
                return event.getTrack();
            }
        }, 0);

        EventValues.registerEventValue(EventTrackStart.class, Guild.class, new Getter<Guild, EventTrackStart>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventTrackStart event) {
                return event.getGuild();
            }
        }, 0);

    }
    
    private final AudioTrack track;
    private final Guild guild;

    public EventTrackStart(
            final AudioTrack track,
            final Guild guild
            ) {
        super(Utils.areEventAsync());
        this.guild = guild;
        this.track = track;
    }

    public AudioTrack getTrack() {
        return track;
    }

    public Guild getGuild() {
        return guild;
    }

    private static final HandlerList HANDLERS = new HandlerList();
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}