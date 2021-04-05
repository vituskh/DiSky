package info.itsthesky.DiSky.skript.audio.tracks;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import info.itsthesky.DiSky.managers.music.GuildAudioManager;
import info.itsthesky.DiSky.skript.audio.ExprLastAudioError;
import info.itsthesky.DiSky.skript.audio.ExprLastPlayedAudio;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.PlayError;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

@Name("Skip Guild Track")
@Description("Skip the track from the specific guild.")
@Examples("skip current track of event-guild")
@Since("1.6-pre2")
public class EffSkipGuildTrack extends Effect {

    static {
        Skript.registerEffect(EffSkipGuildTrack.class, // [the] [bot] [(named|with name)] %string%
                "["+ Utils.getPrefixName() +"] skip [current] track (from|of) [the] [guild] %guild%");
    }

    private Expression<Guild> exprGuild;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprGuild = (Expression<Guild>) exprs[0];
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event e) {
        Guild guild = exprGuild.getSingle(e);
        if (guild == null) return;
        GuildAudioManager manager = AudioUtils.getGuildAudioPlayer(guild);
        manager.trackScheduler.nextTrack();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "skip current track of guild " + exprGuild.toString(e, debug);
    }

}
