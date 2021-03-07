package info.itsthesky.DiSky.skript.expressions.embed.scope;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.EffectSection;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Embed Builder")
@Description("This builder allow you to make embed easily")
@Since("1.0")
@Examples("on discord command:\n" +
        "\t\"%event-prefix%\" is \"!\"\n" +
        "\t\"%event-command%\" is \"say\"\n" +
        "\tmake new embed:\n" +
        "\t\tset title of embed to \"The title\"\n" +
        "\t\tset color of embed to color from rgb 50, 30, 26\n" +
        "\t\tset description of embed to \"The description\"\n" +
        "\treply with last created embed")
public class ScopeEmbed extends EffectSection {

    public static EmbedBuilder lastEmbed;

    static {
        Skript.registerCondition(ScopeEmbed.class, "make [new] [discord] [message] embed");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition()) {
            return false;
        }
        if (!hasSection()) {
            return false;
        }
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        lastEmbed = new EmbedBuilder();
        runSection(e);

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new discord embed";
    }

}
