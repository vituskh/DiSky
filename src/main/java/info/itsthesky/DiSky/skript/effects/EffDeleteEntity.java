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
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.event.Event;

@Name("Delete Discord Entity")
@Description("Delete discord entity, such as channel, role, message, etc...")
@Examples("reply with \"This message will be removed in 2 seconds!\"\n" +
        "wait 2 second\n" +
        "delete discord entity last message")
@Since("1.2")
public class EffDeleteEntity extends Effect {

    static {
        Skript.registerEffect(EffDeleteEntity.class,
                "["+ Utils.getPrefixName() +"] delete discord entity %message/channel/textchannel/role%");
    }

    private Expression<Object> exprEntity;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprEntity = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Object entity = exprEntity.getSingle(e);
        if (entity == null) return;
        if (entity instanceof Role) {
            Role role = (Role) entity;
            role.delete().queue();
        } else if (entity instanceof Webhook) {
            Webhook webhook = (Webhook) entity;
            webhook.delete().queue();
        } else if (
                (entity instanceof Channel) ||
                        (entity instanceof TextChannel)
        ) {
            TextChannel channel = Utils.checkChannel(entity);
            if (channel == null) return;
            channel.delete().queue();
        } else if (entity instanceof Message) {
            Message message = (Message) entity;
            message.delete().queue();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "delete discord entity " + exprEntity.toString(e, debug);
    }

}
