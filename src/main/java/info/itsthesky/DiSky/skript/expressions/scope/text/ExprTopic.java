package info.itsthesky.DiSky.skript.expressions.scope.text;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.skript.scope.textchannels.ScopeTextChannel;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.TextChannelBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Topic of a text channel (or builder)")
@Description("Get or set the topic of text channel or builder.")
@Examples("set topic of text channel builder to \"I love custom topic :heart:\"")
@Since("1.4")
public class ExprTopic extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprTopic.class, String.class,
                "topic",
                "channel/textchannel/textchannelbuilder"
        );
    }

    @Nullable
    @Override
    public String convert(Object entity) {
        TextChannel textChannel = Utils.checkChannel(entity);
        if (textChannel == null) {
            if (entity instanceof TextChannelBuilder) {
                TextChannelBuilder channel = (TextChannelBuilder) entity;
                return channel.getTopic();
            }
        } else {
            return textChannel.getTopic();
        }
        return "";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "topic";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        String topic = delta[0].toString();
        if (mode == Changer.ChangeMode.SET) {
            for (Object entity : getExpr().getArray(e)) {
                TextChannel textChannel = Utils.checkChannel(entity);
                if (textChannel == null) {
                    if (entity instanceof TextChannelBuilder) {
                        TextChannelBuilder channel = (TextChannelBuilder) entity;
                        channel.setTopic(topic);
                        ScopeTextChannel.lastBuilder.setTopic(topic);
                    }
                } else {
                    textChannel
                            .getManager()
                            .setTopic(topic)
                            .queue();
                }
            }
        }
    }
}