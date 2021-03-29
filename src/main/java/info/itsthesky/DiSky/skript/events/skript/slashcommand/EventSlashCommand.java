package info.itsthesky.DiSky.skript.events.skript.slashcommand;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.skript.events.skript.command.ExprCommandArgs;
import info.itsthesky.DiSky.skript.events.skript.command.ExprCommandCore;
import info.itsthesky.DiSky.skript.events.skript.command.ExprCommandPrefix;
import info.itsthesky.DiSky.tools.StaticData;
import info.itsthesky.DiSky.tools.object.command.DiscordCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Slash Command")
@Description("Fired when a SLASH command is executed by any user. See the wiki to know how to get the options :)\nWiki: https://github.com/SkyCraft78/DiSky/wiki/Slash-Commands")
@Examples("on slash command:")
@Since("1.5")
public class EventSlashCommand extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Slash Command", SimpleEvent.class, EventSlashCommand.class, "[discord] slash command");

        EventValues.registerEventValue(EventSlashCommand.class, TextChannel.class, new Getter<TextChannel, EventSlashCommand>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventSlashCommand event) {
                return event.getEvent().getTextChannel();
            }
        }, 0);


        EventValues.registerEventValue(EventSlashCommand.class, User.class, new Getter<User, EventSlashCommand>() {
            @Nullable
            @Override
            public User get(final @NotNull EventSlashCommand event) {
                return event.getEvent().getMember().getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventSlashCommand.class, Guild.class, new Getter<Guild, EventSlashCommand>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventSlashCommand event) {
                return event.getEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventSlashCommand.class, Member.class, new Getter<Member, EventSlashCommand>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventSlashCommand event) {
                return event.getEvent().getMember();
            }
        }, 0);


    }

    private static final HandlerList HANDLERS = new HandlerList();
    private final SlashCommandEvent e;

    public EventSlashCommand(
            final SlashCommandEvent e
            ) {
        super(true);
        StaticData.lastSlashCommandEvent = e;
        this.e = e;
        e.acknowledge(true).queue();
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public SlashCommandEvent getEvent() { return this.e; }
}