package info.itsthesky.DiSky.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.BotManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Name("Online Status")
@Description("Get a member online status, and set if for a bot.")
@Examples("set online status of \"MyBot\" to idle")
@Since("1.6")
public class ExprOnlineStatus extends SimplePropertyExpression<Object, OnlineStatus> {

    static {
        register(ExprOnlineStatus.class, OnlineStatus.class,
                "[discord] online status",
                "member/string/bot"
        );
    }

    @Nullable
    @Override
    public OnlineStatus convert(Object entity) {
        if (entity instanceof Member) {
            return ((Member) entity).getOnlineStatus();
        } else if (entity instanceof String) {
            JDA bot = BotManager.getBot(entity.toString());
            if (bot == null) return null;
            return bot.getPresence().getStatus();
        } else if (entity instanceof JDA) {
            return ((JDA) entity).getPresence().getStatus();
        }
        return null;
    }

    @Override
    public Class<? extends OnlineStatus> getReturnType() {
        return OnlineStatus.class;
    }

    @Override
    protected String getPropertyName() {
        return "online status";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Object.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || !(delta[0] instanceof OnlineStatus)) return;
        OnlineStatus status = (OnlineStatus) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Object entity : getExpr().getArray(e)) {
                if (entity instanceof String) {
                    JDA bot = BotManager.getBot(entity.toString());
                    if (bot == null) return;
                    bot.getPresence().setStatus(status);
                } else if (entity instanceof JDA) {
                    ((JDA) entity).getPresence().setStatus(status);
                }
            }
        }
    }
}