package info.itsthesky.DiSky.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.DiSky.tools.object.command.Arguments;
import info.itsthesky.DiSky.tools.object.command.Command;
import info.itsthesky.DiSky.tools.object.command.Prefix;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

public class Types {
	static  {
		Classes.registerClass(new ClassInfo<>(JDA.class, "bot")
				.user("bots?")
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
				.user("textchannels?")
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
				.user("users?")
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
		Classes.registerClass(new ClassInfo<>(Role.class, "role")
				.user("roles?")
				.name("Discord Role")
				.description("Represent a discord role, within a guild.")
				.since("1.0")
				.parser(new Parser<Role>() {

					@Override
					public String toString(Role o, int flags) {
						return o.getName();
					}

					@Override
					public String toVariableNameString(Role o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Role parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Member.class, "member")
				.user("members?")
				.name("Discord Member")
				.description("Represent a discord user which is in any guild.")
				.since("1.0")
				.parser(new Parser<Member>() {

					@Override
					public String toString(Member o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(Member o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Member parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Message.class, "message")
				.user("messages?")
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
		Classes.registerClass(new ClassInfo<>(Channel.class, "channel")
				.user("channels?")
				.name("Discord Channel")
				.description("Represent a discord channel in a guild. Can be both vocal or text channel." +
						"\n Mainly made for better event value using lol")
				.since("1.1")
				.parser(new Parser<Channel>() {

					@Override
					public String toString(Channel o, int flags) {
						return o.getChannel().getId();
					}

					@Override
					public String toVariableNameString(Channel o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Channel parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(info.itsthesky.DiSky.tools.object.messages.Message.class, "staticmessage")
				.user("staticmessages?")
				.name("Static Discord Message")
				.description("Represent a static discord message, which mean a message / embed which is not sent yet.")
				.since("1.0")
				.parser(new Parser<info.itsthesky.DiSky.tools.object.messages.Message>() {

					@Override
					public String toString(info.itsthesky.DiSky.tools.object.messages.Message o, int flags) {
						return o.toString();
					}

					@Override
					public String toVariableNameString(info.itsthesky.DiSky.tools.object.messages.Message o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public info.itsthesky.DiSky.tools.object.messages.Message parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Guild.class, "guild")
				.user("guilds?")
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
				.user("commands?")
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
		Classes.registerClass(new ClassInfo<>(EmbedBuilder.class, "embed")
				.user("embed")
				.name("Embed")
				.description(new String[] {
						"Represent a discord embed, with title, description, fields, etc..."
				})
				.since("1.0")
				.parser(new Parser<EmbedBuilder>() {

					@Override
					public String toString(EmbedBuilder o, int flags) {
						return o.getDescriptionBuilder().toString();
					}

					@Override
					public String toVariableNameString(EmbedBuilder o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public EmbedBuilder parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
	}
}