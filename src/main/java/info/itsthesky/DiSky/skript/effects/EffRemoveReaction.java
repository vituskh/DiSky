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
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.skript.events.skript.EventReactionAdd;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;

@Name("Remove Reaction")
@Description("Remove a specific reaction, from user on message.")
@Examples({
        "on reaction add:",
        "\tremove event-emote added by event-user from event-message"
})
@Since("1.1")
public class EffRemoveReaction extends Effect {

    static {
        Skript.registerEffect(EffRemoveReaction.class,
                "["+ Utils.getPrefixName() +"] (remove|delete) %emote% added by %user% from %message%");
    }

    private Expression<Emote> exprEmote;
    private Expression<User> exprUser;
    private Expression<Message> exprMessage;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprEmote = (Expression<Emote>) exprs[0];
        this.exprUser = (Expression<User>) exprs[1];
        this.exprMessage = (Expression<Message>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Emote emote = exprEmote.getSingle(e);
        User user = exprUser.getSingle(e);
        Message message = exprMessage.getSingle(e);
        if (emote == null || user == null || message == null) return;
        message.removeReaction(emote, user).queue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "remove the event-reaction from the user";
    }

}
