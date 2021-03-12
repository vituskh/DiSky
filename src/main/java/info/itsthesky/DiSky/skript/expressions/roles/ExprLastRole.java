package info.itsthesky.DiSky.skript.expressions.roles;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Last Role")
@Description("Return the last role made via a role builder.")
@Since("1.1")
public class ExprLastRole extends SimpleExpression<Role> {

    static {
        Skript.registerExpression(ExprLastRole.class, Role.class, ExpressionType.SIMPLE,
                "[the] [last] [(generated|created)] role"
        );
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Nullable
    @Override
    protected Role[] get(Event e) {
        return new Role[]{ScopeRole.lastRole};
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the last generated role";
    }
}
