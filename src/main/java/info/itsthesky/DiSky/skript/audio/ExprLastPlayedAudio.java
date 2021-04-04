package info.itsthesky.DiSky.skript.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.tools.Utils;
import org.bukkit.event.Event;

@Name("Last Played Audio")
@Description("Return the last played audio, if the 'play' effect worked fine.")
@Since("1.6-pre2")
public class ExprLastPlayedAudio extends SimpleExpression<AudioTrack> {

	static {
		Skript.registerExpression(ExprLastPlayedAudio.class, AudioTrack.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] last played (audio|track)");
	}

	public static AudioTrack lastTrack;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected AudioTrack[] get(final Event e) {
		return new AudioTrack[] {lastTrack};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends AudioTrack> getReturnType() {
		return AudioTrack.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "last played audio";
	}

}