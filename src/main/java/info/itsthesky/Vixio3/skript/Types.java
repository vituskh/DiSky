package info.itsthesky.Vixio3.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.Vixio3.Vixio3;
import info.itsthesky.Vixio3.managers.BotManager;
import info.itsthesky.Vixio3.tools.object.command.Arguments;
import info.itsthesky.Vixio3.tools.object.command.Command;
import info.itsthesky.Vixio3.tools.object.command.Prefix;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class Types {
	static  {
		Classes.registerClass(new ClassInfo<>(JDA.class, "bot")
				.user("bot?")
				.name("Discord Bot")
				.description("Represent a loaded Discord Bot")
				.since("1.0")
				.parser(new Parser<JDA>() {

					@Override
					public String toString(JDA o, int flags) {
						return o.getToken();
					}

					@Override
					public String toVariableNameString(JDA o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public JDA parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(TextChannel.class, "textchannel")
				.user("textchannel?")
				.name("Text Channel")
				.description("Represent a Discord text channel (where file and message can be sent)")
				.since("1.0")
				.parser(new Parser<TextChannel>() {

					@Override
					public String toString(TextChannel o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(TextChannel o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public TextChannel parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(User.class, "user")
				.user("user?")
				.name("Discord User")
				.description("Represent a discord user, which is not into a guild.")
				.since("1.0")
				.parser(new Parser<User>() {

					@Override
					public String toString(User o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(User o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public User parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Message.class, "message")
				.user("message?")
				.name("Discord Message")
				.description("Represent a discord message, with ID, author, reactions, etc...")
				.since("1.0")
				.parser(new Parser<Message>() {

					@Override
					public String toString(Message o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(Message o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Message parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Guild.class, "guild")
				.user("guild?")
				.name("Discord Guild (Server)")
				.description("Represent a discord guild")
				.since("1.0")
				.parser(new Parser<Guild>() {

					@Override
					public String toString(Guild o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(Guild o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Guild parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Command.class, "command")
				.user("command?")
				.name("Event Command")
				.description(new String[] {
						"Represent a core command in a command event.",
						"For example, in the command:",
						".say Hello :D",
						"The 'say' represent the core command."
				})
				.since("1.0")
				.parser(new Parser<Command>() {

					@Override
					public String toString(Command o, int flags) {
						return o.getValue();
					}

					@Override
					public String toVariableNameString(Command o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Command parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Prefix.class, "prefix")
				.user("prefix")
				.name("Event Prefix")
				.description(new String[] {
						"Represent the prefix in a command event.",
						"For example, in the command:",
						".say Hello :D",
						"The '.' represent the prefix."
				})
				.since("1.0")
				.parser(new Parser<Prefix>() {

					@Override
					public String toString(Prefix o, int flags) {
						return o.getValue();
					}

					@Override
					public String toVariableNameString(Prefix o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Prefix parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Arguments.class, "argument")
				.user("argument")
				.name("Event Arguments")
				.description(new String[] {
						"Represent all arguments in a command event.",
						"For example, in the command:",
						".say Hello :D",
						"The 'Hello' and the ':D' represent arguments."
				})
				.since("1.0")
				.parser(new Parser<Arguments>() {

					@Override
					public String toString(Arguments o, int flags) {
						return o.getArgs().toString();
					}

					@Override
					public String toVariableNameString(Arguments o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Arguments parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
	}
}