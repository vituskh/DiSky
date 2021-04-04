package info.itsthesky.DiSky;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import info.itsthesky.DiSky.managers.BotManager;
import info.itsthesky.DiSky.managers.WebhookManager;
import info.itsthesky.DiSky.managers.music.AudioUtils;
import info.itsthesky.DiSky.tools.Metrics;
import info.itsthesky.DiSky.tools.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class DiSky extends JavaPlugin {

    /* Initialisation */
    private static DiSky instance;
    private Logger logger;
    private static PluginManager pluginManager;

    @Override
    public void onEnable() {

        /* Variable loading */
        instance = this;
        logger = getLogger();
        pluginManager = getServer().getPluginManager();

        /* Skript loading */
        getServer().getConsoleSender().sendMessage(Utils.colored("&bDiSky &9is loading ..."));
        if ((pluginManager.getPlugin("Skript") != null) && Skript.isAcceptRegistrations()) {
            getServer().getConsoleSender().sendMessage(Utils.colored("&aSkript found! &6Starting registration ..."));
            SkriptAddon addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("info.itsthesky.DiSky.skript");
            } catch (IOException e) {
                Skript.error("Wait, this is anormal. Please report this error on GitHub.");
                e.printStackTrace();
            }
        } else {
            Skript.error("Skript isn't installed or doesn't accept registrations.");
            pluginManager.disablePlugin(this);
        }
        Utils.saveResourceAs("config.yml");

        /* Metrics */
        int pluginId = 10911;
        Metrics metrics = new Metrics(this, pluginId);

        /* Audio system */
        AudioUtils.initializeAudio();

        getServer().getConsoleSender().sendMessage(Utils.colored("&bDiSky &9seems to be loaded correctly!"));
        getServer().getConsoleSender().sendMessage(Utils.colored("&9Join our &bDiscord &9for new update and support:"));
        getServer().getConsoleSender().sendMessage(Utils.colored("&bhttps://discord.gg/whWuXwaVwM"));
    }

    @Override
    public void onDisable() {
        /* Clients Shutdown */
        BotManager.clearBots();
        WebhookManager.clearClients();
    }

    public static DiSky getInstance() { return instance; }
    public static PluginManager getPluginManager() { return pluginManager; }

}
