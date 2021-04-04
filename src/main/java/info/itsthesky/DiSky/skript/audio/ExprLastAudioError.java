package info.itsthesky.DiSky.skript.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.PlayError;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

@Name("Last Audio Error")
@Description("Return the last audio error produced by a 'play' effect.")
@Since("1.6")
public class ExprLastAudioError extends SimpleExpression<PlayError> {

	static {
		Skript.registerExpression(ExprLastAudioError.class, PlayError.class, ExpressionType.SIMPLE,
				"["+ Utils.getPrefixName() +"] [the] last [audio] play error");
	}

	public static PlayError lastError;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected PlayError[] get(final Event e) {
		return new PlayError[] {lastError};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends PlayError> getReturnType() {
		return PlayError.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "last audio play error";
	}

}