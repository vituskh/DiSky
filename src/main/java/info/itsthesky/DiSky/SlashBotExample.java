package info.itsthesky.DiSky;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.commands.CommandHook;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction.OptionData;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

import static net.dv8tion.jda.api.entities.Command.OptionType.*;

public class SlashBotExample extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException
    {
        JDA jda = JDABuilder.createLight("ODIxNDYxMjI3Mzg4Nzk3MDU4.YFEDYg.XUgFNCqZoHyGaLt3hEsfXXhoKws", EnumSet.noneOf(GatewayIntent.class)) // slash commands don't need any intents
                .addEventListeners(new SlashBotExample())
                .build();

        // These commands take up to an hour to be activated after creation/update/delete
        CommandUpdateAction commands = jda.updateCommands();

        // Moderation commands with required options
        commands.addCommands(
                new CommandData("ban", "Ban a user from this server. Requires permission to ban users.")
                        .addOption(new OptionData(USER, "user", "The user to ban") // USER type allows to include members of the server or other users by id
                                .setRequired(true)) // This command requires a parameter
                        .addOption(new OptionData(INTEGER, "delDays", "Delete messages from the past days.")) // This is optional
        );

        commands.addCommands(
                new CommandData("test", "It's a simple test which will mention a channel via it's ID")
                        .addOption(new OptionData(STRING, "id", "The channel ID to mention")
                                .setRequired(true))
        );

        // Simple reply commands
        commands.addCommands(
                new CommandData("say", "Makes the bot say what you tell it to")
                        .addOption(new OptionData(STRING, "content", "What the bot should say")
                                .setRequired(true))
        );

        // Commands without any inputs
        commands.addCommands(
                new CommandData("leave", "Make the bot leave the server")
        );

        // Send the new set of commands to discord, this will override any existing global commands with the new set provided here
        commands.queue();
    }


    @Override
    public void onSlashCommand(SlashCommandEvent event)
    {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {
            case "ban":
                Member member = event.getOption("user").getAsMember(); // the "user" option is required so it doesn't need a null-check here
                User user = event.getOption("user").getAsUser();
                ban(event, user, member);
                break;
            case "say":
                say(event, event.getOption("content").getAsString()); // content is required so no null-check here
                break;
            case "leave":
                leave(event);
                break;
            case "test":
                say(event, event.getOption("id").getAsString());
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    public void ban(SlashCommandEvent event, User user, Member member)
    {
        event.acknowledge(true).queue(); // Let the user know we received the command before doing anything else
        CommandHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("You do not have the required permissions to ban users from this server.").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("I don't have the required permissions to ban users from this server.").queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member))
        {
            hook.sendMessage("This user is too powerful for me to ban.").queue();
            return;
        }

        int delDays = 0;
        SlashCommandEvent.OptionData option = event.getOption("delDays");
        if (option != null) // null = not provided
            delDays = (int) Math.max(0, Math.min(7, option.getAsLong()));
        // Ban the user and send a success response
        event.getGuild().ban(user, delDays)
                .flatMap(v -> hook.sendMessage("Banned user " + user.getAsTag()))
                .queue();
    }

    public void say(SlashCommandEvent event, String content)
    {
        event.reply(content).queue(); // This requires no permissions!
    }

    public void leave(SlashCommandEvent event)
    {
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS))
            event.reply("You do not have permissions to kick me.").setEphemeral(true).queue();
        else
            event.reply("Leaving the server... :wave:") // Yep we received it
                    .flatMap(v -> event.getGuild().leave()) // Leave server after acknowledging the command
                    .queue();
    }
}