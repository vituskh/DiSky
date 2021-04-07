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
import info.itsthesky.DiSky.tools.object.VoiceChannelBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Category Parent of a text / voice channel (or builder)")
@Description("Get or set the parent of text / voice channel or builder.")
@Examples("set parent of text channel builder to category with id \"818182471442890769\"")
@Since("1.4")
public class ExprCategoryParent extends SimplePropertyExpression<Object, Category> {

    static {
        register(ExprCategoryParent.class, Category.class,
                "[text] [channel] parent",
                "channel/textchannel/textchannelbuilder/voicechannel/voicechannelbuilder"
        );
    }

    @Nullable
    @Override
    public Category convert(Object entity) {
        if (entity instanceof VoiceChannel) return ((VoiceChannel) entity).getParent();
        if (entity instanceof VoiceChannelBuilder) return ((VoiceChannelBuilder) entity).getParent();
        TextChannel textChannel = Utils.checkChannel(entity);
        if (textChannel == null) {
            if (entity instanceof TextChannelBuilder) {
                TextChannelBuilder channel = (TextChannelBuilder) entity;
                return channel.getCategory();
            }
        } else {
            return textChannel.getParent();
        }
        return null;
    }

    @Override
    public Class<? extends Category> getReturnType() {
        return Category.class;
    }

    @Override
    protected String getPropertyName() {
        return "channel parent";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Object.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        if (!(delta[0] instanceof Category)) return;
        Category category = (Category) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Object entity : getExpr().getArray(e)) {

                if (entity instanceof VoiceChannelBuilder) ((VoiceChannelBuilder) entity).setParent(category);
                if (entity instanceof VoiceChannel) ((VoiceChannel) entity).getManager().setParent(category).queue();

                TextChannel textChannel = Utils.checkChannel(entity);
                if (textChannel == null) {
                    if (entity instanceof TextChannelBuilder) {
                        TextChannelBuilder channel = (TextChannelBuilder) entity;
                        channel.setCategory(category);
                        ScopeTextChannel.lastBuilder.setCategory(category);
                    }
                } else {
                    textChannel
                            .getManager()
                            .setParent(category)
                            .queue();
                }
            }
        }
    }
}