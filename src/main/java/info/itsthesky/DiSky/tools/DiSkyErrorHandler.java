package info.itsthesky.DiSky.tools;

import info.itsthesky.DiSky.DiSky;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.bukkit.event.Event;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class DiSkyErrorHandler {

    private final static Logger logger = DiSky.getInstance().getLogger();

    public static void executeHandleCode(Event e,Consumer<Event> consumer) {
        new Thread(() -> {
            try {
                consumer.accept(e);
            } catch (InsufficientPermissionException ex) {
                logger.warning("DiSky tried to do an action on Discord, but doesn't have the " + ex.getPermission().getName() + " permission!");
            } catch (NullPointerException ex) {
                logger.warning("DiSky tried to do an action on Discord, but got a NullPointerException! Check if all your value are set!");
            }
        }).start();
    }

}
