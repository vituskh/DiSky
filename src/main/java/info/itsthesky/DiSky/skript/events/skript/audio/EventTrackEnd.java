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
import info.itsthesky.DiSky.skript.events.skript.command.EventCommand;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Track End")
@Description("Fired when a track just ended in a guild.")
@Examples("on track end:")
@Since("1.6")
public class EventTrackEnd extends Event {

    static {
        Skript.registerEvent("Track End", SimpleEvent.class, EventTrackEnd.class, "[discord] (audio|track) (finish[ed]|end)");

        EventValues.registerEventValue(EventTrackEnd.class, AudioTrack.class, new Getter<AudioTrack, EventTrackEnd>() {
            @Nullable
            @Override
            public AudioTrack get(final @NotNull EventTrackEnd event) {
                return event.getTrack();
            }
        }, 0);

        EventValues.registerEventValue(EventTrackEnd.class, Guild.class, new Getter<Guild, EventTrackEnd>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventTrackEnd event) {
                return event.getGuild();
            }
        }, 0);

    }

    private final AudioTrack track;
    private final Guild guild;

    public EventTrackEnd(
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