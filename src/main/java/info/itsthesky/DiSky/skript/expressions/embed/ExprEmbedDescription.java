package info.itsthesky.DiSky.skript.expressions.embed;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.DiSky;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Embed Description")
@Description("Set or clear the description of an embed. Use %nl% to make new line in the description.")
@Examples({"set desc of {_embed} to \"This is the better description I ever saw :joy:\"",
        "clear desc of {_embed}"})
@Since("1.0")
public class ExprEmbedDescription extends SimplePropertyExpression<EmbedBuilder, String> {

    static {
        register(ExprEmbedDescription.class, String.class,
                "[embed] (desc|description)",
                "embed"
        );
    }

    @Nullable
    @Override
    public String convert(EmbedBuilder embed) {
        return embed.build().getDescription();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "embed description";
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
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setDescription(null);
                }
                break;
            case SET:
                String value = delta[0].toString();
                if (value.length() > 2048) {
                    DiSky.getInstance().getLogger()
                            .warning("The embed's description cannot be bigger than 2048 characters. The one you're trying to set is '"+value.length()+"' length!");
                    return;
                }
                for (EmbedBuilder embed : getExpr().getArray(e)) {
                    embed.setDescription(value);
                }
                break;
        }
    }
}