package info.itsthesky.DiSky.skript.expressions.embed;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.tools.object.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Embed or Slash Command Description")
@Description("Set or clear the description of an embed or a slash command. Use %nl% to make new line in the description.")
@Examples({"set desc of {_embed} to \"This is the better description I ever saw :joy:\"",
        "clear desc of {_embed}",
        "set description of command to \"This is an awesome command!\""})
@Since("1.0")
public class ExprEmbedDescription extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprEmbedDescription.class, String.class,
                "[(embed|command)] (desc|description)",
                "embed/commandbuilder"
        );
    }

    @Nullable
    @Override
    public String convert(Object entity) {
        if (entity instanceof EmbedBuilder) {
            return ((EmbedBuilder) entity).build().getDescription();
        } else if (entity instanceof SlashCommand) {
            return ((SlashCommand) entity).getName();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "embed / slash command description";
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
                    if (entity instanceof EmbedBuilder) {
                        ((EmbedBuilder) entity).setDescription(null);
                    }
                }
                break;
            case SET:
                String value = delta[0].toString();
                if (value.length() > 2048) {
                    DiSky.getInstance().getLogger()
                            .warning("The embed's description cannot be bigger than 2048 characters. The one you're trying to set is '"+value.length()+"' length!");
                    return;
                }
                for (Object entity : getExpr().getArray(e)) {
                    if (entity instanceof EmbedBuilder) {
                        ((EmbedBuilder) entity).setDescription(value);
                    } else if (entity instanceof SlashCommand) {
                        ((SlashCommand) entity).setDescription(value);
                    }
                }
                break;
        }
    }
}