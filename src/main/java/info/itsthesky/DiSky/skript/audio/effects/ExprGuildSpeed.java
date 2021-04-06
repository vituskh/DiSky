package info.itsthesky.DiSky.skript.audio.effects;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.github.natanbc.lavadsp.timescale.TimescalePcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

@Name("Guild Speed")
@Description("Get or change the speed of a guild. (Of course, the audio speed)")
@Examples("set speed of event-guild to 1.3")
@Since("1.6-pre2")
public class ExprGuildSpeed extends SimplePropertyExpression<Guild, Number> {

    static {
        register(ExprGuildSpeed.class, Number.class,
                "[discord] [audio] [guild] speed",
                "guild"
        );
    }

    @Nullable
    @Override
    public Number convert(Guild guild) {
        AudioPlayer player = AudioUtils.getGuildAudioPlayer(guild).getPlayer();
        AtomicReference<Double> speed = new AtomicReference<>(1.0);
        player.setFilterFactory((track, format, output)->{
            TimescalePcmAudioFilter timescale = new TimescalePcmAudioFilter(output, format.channelCount, format.sampleRate);
            speed.set(timescale.getSpeed());
            return Collections.singletonList(timescale);
        });
        return speed.get();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "guild volume";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (
                mode.equals(Changer.ChangeMode.ADD) ||
                        mode.equals(Changer.ChangeMode.REMOVE) ||
                        mode.equals(Changer.ChangeMode.SET)
        ) {
            return CollectionUtils.array(Number.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        Number value = (Number) delta[0];
        switch (mode) {
            case SET:
                for (Guild guild : getExpr().getArray(e)) {
                    AudioUtils.getGuildAudioPlayer(guild).getPlayer().setFilterFactory((track, format, output)->{
                        TimescalePcmAudioFilter audioFilter = new TimescalePcmAudioFilter(output, format.channelCount, format.sampleRate);
                        audioFilter.setSpeed(value.doubleValue());
                        return Collections.singletonList(audioFilter);
                    });
                }
                break;
            case ADD:
                for (Guild guild : getExpr().getArray(e)) {
                    AudioPlayer player = AudioUtils.getGuildAudioPlayer(guild).getPlayer();
                    player.setFilterFactory((track, format, output)->{
                        TimescalePcmAudioFilter timescale = new TimescalePcmAudioFilter(output, format.channelCount, format.sampleRate);
                        timescale.setSpeed(value.doubleValue() + timescale.getSpeed());
                        return Collections.singletonList(timescale);
                    });
                }
                break;
            case REMOVE:
                for (Guild guild : getExpr().getArray(e)) {
                    AudioPlayer player = AudioUtils.getGuildAudioPlayer(guild).getPlayer();
                    player.setFilterFactory((track, format, output)->{
                        TimescalePcmAudioFilter timescale = new TimescalePcmAudioFilter(output, format.channelCount, format.sampleRate);
                        timescale.setSpeed(timescale.getSpeed() - value.doubleValue());
                        return Collections.singletonList(timescale);
                    });
                }
                break;
        }
    }
}