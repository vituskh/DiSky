package info.itsthesky.DiSky.skript.expressions.emotes;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.dv8tion.jda.api.entities.MessageReaction;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Emoji Unicode")
@Description("Get the unicode code of an emoji, return null if it's an emote.")
@Since("1.3")
public class ExprEmoteUnicode extends SimplePropertyExpression<MessageReaction.ReactionEmote, String> {

    static {
        register(ExprEmoteUnicode.class, String.class,
                "emote unicode [code]",
                "emote"
        );
    }

    @Nullable
    @Override
    public String convert(MessageReaction.ReactionEmote emote) {
        if (emote.isEmote()) return null;
        return emote.getAsCodepoints();
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