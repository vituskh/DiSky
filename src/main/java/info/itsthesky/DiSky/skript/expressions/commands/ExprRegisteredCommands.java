package info.itsthesky.DiSky.skript.expressions.commands;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.skript.commands.CommandFactory;
import info.itsthesky.DiSky.skript.commands.CommandObject;
import info.itsthesky.DiSky.tools.MultiplyPropertyExpression;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("Registered Commands")
@Description("Get all discord command registered in the server (minecraft server, not the guild)")
@Since("1.7")
public class ExprRegisteredCommands extends SimpleExpression<CommandObject> {

    static {
        Skript.registerExpression(ExprRegisteredCommands.class, CommandObject.class, ExpressionType.SIMPLE,
                "(registered|all) [discord] (command[s]|cmd[s])"
        );
    }


    @Nullable
    @Override
    protected CommandObject[] get(Event e) {
        List<CommandObject> objects = new ArrayList<>();
        CommandFactory.getInstance().commandMap.forEach((cmd, cmd1) -> objects.add(cmd1));
        return objects.toArray(new CommandObject[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends CommandObject> getReturnType() {
        return CommandObject.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "all discord commands";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}