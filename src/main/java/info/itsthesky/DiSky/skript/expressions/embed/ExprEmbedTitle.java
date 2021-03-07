package info.itsthesky.DiSky.skript.expressions.embed;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Embed Title")
@Description("Set or clear the title of an embed.")
@Examples({"set embed title of {_embed} to \"The addon's developer link\"",
        "clear embed title of {_embed}"})
@Since("1.0")
public class ExprEmbedTitle extends SimplePropertyExpression<EmbedBuilder, String> {

    static {
        register(ExprEmbedTitle.class, String.class,
                "[embed] title",
                "embed"
        );
    }

    @Nullable
    @Override
    public String convert(EmbedBuilder areaStyle) {
        return areaStyle.getFields().getClass().getName();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "embed title";
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
        switch (mode) {
            case RESET:
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setTitle(null);
                }
                break;
            case SET:
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setTitle((String) delta[0]);
                }
                break;
            default:
                return;
        }
    }
}