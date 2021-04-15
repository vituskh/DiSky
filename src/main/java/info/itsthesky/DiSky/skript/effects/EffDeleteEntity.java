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
import info.itsthesky.DiSky.tools.DiSkyErrorHandler;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;

@Name("Delete Discord Entity")
@Description("Delete discord entity, such as channel, role, message, etc...")
@Examples("discord command tempMessage <text>:\n" +
        "\tprefixes: $\n" +
        "\ttrigger:\n" +
        "\t\treply with \"The message you want me to say is: `%arg 1%`\" and store it in {_msg}\n" +
        "\t\twait 5 second\n" +
        "\t\tdelete discord entity {_msg}")
@Since("1.2")
public class EffDeleteEntity extends Effect {

    static {
        Skript.registerEffect(EffDeleteEntity.class,
                "["+ Utils.getPrefixName() +"] delete discord entity %messages/channels/voicechannel/textchannels/roles%");
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
        DiSkyErrorHandler.executeHandleCode(e, Event -> {
            Object[] entity = exprEntity.getArray(e);
            if (entity.length == 0) return;
            for (Object en : entity) {
                if (en instanceof Role) {
                    Role role = (Role) en;
                    role.delete().queue(null, DiSkyErrorHandler::logException);
                } else if (en instanceof Webhook) {
                    Webhook webhook = (Webhook) en;
                    webhook.delete().queue(null, DiSkyErrorHandler::logException);
                } else if (en instanceof TextChannel) {
                    ((TextChannel) en).delete().queue(null, DiSkyErrorHandler::logException);
                } else if (en instanceof GuildChannel && ((GuildChannel) en).getType().equals(ChannelType.TEXT)) {
                    ((TextChannel) en).delete().queue(null, DiSkyErrorHandler::logException);
                } else if (en instanceof Message) {
                    Message message = (Message) en;
                    message.delete().queue(null, DiSkyErrorHandler::logException);
                }
            }
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "delete discord entity " + exprEntity.toString(e, debug);
    }

}
