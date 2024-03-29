package info.itsthesky.DiSky.skript.effects.webhooks;

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
import info.itsthesky.DiSky.managers.WebhookManager;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import info.itsthesky.DiSky.skript.audio.ExprLastAudioError;
import info.itsthesky.DiSky.skript.audio.ExprLastPlayedAudio;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.PlayError;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Event;

@Name("Register Webhooks")
@Description("Register a new webhook client, using a specific ID and its url or token.")
@Examples("register webhook \"name\" using url \"url\"")
@Since("1.8")
public class EffRegisterWebhook extends Effect {

    static {
        Skript.registerEffect(EffRegisterWebhook.class, // [the] [bot] [(named|with name)] %string%
                "["+ Utils.getPrefixName() +"] register [new] [webhook] [(with name|named)] %string% (using|with) [the] [(url|token|id)] %string%");
    }

    private Expression<String> exprName;
    private Expression<String> exprURL;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        exprURL = (Expression<String>) exprs[1];
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        String url = exprURL.getSingle(e);
        if (name == null || url == null) return;
        WebhookManager.registerWebhooks(name, url);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "register webhook named " + exprName.toString(e, debug) + " using url " + exprURL.toString(e, debug);
    }

}
