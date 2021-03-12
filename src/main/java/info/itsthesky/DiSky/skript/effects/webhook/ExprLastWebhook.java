package info.itsthesky.DiSky.skript.effects.webhook;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Last Webhook")
@Description("This expression returns the last generated webhook using the webhook builder.")
@Since("1.2")
public class ExprLastWebhook extends SimpleExpression<Webhook> {

    static {
        Skript.registerExpression(ExprLastWebhook.class, Webhook.class, ExpressionType.SIMPLE,
                "[the] [last] [(generated|created)] webhook"
        );
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Nullable
    @Override
    protected Webhook[] get(Event e) {
        return new Webhook[]{ScopeWebhook.lastWebhook};
    }

    @Override
    public Class<? extends Webhook> getReturnType() {
        return Webhook.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the last generated webhook";
    }
}
