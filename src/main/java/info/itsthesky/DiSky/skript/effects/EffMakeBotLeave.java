package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.DiSkyErrorHandler;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.event.Event;

@Name("Make Bot Leave Guild")
@Description("Make the specific bot leave the specific guild.")
@Examples("make bot named \"MyBot\" leave event-guild")
@Since("1.8")
public class EffMakeBotLeave extends Effect {

    static {
        Skript.registerEffect(EffMakeBotLeave.class,
                "["+ Utils.getPrefixName() +"] make [the] [bot] [(named|with name)] %string% leave [the] [guild] %guild%");
    }

    private Expression<String> exprName;
    private Expression<Guild> exprGuild;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        exprGuild = (Expression<Guild>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        DiSkyErrorHandler.executeHandleCode(e, Event -> {
            Guild guild = exprGuild.getSingle(e);
            String name = exprName.getSingle(e);
            if (name == null || guild == null) return;
            if (Utils.areJDASimilar(guild.getJDA(), name)) return;
            guild.leave().queue();
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make bot named " + exprGuild.toString(e, debug) + " leave guild " + exprGuild.toString(e, debug);
    }

}
