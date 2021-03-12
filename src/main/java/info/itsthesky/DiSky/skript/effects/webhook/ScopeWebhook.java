package info.itsthesky.DiSky.skript.effects.webhook;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.EffectSection;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.internal.entities.WebhookImpl;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.Random;

@Name("Webhook Builder")
@Description("This builder allow you to make webhook easily")
@Since("1.2")
@Examples("")
public class ScopeWebhook extends EffectSection {

    public static Webhook lastWebhook;
    private Expression<Object> exprChannel;

    static {
        Skript.registerCondition(ScopeWebhook.class, "make [new] [discord] webhook in [channel] %channel/textchannel%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition()) {
            return false;
        }
        if (!hasSection()) {
            return false;
        }
        loadSection(true);
        exprChannel = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Object channelObj = exprChannel.getSingle(e);
        TextChannel channel = Utils.checkChannel(channelObj);
        if (channel == null) return;
        lastWebhook = channel
                .createWebhook("DiSky Webhook's Builder N°" + new Random().nextInt(50))
                .complete();
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new discord webhook in channel " + exprChannel.toString(e, debug);
    }

}
