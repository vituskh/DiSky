package info.itsthesky.DiSky.skript.expressions.roles;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.EffectSection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Role Builder")
@Description("This builder allow you to create new role in a specific guild easily.")
@Since("1.0")
@Examples("on discord command:\n" +
        "\t\"%event-prefix%\" is \"!\"\n" +
        "\t\"%event-command%\" is \"say\"\n" +
        "\tmake new embed:\n" +
        "\t\tset title of embed to \"The title\"\n" +
        "\t\tset color of embed to color from rgb 50, 30, 26\n" +
        "\t\tset description of embed to \"The description\"\n" +
        "\treply with last created embed")
public class ScopeRole extends EffectSection {

    public static Role lastRole;

    static {
        Skript.registerCondition(ScopeRole.class, "make [new] [discord] [guild] role in [the] [guild] %guild%");
    }

    Expression<Guild> exprGuild;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition()) {
            return false;
        }
        if (!hasSection()) {
            return false;
        }
        this.exprGuild = (Expression<Guild>) exprs[0];
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        Guild guild = exprGuild.getSingle(e);
        if (guild == null) return;
        try {
            lastRole = guild.createRole().complete(true);
        } catch (RateLimitedException rateLimitedException) {
            rateLimitedException.printStackTrace();
        }
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new discord role in guild " + exprGuild.toString(e, debug);
    }

}
