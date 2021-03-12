package info.itsthesky.DiSky.skript.effects.webhook;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.WebhookManager;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.event.Event;

@Name("Register new Discord Webhook")
@Description("Register and load a new discord webhook from a webhook builder and with a specific Name." +
        "\nTo create one, go to your channel's settings and manage them in 'Integrations' tab, or use the webhook maker!")
@Examples("register webhook with name \"client\"")
@Since("1.2")
public class EffRegisterWebhook extends Effect {

    static {
        Skript.registerEffect(EffRegisterWebhook.class,
                "["+ Utils.getPrefixName() +"] register [new] [discord] [webhook] [with] [the] [builder] %string/webhookbuilder% [and] with [the] (name|id) %string%");
    }

    private Expression<String> exprName;
    private Expression<Object> exprWebhook;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprWebhook = (Expression<Object>) exprs[0];
        exprName = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        Object entity = exprWebhook.getSingle(e);
        if (name == null || entity == null) return;
        if (entity instanceof Webhook) {
            WebhookManager.addClient(name, (Webhook) entity);
        } else {
            WebhookManager.addClient(name, entity.toString());
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "register new discord webhook from builder or url " + exprWebhook.toString(e, debug) + " with name " + exprName.toString(e, debug);
    }

}
