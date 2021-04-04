package info.itsthesky.DiSky.managers;

import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.managers.cache.EditedMessages;
import info.itsthesky.DiSky.managers.cache.Messages;
import info.itsthesky.DiSky.skript.commands.CommandListener;
import info.itsthesky.DiSky.skript.events.bukkit.JDAListener;
import info.itsthesky.DiSky.skript.events.skript.EventBotJoin;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class BotManager {

    private static final Logger logger = DiSky.getInstance().getLogger();
    private static final HashMap<String, JDA> bots = new HashMap<>();

    public static HashMap<String, JDA> getBots() { return bots; }

    /**
     * Register a new bot with specific name in the bots list.
     * Load the JDA instance linked to it via the token.
     * @param name The bot name (= id)
     * @param token The token to connect to
     */
    public static void addBot(final String name, final String token) {
        if (bots.containsKey(name)) {
            logger.warning("The bot named '"+name+"' is already loaded on the server! Shutdown it or change the name.");
            return;
        }

        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(new JDAListener())
                    .addEventListeners(new Messages())
                    .addEventListeners(new EditedMessages())
                    .addEventListeners(new Utils())
                    .addEventListeners(new CommandListener())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                    .enableCache(CacheFlag.ACTIVITY)
                    .setChunkingFilter(ChunkingFilter.NONE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
            logger.severe("Can't load the bot named '"+name+"', see error above for more information.");
            return;
        }

        bots.put(name, jda);
        logger.info("The bot named '"+name+"' seems to be loaded correctly!");
    }

    /**
     * Shutdown all JDA instance of all loaded bots, and clear the bots list.
     */
    public static void clearBots() {
        bots.forEach((name, jda) -> {
            jda.shutdown();
            logger.warning("The bot '"+name+"' has been disconnected!");
        });
        bots.clear();
    }

    /**
     * Get the first bot loaded, to use it in non-bot syntaxes.
     * @return The instance of the first bot, else null
     */
    public static JDA getFirstBot() {
        AtomicReference<JDA> r = new AtomicReference<>();
        bots.forEach((name, jda) -> r.set(jda));
        return r.get();
    }

    /**
     * Get the first name of the first bot loaded.
     * @return The instance of the first bot, else null
     */
    public static String getFirstBotName() {
        AtomicReference<String> r = new AtomicReference<>();
        bots.forEach((name, jda) -> r.set(name));
        return r.get();
    }

    /**
     * If into the bots list, shutdown the JDA instance and remove the bot name from the bots list.
     * @param name The name (= id) of the bot
     */
    public static void removeAndShutdown(final String name) {
        if (!bots.containsKey(name)) {
            logger.warning("The bot named '"+name+"' is not loaded / already shutdown!");
            return;
        }

        final JDA jda = bots.get(name);
        jda.shutdown();
        logger.warning("The bot '"+name+"' has been disconnected!");
        bots.remove(name);
    }

    /**
     * Get a bot name via its JDA.
     * Mainly used in discord command factory
     * @param target The bot's JDA instance
     * @return The name of bot via the JDA
     */
    public static String getNameByJDA(final JDA target) {
        AtomicReference<String> f = new AtomicReference<>();
        bots.forEach((name, jda) -> f.set(name));
        return f.get();
    }

    /**
     * Get, if loaded, the JDA instance of a bot from its name
     * @param name The name (= id) of the wanted bot
     * @return The JDA instance of the specific bot
     */
    public static JDA getBot(final String name) {
        if (!bots.containsKey(name)) {
            logger.warning("The bot named '"+name+"' is not loaded, but you're trying to use it into an effect / expression!");
            return null;
        }
        return bots.get(name);
    }
}
