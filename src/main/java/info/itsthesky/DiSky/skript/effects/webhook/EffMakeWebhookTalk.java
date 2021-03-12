package info.itsthesky.DiSky.skript.effects.webhook;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.managers.WebhookManager;
import info.itsthesky.DiSky.skript.expressions.messages.ExprLastMessage;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Make Webhook Talk")
@Description("Make any registered webhook talk in his channel.")
@Examples("make webhook \"Name\" send \"Hello World :D\"")
@Since("1.0")
public class EffMakeWebhookTalk extends Effect {

    static {
        Skript.registerEffect(EffMakeWebhookTalk.class,
                "["+ Utils.getPrefixName() +"] make [the] [webhook] [(named|with name)] %string% send [message] %string/message/embed%");
    }

    private Expression<Object> exprMessage;
    private Expression<String> exprName;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) exprs[0];
        exprMessage = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        Object content = exprMessage.getSingle(e);

        if (name == null || content == null) return;
        WebhookClient webhook = WebhookManager.getClient(name);
        if (webhook == null) return;

        if (content instanceof EmbedBuilder) {
            webhook.send(
                    WebhookEmbedBuilder.fromJDA(
                            ((EmbedBuilder) content).build()
                    ).build().reduced()
            );
        } else {
            webhook.send(
                    content.toString()
            );
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make webhook named " + exprName.toString(e, debug) + " send " + exprMessage.toString(e, debug);
    }

}
