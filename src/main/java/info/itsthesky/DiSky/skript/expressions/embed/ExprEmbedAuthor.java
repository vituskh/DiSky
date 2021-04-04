package info.itsthesky.DiSky.skript.expressions.embed;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Author of Track / Embed")
@Description("Set or clear the author of an embed, and get the track's one.")
@Examples({"set author of {_embed} to \"ItsTheSky\"",
        "clear author of {_embed}",
        "set {_author} to author of last played track"
})
@Since("1.4.3")
public class ExprEmbedAuthor extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprEmbedAuthor.class, String.class,
                "[(embed|track)] author",
                "embed/track"
        );
    }

    @Nullable
    @Override
    public String convert(Object entity) {
        if (entity instanceof EmbedBuilder) {
            return ((EmbedBuilder) entity).build().getAuthor().getName();
        } else {
            return ((AudioTrack) entity).getInfo().author;
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "embed author";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        switch (mode) {
            case RESET:
                for (Object entity : getExpr().getArray(e)) {
                    if (entity instanceof AudioTrack) return;
                    EmbedBuilder embed = (EmbedBuilder) entity;
                    MessageEmbed builded = embed.build();
                    embed.setAuthor(
                            null,
                            (builded.getAuthor() == null) ? null : builded.getAuthor().getUrl(),
                            (builded.getAuthor() == null) ? null : builded.getAuthor().getIconUrl()
                    );
                }
                break;
            case SET:
                String value = delta[0].toString();
                for (Object entity : getExpr().getArray(e)) {
                    if (entity instanceof AudioTrack) return;
                    EmbedBuilder embed = (EmbedBuilder) entity;
                    MessageEmbed builded = embed.build();
                    embed.setAuthor(
                            value,
                            (builded.getAuthor() == null) ? null : builded.getAuthor().getUrl(),
                            (builded.getAuthor() == null) ? null : builded.getAuthor().getIconUrl()
                    );
                }
                break;
        }
    }
}