package info.itsthesky.DiSky.skript.commands;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.config.Config;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Kleenean;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Name("Discord Command")
@Description("Beta custom DiSky's command system. Took from Vixio and edited a lot to work with DiSky")
@Since("1.5.2")
public class CommandRegistry extends SelfRegisteringSkriptEvent {

    static {
        Skript.registerEvent("Discord Command", CommandRegistry.class, CommandEvent.class, "discord command <([^\\s]+)( .+)?$>");

        EventValues.registerEventValue(CommandEvent.class, CommandObject.class, new Getter<CommandObject, CommandEvent>() {
                    @Override
                    public CommandObject get(CommandEvent event) {
                        return event.getCommand();
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, User.class, new Getter<User, CommandEvent>() {
                    @Override
                    public User get(CommandEvent event) {
                        return event.getUser();
                    }
                }
                , 0);


        EventValues.registerEventValue(CommandEvent.class, Member.class, new Getter<Member, CommandEvent>() {
                    @Override
                    public Member get(CommandEvent event) {
                        return event.getMember();
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, GuildChannel.class, new Getter<GuildChannel, CommandEvent>() {
                    @Override
                    public GuildChannel get(CommandEvent event) {
                        return event.getChannel();
                    }
                }
                , 0);


        EventValues.registerEventValue(CommandEvent.class, MessageChannel.class, new Getter<MessageChannel, CommandEvent>() {
                    @Override
                    public MessageChannel get(CommandEvent event) {
                        return event.getMessageChannel();
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, Message.class, new Getter<Message, CommandEvent>() {
                    @Override
                    public Message get(CommandEvent event) {
                        return event.getMessage();
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, Channel.class, new Getter<Channel, CommandEvent>() {
                    @Override
                    public Channel get(CommandEvent event) {
                        return new Channel(event.getMessageChannel());
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, Guild.class, new Getter<Guild, CommandEvent>() {
                    @Override
                    public Guild get(CommandEvent event) {
                        return event.getGuild();
                    }
                }
                , 0);

        EventValues.registerEventValue(CommandEvent.class, JDA.class, new Getter<JDA, CommandEvent>() {
            @Override
            public JDA get(CommandEvent event) {
                return event.getBot();
            }
        }, 0);
    }

    private String arguments;
    private String command;

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        command = parser.regexes.get(0).group(1);
        arguments = parser.regexes.get(0).group(2);
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();

        String originalName = ScriptLoader.getCurrentEventName();
        Class<? extends Event>[] originalEvents = ScriptLoader.getCurrentEvents();
        Kleenean originalDelay = ScriptLoader.hasDelayBefore;
        ScriptLoader.setCurrentEvent("discord command", CommandEvent.class);

        CommandObject cmd = CommandFactory.getInstance().add(sectionNode);
        command = cmd == null ? command : cmd.getName();

        ScriptLoader.setCurrentEvent(originalName, originalEvents);
        ScriptLoader.hasDelayBefore = originalDelay;
        nukeSectionNode(sectionNode);

        return cmd != null;
    }

    @Override
    public void afterParse(Config config) {
    }

    @Override
    public void register(Trigger t) {
    }

    @Override
    public void unregister(Trigger t) {
        CommandFactory.getInstance().remove(command);
    }

    @Override
    public void unregisterAll() {
        CommandFactory.getInstance().commandMap.clear();
    }


    @Override
    public String toString(final Event e, final boolean debug) {
        return "discord command " + command + (arguments == null ? "" : arguments);
    }

    public void nukeSectionNode(SectionNode sectionNode) {
        List<Node> nodes = new ArrayList<>();
        for (Iterator<Node> iterator = sectionNode.iterator(); iterator.hasNext(); ) {
            nodes.add(iterator.next());
        }
        for (Node n : nodes) {
            sectionNode.remove(n);
        }
    }

}