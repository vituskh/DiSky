package info.itsthesky.DiSky.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.DiSky.tools.object.CategoryBuilder;
import info.itsthesky.DiSky.tools.object.RoleBuilder;
import info.itsthesky.DiSky.tools.object.TextChannelBuilder;
import info.itsthesky.DiSky.tools.object.command.Arguments;
import info.itsthesky.DiSky.tools.object.command.Command;
import info.itsthesky.DiSky.tools.object.command.Prefix;
import info.itsthesky.DiSky.tools.object.messages.Channel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Locale;

public class Types {
	static  {
		Classes.registerClass(new ClassInfo<>(Category.class, "category")
				.user("categor(y|ies)")
				.name("Category")
				.description("Represent a category in a guild, which is already created.")
				.since("1.4.1")
				.parser(new Parser<Category>() {
					@Override
					public Category parse(String input, ParseContext context) {
						return null;
					}

					@Override
					public String toString(Category c, int flags) {
						return c.getName();
					}

					@Override
					public String toVariableNameString(Category cat) {
						return cat.getName();
					}

					@Override
					public String getVariableNamePattern() {
						return "[a-z ]+";
					}
				}));
		Classes.registerClass(new ClassInfo<>(CategoryBuilder.class, "categorybuilder")
				.user("categorybuilders?")
				.name("Category Builder")
				.description("Represent a category builder, which is not created yet in a guild.")
				.since("1.4.1")
				.parser(new Parser<CategoryBuilder>() {
					@Override
					public CategoryBuilder parse(String input, ParseContext context) {
						return null;
					}

					@Override
					public String toString(CategoryBuilder c, int flags) {
						return c.getName();
					}

					@Override
					public String toVariableNameString(CategoryBuilder cat) {
						return cat.getName();
					}

					@Override
					public String getVariableNamePattern() {
						return "[a-z ]+";
					}
				}));
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
		Classes.registerClass(new ClassInfo<>(TextChannelBuilder.class, "textchannelbuilder")
				.user("textchannelbuilders?")
				.name("TextChannel Builder")
				.description("Represent a textchannel builder")
				.since("1.4")
				.parser(new Parser<TextChannelBuilder>() {

					@Override
					public String toString(TextChannelBuilder o, int flags) {
						return o.getName();
					}

					@Override
					public String toVariableNameString(TextChannelBuilder o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public TextChannelBuilder parse(final String s, final ParseContext context) {
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
		Classes.registerClass(new ClassInfo<>(MessageReaction.ReactionEmote.class, "emote")
				.user("emotes?")
				.name("Discord Emote")
				.description("Represent a discord emote in a guild, with id, url, unicode, etc...")
				.since("1.3")
				.parser(new Parser<MessageReaction.ReactionEmote>() {

					@Override
					public String toString(MessageReaction.ReactionEmote o, int flags) {
						if (o.isEmote()){
							return o.getEmote().getAsMention();
						} else {
							return o.getAsReactionCode();
						}
					}

					@Override
					public String toVariableNameString(MessageReaction.ReactionEmote o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public MessageReaction.ReactionEmote parse(final String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Webhook.class, "webhookbuilder")
				.user("webhookbuilders?")
				.name("Discord Webhook Builder")
				.description("Represent a webhook which is not actually made in the guild.")
				.since("1.2")
				.parser(new Parser<Webhook>() {

					@Override
					public String toString(Webhook o, int flags) {
						return o.getId();
					}

					@Override
					public String toVariableNameString(Webhook o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public Webhook parse(final String s, final ParseContext context) {
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
		Classes.registerClass(new ClassInfo<>(MessageBuilder.class, "messagebuilder")
				.user("messagebuilders?")
				.name("Message Builder")
				.description(new String[] {
						"Represent a discord message builder, which you can append embed and string."
				})
				.since("1.4")
				.parser(new Parser<MessageBuilder>() {

					@Override
					public String toString(MessageBuilder o, int flags) {
						return o.toString();
					}

					@Override
					public String toVariableNameString(MessageBuilder o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public MessageBuilder parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(RoleBuilder.class, "rolebuilder")
				.user("rolebuilders?")
				.name("Role Builder")
				.description(new String[] {
						"Represent a discord role builder, which is not created yet in a guild."
				})
				.since("1.4")
				.parser(new Parser<RoleBuilder>() {

					@Override
					public String toString(RoleBuilder o, int flags) {
						return o.getName();
					}

					@Override
					public String toVariableNameString(RoleBuilder o) {
						return "";
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public RoleBuilder parse(String s, final ParseContext context) {
						return null;
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(Permission.class, "permission")
				.user("permissions?")
				.name("Discord Permission")
				.description("Permission used for a role, channel, member, etc...")
				.usage("create instant invite, kick members, ban members, administrator, manage channel, manage server, message add reaction, view audit logs, view channel, message read, message write, message tts, message manage, message embed links, message attach files, message history, message mention everyone, message ext emoji, voice connect, voice speak, voice mute others, voice deaf others, voice move others, voice use vad, nickname change, nickname manage, manage roles, manage permissions, manage webhooks, manage emotes, unknown")
				.since("1.4")
				.parser(new Parser<Permission>() {
					@Override
					public Permission parse(String input, ParseContext context) {
						for (Permission perm : Permission.values()) {
							if (perm.getName().equalsIgnoreCase(input.replaceAll("_", " ").toLowerCase())) return perm;
						}
						return null;
					}

					@Override
					public String toString(Permission c, int flags) {
						return c.getName();
					}

					@Override
					public String toVariableNameString(Permission perm) {
						return "" + perm.getName().toLowerCase(Locale.ENGLISH).replace('_', ' ');
					}

					@Override
					public String getVariableNamePattern() {
						return "[a-z ]+";
					}
				}));
	}
}