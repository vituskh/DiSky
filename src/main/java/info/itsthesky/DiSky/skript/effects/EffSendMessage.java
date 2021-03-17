package info.itsthesky.DiSky.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.skript.expressions.messages.ExprLastMessage;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

@Name("Register new Discord Bot")
@Description("Register and load a new discord bot from a token and with a specific Name." +
        "\nYou need to follow Discord's developer instruction in order to generate a new bot with a token")
@Examples("login to \"TOKEN\" with name \"MyBot\"")
@Since("1.0")
public class EffSendMessage extends Effect {

    static {
        Skript.registerEffect(EffSendMessage.class,
                "["+ Utils.getPrefixName() +"] send [message] %string/message/embed% to [the] [(user|channel)] %user/member/textchannel/channel% [with [the] bot [(named|with name)] %string%] [and store it in %-object%]");
    }

    private Expression<Object> exprMessage;
    private Expression<Object> exprChannel;
    private Expression<Object> exprVar;
    private Expression<String> exprName;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprMessage = (Expression<Object>) exprs[0];
        exprChannel = (Expression<Object>) exprs[1];
        if (exprs.length == 2) return true;
        exprName = (Expression<String>) exprs[2];
        if (exprs.length == 3) return true;
        exprVar = (Expression<Object>) exprs[3];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        Object channel = exprChannel.getSingle(e);
        Object content = exprMessage.getSingle(e);
        if (channel == null || content == null) return;
        Message storedMessage;

        TextChannel channel1 = null;
        if (channel instanceof TextChannel) {
            channel1 = (TextChannel) channel;
        } else if (channel instanceof Channel) {
            channel1 = ((Channel) channel).getTextChannel();
        } else if (
                channel instanceof User ||
                        channel instanceof Member
        ) {
            User user;
            if (channel instanceof Member) {
                user = ((Member) channel).getUser();
            } else {
                user = (User) channel;
            }
            if (content instanceof EmbedBuilder) {
                storedMessage = user.openPrivateChannel()
                        .flatMap(channel2 -> channel2.sendMessage(((EmbedBuilder) content).build()))
                        .complete();
            } else {
                storedMessage = user.openPrivateChannel()
                        .flatMap(channel2 -> channel2.sendMessage(content.toString()))
                        .complete();
            }
            ExprLastMessage.lastMessage = storedMessage;
            if (exprVar == null) return;
            if (!exprVar.getClass().getName().equalsIgnoreCase("ch.njol.skript.lang.Variable")) return;
            Variable var = (Variable) exprVar;
            Utils.setSkriptVariable(var, storedMessage, e);
        } else return;

        if (channel1 == null) return;
        JDA bot = null;
        if (name != null) {
            bot = BotManager.getBot(name);
        } else {
            bot = channel1.getJDA();
        }
        if (bot == null) return;

        if (content instanceof EmbedBuilder) {
            storedMessage = bot.getTextChannelById(channel1.getId()).sendMessage(((EmbedBuilder) content).build()).complete();
        } else {
            storedMessage = bot.getTextChannelById(channel1.getId()).sendMessage(content.toString()).complete();
        }
        ExprLastMessage.lastMessage = storedMessage;
        if (exprVar == null) return;
        if (!exprVar.getClass().getName().equalsIgnoreCase("ch.njol.skript.lang.Variable")) return;
        Variable var = (Variable) exprVar;
        Utils.setSkriptVariable(var, storedMessage, e);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send discord message " + exprMessage.toString(e, debug) + " to channel or user " + exprChannel.toString(e, debug) + " with bot named " + exprName.toString(e, debug);
    }

}
