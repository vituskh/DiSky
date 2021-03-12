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
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.DiSky.managers.WebhookManager;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.event.Event;

@Name("Make Custom Webhook")
@Description("Make new webhook using registered webhook as base, change its name, avatar and send a message.")
@Examples("create new webhook from \"client\" with name \"Another Name :D\" and with avatar \"https://downloadwap.com/thumbs2/wallpapers/p2/2019/anime/22/m9l7p17513293259.jpg\" and make it send \"Hello World :D\"")
@Since("1.2")
public class EffSendCustomWebhook extends Effect {

    static {
        Skript.registerEffect(EffSendCustomWebhook.class,
                "["+ Utils.getPrefixName() +"] create [new] webhook from [client] [(named|with name)] %string%[(, | and)] with [new] name %string%[(, | and)] with [new] avatar %string% and make (it|the webhook) send %string/embed%");
    }

    private Expression<String> exprName;
    private Expression<String> exprNewName;
    private Expression<String> exprAvatar;
    private Expression<Object> exprMessage;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        exprNewName = (Expression<String>) exprs[1];
        exprAvatar = (Expression<String>) exprs[2];
        exprMessage = (Expression<Object>) exprs[3];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        String newName = exprNewName.getSingle(e);
        String avatar = exprAvatar.getSingle(e);
        Object entity = exprMessage.getSingle(e);
        if (name == null || newName == null || avatar == null || entity == null) return;
        WebhookClient client = WebhookManager.getClient(name);
        if (client == null) return;
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(newName);
        builder.setAvatarUrl(avatar);
        if (entity instanceof EmbedBuilder) {
            builder.addEmbeds(WebhookEmbedBuilder.fromJDA(((EmbedBuilder) entity).build()).build());
        } else {
            builder.setContent(entity.toString());
        }
        client.send(builder.build());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create new webhook from base " + exprName.toString(e, debug) + " named " + exprName.toString(e, debug) + " with avatar " + exprAvatar.toString(e, debug) + " and make it send " + exprMessage.toString(e, debug);
    }

}
