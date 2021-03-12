package info.itsthesky.DiSky.skript.expressions.roles;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.DiSky;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.*;

@Name("Role Color")
@Description("Set or clear the color of a role. You can use any other color expression (from other addon too) as soon as the object's type return is java.awt.Color")
@Examples({"set color of role to orange",
        "clear color of role"})
@Since("1.0")
public class ExprRoleColor extends SimplePropertyExpression<Role, Object> {

    static {
        register(ExprRoleColor.class, Object.class,
                "[role] colo[u]r",
                "role"
        );
    }

    @Nullable
    @Override
    public Color convert(Role role) {
        return role.getColor();
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    protected String getPropertyName() {
        return "role color";
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
        if (delta == null || delta.length == 0) return;
        Color finalColor = null;

        if (delta[0] instanceof org.bukkit.Color) {
            org.bukkit.Color tempBukkit = (org.bukkit.Color) delta[0];
            finalColor = new Color(tempBukkit.getRed(), tempBukkit.getGreen(), tempBukkit.getBlue());
        } else if (delta[0] instanceof ch.njol.skript.util.Color) {
            ch.njol.skript.util.Color tempSkript = (ch.njol.skript.util.Color) delta[0];
            finalColor = new Color(tempSkript.asBukkitColor().getRed(), tempSkript.asBukkitColor().getGreen(), tempSkript.asBukkitColor().getBlue());
        } else if (delta[0] instanceof Color) {
            finalColor = (Color) delta[0];
        }

        if (finalColor == null) {
            DiSky.getInstance().getLogger().severe("Cannot parse the color '"+delta[0]+"', which is not from Skript, Bukkit or Java object! (From class: " + delta[0].getClass().getName());
            return;
        }

        switch (mode) {
            case RESET:
                for (Role role : getExpr().getArray(e)) {
                    role.getManager().setColor(null).queue();
                    ScopeRole.lastRole = role;
                }
                break;
            case SET:
                for (Role role : getExpr().getArray(e)) {
                    role.getManager().setColor(finalColor).queue();
                    ScopeRole.lastRole = role;
                }
                break;
            default:
                return;
        }
    }
}