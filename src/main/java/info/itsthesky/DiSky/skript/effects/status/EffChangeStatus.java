package info.itsthesky.DiSky.skript.effects.status;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import org.bukkit.event.Event;

@Name("Change Online Status")
@Description("Change the online status of the bot. Possible online status are:" +
        "\nONLINE" +
        "\nIDLE" +
        "\nDO_NOT_DISTURB" +
        "\nOFFLINE")
@Examples("change status of bot \"MyBot\" to ONLINE")
@Since("1.0")
public class EffChangeStatus extends Effect {

    static {
        Skript.registerEffect(EffChangeStatus.class,
                "["+ Utils.getPrefixName() +"] change [online] status of [the] [bot] %string% to [status] idle",
                "["+ Utils.getPrefixName() +"] change [online] status of [the] [bot] %string% to [status] (offline|invisible)",
                "["+ Utils.getPrefixName() +"] change [online] status of [the] [bot] %string% to [status] online",
                "["+ Utils.getPrefixName() +"] change [online] status of [the] [bot] %string% to [status] do not disturb"
                );
    }

    private Expression<String> exprName;
    private OnlineStatus valueStats;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        if (matchedPattern == 0) {
            valueStats = OnlineStatus.IDLE;
        } else if (matchedPattern == 1) {
            valueStats = OnlineStatus.OFFLINE;
        } else if (matchedPattern == 2) {
            valueStats = OnlineStatus.ONLINE;
        } else if (matchedPattern == 3) {
            valueStats = OnlineStatus.DO_NOT_DISTURB;
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        JDA bot = BotManager.getBot(name);
        if (name == null || bot == null) return;
        bot.getPresence().setStatus(valueStats);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "change online status of bot named " + exprName.toString(e, debug) + " to " + valueStats;
    }

}
