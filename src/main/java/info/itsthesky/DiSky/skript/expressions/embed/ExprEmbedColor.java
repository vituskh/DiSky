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
import java.awt.*;

@Name("Embed Title")
@Description("Set or clear the color of an embed. You can use any other color expression (from other addon too) as soon as the object's type return is java.awt.Color")
@Examples({"set color of {_embed} to color from rgb 255, 30, 185",
        "set color of {_embed} to color orange",
        "clear color of {_embed}"})
@Since("1.0")
public class ExprEmbedColor extends SimplePropertyExpression<EmbedBuilder, Object> {

    static {
        register(ExprEmbedColor.class, Object.class,
                "[embed] colo[u]r",
                "embed"
        );
    }

    @Nullable
    @Override
    public Color convert(EmbedBuilder embed) {
        return embed.build().getColor();
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    protected String getPropertyName() {
        return "embed color";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Object.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta[0] instanceof org.bukkit.Color) delta[0] = new Color(((org.bukkit.Color) delta[0]).asRGB());
        switch (mode) {
            case RESET:
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setColor(null);
                }
                break;
            case SET:
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setColor((Color) delta[0]);
                }
                break;
            default:
                return;
        }
    }
}