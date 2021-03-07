package info.itsthesky.DiSky;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import info.itsthesky.DiSky.managers.BotManager;
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
        if ((pluginManager.getPlugin("Skript") != null) && Skript.isAcceptRegistrations()) {
            SkriptAddon addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("info.itsthesky.Vixio3.skript");
            } catch (IOException e) {
                Skript.error("Wait, this is anormal. Please report this error on GitHub.");
                e.printStackTrace();
            }
        } else {
            Skript.error("Skript isn't installed or doesn't accept registrations.");
            pluginManager.disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {

        /* Bot Shutdown */
        BotManager.clearBots();

    }

    public static DiSky getInstance() { return instance; }
    public static PluginManager getPluginManager() { return pluginManager; }

}
