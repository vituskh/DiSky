package info.itsthesky.DiSky.skript.commands;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Utils;
import info.itsthesky.DiSky.managers.BotManager;
import net.dv8tion.jda.api.entities.ChannelType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandObject {

    private String name;
    private List<String> aliases;
    private List<String> roles;
    private List<ChannelType> executableIn;
    private List<Expression<String>> prefixes;
    private String description;
    private String usage;
    private String pattern;
    private List<String> bots;

    private Trigger trigger;

    private List<Argument<?>> arguments;

    public CommandObject(File script, String name, String pattern, List<Argument<?>> arguments, List<Expression<String>> prefixes,
                         List<String> aliases, String description, String usage, List<String> roles,
                         List<ChannelType> executableIn, List<String> bots, List<TriggerItem> items) {
        this.name = name;
        if (aliases != null) {
            aliases.removeIf(alias -> alias.equalsIgnoreCase(name));
        }
        this.aliases = aliases;
        this.roles = roles;
        this.executableIn = executableIn;
        this.description = Utils.replaceEnglishChatStyles(description);
        this.usage = Utils.replaceEnglishChatStyles(usage);
        this.pattern = pattern;
        this.prefixes = prefixes;
        this.bots = bots;
        this.arguments = arguments;

        trigger = new Trigger(script, "discord command " + name, new SimpleEvent(), items);

    }

    public boolean execute(CommandEvent event) {
        ParseLogHandler log = SkriptLogger.startParseLogHandler();

        try {

            boolean ok = CommandFactory.getInstance().parseArguments(event.getArguments(), this, event);
            if (!ok) {
                return false;
            }
            if (!this.getExecutableIn().contains(event.getMessageChannel().getType())) {
                return false;
            }
            if (this.getRoles() != null && event.getMember() != null) {
                if (event.getMember().getRoles().stream().noneMatch(r -> this.getRoles().contains(r.getName()))) {
                    return false;
                }
            }
            if (bots != null && !bots.contains(BotManager.getNameByJDA(event.getBot()))) {
                return false;
            }

            // again, bukkit apis are mostly sync so run it on the main thread
            info.itsthesky.DiSky.tools.Utils.sync(() -> trigger.execute(event));

        } finally {
            log.stop();
        }

        return true;
    }

    public List<Argument<?>> getArguments() {
        return arguments;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public List<Expression<String>> getPrefixes() {
        return prefixes;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getUsableAliases() {
        List<String> usableAliases = new ArrayList<>();
        usableAliases.add(getName());
        if (getAliases() != null) {
            usableAliases.addAll(getAliases());
        }
        return usableAliases;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public List<ChannelType> getExecutableIn() {
        return executableIn;
    }

    public List<String> getRoles() {
        return roles;
    }

    public File getScript() {
        return trigger.getScript();
    }

}