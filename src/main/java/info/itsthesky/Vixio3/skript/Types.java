package info.itsthesky.Vixio3.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import info.itsthesky.Vixio3.Vixio3;
import info.itsthesky.Vixio3.managers.BotManager;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.awt.image.BufferedImage;

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
							Vixio3.getInstance().getLogger().warning("You're trying to parse a bot wia its name, however '"+s+"' doesn't exist or is not loaded!");
							return null;
						}
						return BotManager.getBot(s);
					}
				})
		);
	}
}