package info.itsthesky.DiSky.tools.object.command;

import info.itsthesky.DiSky.tools.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DiscordCommand {

    private final Command command;
    private final Arguments arguments;
    private final Prefix prefix;

    public DiscordCommand(String message) {
        /* Space split */
        String[] values = message.split(" ");

        /* Set values */
        this.prefix = new Prefix(values[0].split("")[0]);
        this.command = new Command(Utils.replaceFirst(values[0], getPrefix().getValue(), ""));
        List<String> tempArgs = new ArrayList<>();
        int i = 0;
        for (String v : values) {
            if (i == 0) {
                i++;
                continue;
            }
            tempArgs.add(values[i]);
            i++;
        }
        this.arguments = new Arguments(tempArgs);
    }

    public Command getCommand() {
        return command;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public Prefix getPrefix() {
        return prefix;
    }
}
