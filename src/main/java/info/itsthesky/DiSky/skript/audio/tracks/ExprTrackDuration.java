package info.itsthesky.DiSky.skript.audio.tracks;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.tools.object.RoleBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Date;

@Name("Track Duration")
@Description("Return the duration of a specific track")
@Examples("set {_duration} to duration of last played track.")
@Since("1.6-pre2")
public class ExprTrackDuration extends SimplePropertyExpression<AudioTrack, Date> {

    static {
        register(ExprTrackDuration.class, Date.class,
                "[discord] [audio] duration",
                "track"
        );
    }

    @Nullable
    @Override
    public Date convert(AudioTrack entity) {
        return new Date(entity.getDuration() * 1000);
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    protected String getPropertyName() {
        return "duration";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {

    }
}