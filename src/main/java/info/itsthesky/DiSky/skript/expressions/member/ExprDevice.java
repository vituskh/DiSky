package info.itsthesky.DiSky.skript.expressions.member;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Member Device")
@Description("Get the device the member is on (desktop, web, mobile, etc...)")
@Examples("")
@Since("1.6")
public class ExprDevice extends SimplePropertyExpression<Member, String> {

    static {
        register(ExprDevice.class, String.class,
                "[discord] [member] device [status]",
                "member"
        );
    }

    @Nullable
    @Override
    public String convert(Member entity) {
        if (!entity.getOnlineStatus(ClientType.DESKTOP).equals(OnlineStatus.OFFLINE)) return "Desktop";
        if (!entity.getOnlineStatus(ClientType.MOBILE).equals(OnlineStatus.OFFLINE)) return "Mobile";
        if (!entity.getOnlineStatus(ClientType.WEB).equals(OnlineStatus.OFFLINE)) return "Web";
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "device status";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {

    }
}