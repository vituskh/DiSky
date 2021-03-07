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
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

@Name("Register new Discord Bot")
@Description("Register and load a new discord bot from a token and with a specific Name." +
        "\nYou need to follow Discord's developer instruction in order to generate a new bot with a token")
@Examples("login to \"TOKEN\" with name \"MyBot\"")
@Since("1.0")
public class EffSendMessage extends Effect {

    static {
        Skript.registerEffect(EffSendMessage.class,
                "["+ Utils.getPrefixName() +"] send message %string/message/embed% to [the] [channel] %textchannel% with [the] bot [(named|with name)] %string%");
    }

    private Expression<Object> exprMessage;
    private Expression<TextChannel> exprChannel;
    private Expression<String> exprName;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprMessage = (Expression<Object>) exprs[0];
        exprChannel = (Expression<TextChannel>) exprs[1];
        exprName = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        TextChannel channel = exprChannel.getSingle(e);
        Object content = exprMessage.getSingle(e);
        if (name == null || channel == null || content == null) return;
        JDA bot = BotManager.getBot(name);
        if (bot == null) return;
        if (content instanceof EmbedBuilder) {
            bot.getTextChannelById(channel.getId()).sendMessage(((EmbedBuilder) content).build()).queue();
        } else {
            bot.getTextChannelById(channel.getId()).sendMessage(content.toString()).queue();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send discord message " + exprMessage.toString(e, debug) + " to channel " + exprChannel.toString(e, debug) + " with bot named " + exprName.toString(e, debug);
    }

}
