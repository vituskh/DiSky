package info.itsthesky.DiSky.skript.events.bukkit;

import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.skript.events.skript.*;
import info.itsthesky.DiSky.skript.events.skript.command.EventCommand;
import info.itsthesky.DiSky.skript.events.skript.nickname.EventNickChange;
import info.itsthesky.DiSky.tools.object.command.DiscordCommand;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
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
            event.add(new EventMessageReceive(
                    new Channel(e.getTextChannel()),
                    e.getMember(),
                    e.getMessage(),
                    e.getGuild(),
                    e
            ));
            // ***
            /* Command event */
            DiscordCommand discordCommand = new DiscordCommand(e.getMessage().getContentRaw());
            event.add(new EventCommand(
                    new Channel(e.getTextChannel()),
                    e.getMember(),
                    e.getMessage(),
                    e.getGuild(),
                    discordCommand
            ));
        } else {
            if (e.getAuthor().isBot()) return;
            event.add(new EventPrivateMessage(e));
        }
        event.forEach((event1) -> {
            DiSky.getInstance().getServer().getPluginManager().callEvent(event1);
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
        DiSky.getInstance().getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
        Event event = new EventMemberLeave(
                e.getMember(),
                e.getGuild()
        );
        DiSky.getInstance().getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        DiSky.getInstance().getServer().getPluginManager().callEvent(new EventReactionAdd(e));
    }
    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        DiSky.getInstance().getServer().getPluginManager().callEvent(new EventReactionRemove(e));
    }
    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent e) {
        DiSky.getInstance().getServer().getPluginManager().callEvent(new EventNickChange(e));
    }
    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
        DiSky.getInstance().getServer().getPluginManager().callEvent(new EventMessageDelete(e));
    }

    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent e) {
        DiSky.getInstance().getServer().getPluginManager().callEvent(new EventMemberBoost(e));
    }

}
