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
import info.itsthesky.DiSky.skript.scope.role.ScopeRole;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.RoleBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;

@Name("Allow / Deny permission")
@Description("Allow or deny a discord permission to a role or a user in a channel.")
@Examples("allow administrator to public role of guild # Make every member administrator >:D")
@Since("1.4")
public class EffManagePermission extends Effect {

    static {
        Skript.registerEffect(EffManagePermission.class,
                "["+ Utils.getPrefixName() +"] allow [discord] [perm[ission]] %permissions% to %member/role/rolebuilder% in [the] [channel] %channel/textchannel%",
                "["+ Utils.getPrefixName() +"] deny [discord] [perm[ission]] %permissions% to %member/role/rolebuilder% in [the] [channel] %channel/textchannel%"
        );
    }

    private Expression<Permission> exprPerm;
    private Expression<Object> exprTarget;
    private Expression<Object> exprEntity;
    private boolean isAllow;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprPerm = (Expression<Permission>) exprs[0];
        exprTarget = (Expression<Object>) exprs[1];
        exprEntity = (Expression<Object>) exprs[2];
        isAllow = matchedPattern == 0;
        return true;
    }

    @Override
    protected void execute(Event e) {
        Permission[] perm = exprPerm.getArray(e);
        Object target = exprTarget.getSingle(e);
        Object entity = exprEntity.getSingle(e);
        if (perm == null || target == null) return;

        TextChannel channel = Utils.checkChannel(entity);
        if (channel != null ) {
            /* IF IN CHANNEL */

            if (entity instanceof Member) {
                Member member = (Member) target;
                if (isAllow) {
                    channel.createPermissionOverride(member).setAllow(perm).queue();
                } else {
                    channel.createPermissionOverride(member).setDeny(perm).queue();
                }
            } else if (entity instanceof Role) {
                Role role = (Role) target;
                if (isAllow) {
                    channel.createPermissionOverride(role).setAllow(perm).queue();
                } else {
                    channel.createPermissionOverride(role).setDeny(perm).queue();
                }
            } else if (entity instanceof RoleBuilder) {
                RoleBuilder role = (RoleBuilder) target;
                if (isAllow) {
                    role.addAllow(perm);
                    ScopeRole.lastBuilder = role;
                } else {
                    role.addDeny(perm);
                    ScopeRole.lastBuilder = role;
                }
            }


            /* END CHANNEL */
        } else {
            /* IF GLOBALLY */

            Guild guild = null;
            if (entity instanceof Member) {
                Member member = (Member) target;
                guild = member.getGuild();
            } else if (entity instanceof Role) {
                Role role = (Role) target;
                guild = role.getGuild();
            }
            if (guild == null) return;

            for (GuildChannel channel1 : guild.getChannels()) {
                if (target instanceof RoleBuilder) return;
                if (target instanceof Role) {
                    if (isAllow) {
                        channel1.createPermissionOverride((Role) target).setAllow(perm).queue();
                    } else {
                        channel1.createPermissionOverride((Role) target).setDeny(perm).queue();
                    }
                } else {
                    if (isAllow) {
                        channel1.createPermissionOverride((Member) target).setAllow(perm).queue();
                    } else {
                        channel1.createPermissionOverride((Member) target).setDeny(perm).queue();
                    }
                }
            }

            /* END GLOBALLY */
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        if (isAllow) {
            return "allow permission " + exprPerm.toString(e, debug) + " to role or user " + exprTarget.toString(e,debug);
        } else {
            return "deny permission " + exprPerm.toString(e, debug) + " to role or user " + exprTarget.toString(e,debug);
        }
    }

}
