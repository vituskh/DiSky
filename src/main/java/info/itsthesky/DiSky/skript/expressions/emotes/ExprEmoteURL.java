package info.itsthesky.DiSky.skript.expressions.emotes;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageReaction;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Emoji Image")
@Description("Get the image URL of an emote, and if it's an emoji get his mention.")
@Examples("reply with \"The best emote is %emote name of event-emote%\"!")
@Since("1.3")
public class ExprEmoteURL extends SimplePropertyExpression<MessageReaction.ReactionEmote, String> {

    static {
        register(ExprEmoteURL.class, String.class,
                "emote (image|img)",
                "emote"
        );
    }

    @Nullable
    @Override
    public String convert(MessageReaction.ReactionEmote emote) {
        if (emote.isEmote())
            return emote.getEmote().getImageUrl();
        return emote.getEmoji();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "emote image";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        return;
    }
}