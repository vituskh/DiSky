package info.itsthesky.DiSky.skript.events.bukkit;

import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.skript.events.skript.*;
import info.itsthesky.DiSky.skript.events.skript.command.EventCommand;
import info.itsthesky.DiSky.skript.events.skript.guild.EventGuildBan;
import info.itsthesky.DiSky.skript.events.skript.guild.EventGuildUnban;
import info.itsthesky.DiSky.skript.events.skript.members.EventMemberBoost;
import info.itsthesky.DiSky.skript.events.skript.members.EventMemberJoin;
import info.itsthesky.DiSky.skript.events.skript.members.EventMemberLeave;
import info.itsthesky.DiSky.skript.events.skript.messages.EventMessageDelete;
import info.itsthesky.DiSky.skript.events.skript.messages.MessageEdit.EventMessageEdit;
import info.itsthesky.DiSky.skript.events.skript.messages.EventMessageReceive;
import info.itsthesky.DiSky.skript.events.skript.messages.EventPrivateMessage;
import info.itsthesky.DiSky.skript.events.skript.nickname.EventNickChange;
import info.itsthesky.DiSky.skript.events.skript.reaction.EventReactionAdd;
import info.itsthesky.DiSky.skript.events.skript.reaction.EventReactionRemove;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.command.DiscordCommand;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class JDAListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        List<Event> event = new ArrayList<>();

        if (e.isFromGuild()) {
            /* Message receive */
            event.add(new EventMessageReceive(e));
            // ***
            /* Command event */
            DiscordCommand discordCommand = new DiscordCommand(e.getMessage().getContentRaw());
            if (!e.isWebhookMessage()) {
                event.add(new EventCommand(
                        discordCommand,
                        e
                ));
            }
        } else {
            if (e.getAuthor().isBot()) return;
            event.add(new EventPrivateMessage(e));
        }
        event.forEach((event1) -> {
            Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(event1));
        });
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        Event event;
        if (e.getUser().isBot() && e.getUser().getId().equals(BotManager.getFirstBot().getSelfUser().getId())) {
           event = new EventBotJoin(
                    BotManager.getFirstBotName(),
                    e.getGuild()
            );
        } else {
            event = new EventMemberJoin(
                    e.getMember(),
                    e.getGuild()
            );
        }
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(event));
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
        Event event = new EventMemberLeave(
                e.getMember(),
                e.getGuild()
        );
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(event));
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventReactionAdd(e)));
    }
    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventReactionRemove(e)));
    }
    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventNickChange(e)));
    }
    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventMessageDelete(e)));
    }

    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventMemberBoost(e)));
    }


    @Override
    public void onGuildBan(GuildBanEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventGuildBan(e)));
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent e) {
        Utils.sync(() -> DiSky.getInstance().getServer().getPluginManager().callEvent(new EventGuildUnban(e)));
    }

}
