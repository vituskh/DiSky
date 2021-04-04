package info.itsthesky.DiSky.skript.audio.tracks;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.Date;

@Name("Track Position")
@Description("Return the position of a specific track")
@Examples("set {_position} to position of last played track.")
@Since("1.6-pre2")
public class ExprTrackPosition extends SimplePropertyExpression<AudioTrack, Date> {

    static {
        register(ExprTrackPosition.class, Date.class,
                "[discord] [audio] position",
                "track"
        );
    }

    @Nullable
    @Override
    public Date convert(AudioTrack entity) {
        return new Date(entity.getPosition() * 1000);
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    protected String getPropertyName() {
        return "position";
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