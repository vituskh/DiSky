package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

@Name("Send Typing")
@Description("Make a bot \"typing\" in a text channel. You can enable it one time, and it automatically expire after 10 seconds.")
@Examples("make bot typing in event-channel")
@Since("1.5.2")
public class EffSendTyping extends Effect {

    static {
        Skript.registerEffect(EffSendTyping.class,
                "["+ Utils.getPrefixName() +"] (make|send) [bot] typing in [the] [channel] %channel/textchannel%");
    }

    private Expression<Object> exprEntity;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprEntity = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Object entity = exprEntity.getSingle(e);
        if (entity == null) return;
        TextChannel channel = Utils.checkChannel(entity);
        if (channel != null) channel.sendTyping().queue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make the bot typing in channel " +exprEntity.toString(e, debug);
    }

}
