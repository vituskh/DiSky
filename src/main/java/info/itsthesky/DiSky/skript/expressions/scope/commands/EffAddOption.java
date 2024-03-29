package info.itsthesky.DiSky.skript.expressions.scope.commands;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.SlashCommand;
import net.dv8tion.jda.api.entities.Command;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction;
import org.bukkit.event.Event;

@Name("Add Slash Command Option")
@Description("Add an (require or not) option to a specific command builder. See OptionType for possible sort of input!")
@Examples({"register require option USER with id \"user\" and description \"The 'USER' word is an option type.\" to command",
        "register option STRING with id \"text\" and description \"This option is not require\" to command"})
@Since("1.5")
public class EffAddOption extends Effect {

    static {
        Skript.registerEffect(EffAddOption.class,
                "[" + Utils.getPrefixName() + "] register option [with type] %optiontype% with (id|name) %string% [and] [with] [(desc|description)] %string% to [command] [builder] %commandbuilder%",
                "[" + Utils.getPrefixName() + "] register require option [with type] %optiontype% with (id|name) %string% [and] [with] [(desc|description)] %string% to [command] [builder] %commandbuilder%"
        );
    }

    private boolean isRequire;
    private Expression<Command.OptionType> exprType;
    private Expression<String> exprName;
    private Expression<String> exprDesc;
    private Expression<SlashCommand> exprBuilder;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprType = (Expression<Command.OptionType>) exprs[0];
        exprName = (Expression<String>) exprs[1];
        exprDesc = (Expression<String>) exprs[2];
        exprBuilder = (Expression<SlashCommand>) exprs[3];
        isRequire = matchedPattern != 0;
        return true;
    }

    @Override
    protected void execute(Event e) {
        Command.OptionType type = exprType.getSingle(e);
        String name = exprName.getSingle(e);
        String desc = exprDesc.getSingle(e);
        SlashCommand builder = exprBuilder.getSingle(e);
        if (name == null || desc == null || type == null || builder == null) return;
        builder.addOption(
                new CommandUpdateAction.OptionData(type, name, desc)
                        .setRequired(isRequire)
        );
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add option with type " + exprType.toString(e, debug) + " named " + exprName.toString(e, debug) + " with description " + exprDesc.toString(e, debug) + " to builder " + exprBuilder.toString(e, debug);
    }

}
