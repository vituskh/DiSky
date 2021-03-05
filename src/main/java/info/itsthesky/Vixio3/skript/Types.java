package info.itsthesky.Vixio3.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.Vixio3.Vixio3;
import info.itsthesky.Vixio3.managers.BotManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

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
						return "JDA: " + o;
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
						if (!BotManager.getBots().containsKey(s)) {
							Vixio3.getInstance().getLogger().warning("You're trying to parse a bot via its name, however '"+s+"' doesn't exist or is not loaded!");
							return null;
						}
						return BotManager.getBot(s);
					}
				})
		);
		Classes.registerClass(new ClassInfo<>(TextChannel.class, "textchannel")
				.user("textchannel")
				.name("Text Channel")
				.description("Represent a Discord text channel (where file and message can be sent)")
				.since("1.0")
				.parser(new Parser<TextChannel>() {

					@Override
					public String toString(TextChannel o, int flags) {
						return "JDA: " + o;
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
					public TextChannel parse(final String s, final ParseContext context) {
						AtomicReference<JDA> bot = new AtomicReference<>();
						BotManager.getBots().forEach((name, key) -> bot.set(key));
						return bot.get().getTextChannelById(s);
					}
				})
		);
	}
}