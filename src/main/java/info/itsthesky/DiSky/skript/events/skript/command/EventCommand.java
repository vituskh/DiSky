package info.itsthesky.DiSky.skript.events.skript.command;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import info.itsthesky.DiSky.tools.object.command.Arguments;
import info.itsthesky.DiSky.tools.object.command.Command;
import info.itsthesky.DiSky.tools.object.command.DiscordCommand;
import info.itsthesky.DiSky.tools.object.command.Prefix;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Command")
@Description("Fired when a command is executed by any user. Have a prefix, a core command and some args")
@Examples({"on discord command:",
        "\tevent-prefix is \".\"",
        "\tevent-command is \"say\"",
        "\tset {_args::*} to event-arguments",
        "\tif {_args::1} is set:",
        "\t\treply with {_args::1}",
        "\telse:",
        "\t\nreply with \":x: You have to specify a message!\""
})
@Since("1.0")
public class EventCommand extends Event {

    static {
        // [seen by [bot] [(named|with name)]%string%]
        Skript.registerEvent("Member Leave", SimpleEvent.class, EventCommand.class, "[discord] command");

        EventValues.registerEventValue(EventCommand.class, TextChannel.class, new Getter<TextChannel, EventCommand>() {
            @Nullable
            @Override
            public TextChannel get(final @NotNull EventCommand event) {
                return event.getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Channel.class, new Getter<Channel, EventCommand>() {
            @Nullable
            @Override
            public Channel get(final @NotNull EventCommand event) {
                return event.getChannel();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Message.class, new Getter<Message, EventCommand>() {
            @Nullable
            @Override
            public Message get(final @NotNull EventCommand event) {
                return event.getMessage();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, User.class, new Getter<User, EventCommand>() {
            @Nullable
            @Override
            public User get(final @NotNull EventCommand event) {
                return event.getUser();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Guild.class, new Getter<Guild, EventCommand>() {
            @Nullable
            @Override
            public Guild get(final @NotNull EventCommand event) {
                return event.getGuild();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Command.class, new Getter<Command, EventCommand>() {
            @Nullable
            @Override
            public Command get(final @NotNull EventCommand event) {
                return event.getCommand();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Prefix.class, new Getter<Prefix, EventCommand>() {
            @Nullable
            @Override
            public Prefix get(final @NotNull EventCommand event) {
                return event.getPrefix();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Arguments.class, new Getter<Arguments, EventCommand>() {
            @Nullable
            @Override
            public Arguments get(final @NotNull EventCommand event) {
                return event.getArguments();
            }
        }, 0);

        EventValues.registerEventValue(EventCommand.class, Member.class, new Getter<Member, EventCommand>() {
            @Nullable
            @Override
            public Member get(final @NotNull EventCommand event) {
                return event.getMember();
            }
        }, 0);


    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final Channel channel;
    private final Guild guild;
    private final User user;
    private final Message message;
    private final Command command;
    private final Arguments arguments;
    private final Prefix prefix;
    private final Member member;

    public EventCommand(
            final Channel channel,
            final Member member,
            final Message message,
            final Guild guild,
            final DiscordCommand command
            ) {
        super(true);
        this.channel = channel;
        this.guild = guild;
        this.user = member.getUser();
        this.member = member;
        this.message = message;
        this.command = command.getCommand();
        this.prefix = command.getPrefix();
        this.arguments = command.getArguments();
        ExprCommandCore.commandCore = command.getCommand().getValue();
        ExprCommandPrefix.commandPrefix = command.getPrefix().getValue();
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Arguments getArguments() {
        return arguments;
    }
    public Prefix getPrefix() {
        return prefix;
    }
    public Channel getChannel() {
        return channel;
    }
    public TextChannel getTextChannel() {
        return channel.getTextChannel();
    }
    public Message getMessage() {
        return message;
    }
    public Command getCommand() {
        return command;
    }
    public Guild getGuild() {
        return guild;
    }
    public User getUser() {
        return user;
    }
    public Member getMember() {
        return member;
    }
}