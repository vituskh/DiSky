package info.itsthesky.DiSky.skript.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEvent extends Event implements Cancellable {

    public static CommandEvent lastEvent;

    private final CommandObject command;
    private final Guild guild;
    private final Message message;
    private final User user;
    private final Member member;
    private final MessageChannel messagechannel;
    private String prefix;
    private final String usedAlias;
    private boolean cancelled;
    private final GuildChannel channel;
    private final JDA bot;
    private String arguments;

	public CommandEvent(String prefix, String usedAlias, CommandObject command, String arguments, Guild guild,
                        MessageChannel messagechannel, GuildChannel channel, Message message, User user,
                        Member member, JDA bot) {
		this.arguments = arguments == null ? "" : arguments;
        this.command = command;
        this.guild = guild;
        this.user = user;
        this.usedAlias = usedAlias;
        this.message = message;
        this.member = member;
        this.channel = channel;
        this.messagechannel = messagechannel;
        this.prefix = prefix;
        this.bot = bot;
        lastEvent = this;
    }

    public CommandEvent(CommandEvent original) {
        this(original.getPrefix(),
                original.getUsedAlias(),
                original.getCommand(),
				original.getArguments(),
                original.getGuild(),
                original.getMessageChannel(),
                original.getChannel(),
                original.getMessage(),
                original.getUser(),
                original.getMember(),
                original.getBot());
    }

    public CommandObject getCommand() {
        return command;
    }

    public Guild getGuild() {
        return guild;
    }

    public Message getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }

    public User getUser() {
        return user;
    }

    public GuildChannel getChannel() {
        return channel;
    }

    public MessageChannel getMessageChannel() {
        return messagechannel;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUsedAlias() {
        return usedAlias;
    }

    public JDA getBot() {
        return bot;
    }

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments == null ? "" : arguments;
	}

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private static final HandlerList HANDLERS = new HandlerList();
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
