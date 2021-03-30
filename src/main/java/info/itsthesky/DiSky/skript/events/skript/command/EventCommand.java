package info.itsthesky.DiSky.skript.events.skript.command;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.command.Arguments;
import info.itsthesky.DiSky.tools.object.command.Command;
import info.itsthesky.DiSky.tools.object.command.DiscordCommand;
import info.itsthesky.DiSky.tools.object.command.Prefix;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Discord Command")
@Description("Fired when a command is executed by any user. Have a prefix, a core command and some args")
@Examples({"on discord command:\n" +
        "\tprefix is \"!\"\n" +
        "\tcore is \"slowmode\"\n" +
        "\tset {_time} to first element of arguments\n" +
        "\treply with \"%{_time}%\"\n" +
        "\tif {_time} is not set:\n" +
        "\t\treply with \":x: You have t define a new time!\"\n" +
        "\t\tstop\n" +
        "\tset slowmode of event-channel to (\"%{_time}%\" parsed as number)\n" +
        "\treply with \":v: Slow mode changed!\""
})
@Since("1.0")
public class EventCommand extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Discord Command", SimpleEvent.class, EventCommand.class, "[discord] command");

        EventValues.registerEventValue(EventCommand.class, TextChannel.class, new Getter<TextChannel, EventCommand>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventCommand event) {
                return event.getEvent().getMessage().getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Channel.class, new Getter<Channel, EventCommand>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventCommand event) {
                return new Channel(event.getEvent().getMessage().getChannel());
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Message.class, new Getter<Message, EventCommand>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventCommand event) {
                return event.getEvent().getMessage();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, User.class, new Getter<User, EventCommand>() {
            @Nullable
            @Override
            public User get(final @NotNull EventCommand event) {
                return event.getEvent().getMember().getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Guild.class, new Getter<Guild, EventCommand>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventCommand event) {
                return event.getEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Member.class, new Getter<Member, EventCommand>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventCommand event) {
                return event.getEvent().getMember();
            }
        }, 0);


    }

    private static final HandlerList HANDLERS = new HandlerList();
    private final MessageReceivedEvent e;

    public EventCommand(
            final MessageReceivedEvent e
            ) {
        super(Utils.areEventAsync());
        this.e = e;
        DiscordCommand command = new DiscordCommand(e.getMessage().getContentRaw());
        ExprCommandCore.commandCore = command.getCommand().getValue();
        ExprCommandPrefix.commandPrefix = command.getPrefix().getValue();
        ExprCommandArgs.commandArgs = command.getArguments().getArgs().toArray(new String[0]);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public MessageReceivedEvent getEvent() { return this.e; }
}