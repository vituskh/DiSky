package info.itsthesky.DiSky.skript.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.PlayError;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Event;

@Name("Play Audio")
@Description("Play an URL or an input (if it's only text, DiSky will search the input within Youtube). Also connect to bot to the member channel.\nCould send multiple error, see 'Audio Player Error' for more information.")
@Examples("play \"escanor ost remix\" in event-guild to event-member")
@Since("1.6")
public class EffPlayAudio extends Effect {

    static {
        Skript.registerEffect(EffPlayAudio.class, // [the] [bot] [(named|with name)] %string%
                "["+ Utils.getPrefixName() +"] play [input] %string% in [the] [guild] %guild% to [the] [member] %member%");
    }

    private Expression<String> exprInput;
    private Expression<Guild> exprGuild;
    private Expression<Member> exprMember;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprInput = (Expression<String>) exprs[0];
        exprGuild = (Expression<Guild>) exprs[1];
        exprMember = (Expression<Member>) exprs[2];
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event e) {
        String input = exprInput.getSingle(e);
        Guild guild = exprGuild.getSingle(e);
        Member member = exprMember.getSingle(e);
        if (input == null || guild == null || member == null) return;
        AudioTrack[] tracks = AudioUtils.search(input);
        ExprLastAudioError.lastError = PlayError.NONE;
        ExprLastPlayedAudio.lastTrack = null;
        if (tracks == null || tracks.length == 0) {
            ExprLastAudioError.lastError = PlayError.NOT_FOUND;
            return;
        }
        if (member.getVoiceState().getChannel() == null) {
            ExprLastAudioError.lastError = PlayError.MEMBER_NOT_CONNECTED;
            return;
        }
        AudioUtils.play(guild, AudioUtils.getGuildAudioPlayer(guild), tracks[0], member);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "play input " + exprInput.toString(e, debug) + " in guild " + exprGuild.toString(e, debug);
    }

}
