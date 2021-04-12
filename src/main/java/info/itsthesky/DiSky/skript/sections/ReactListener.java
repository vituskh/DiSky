package info.itsthesky.DiSky.skript.sections;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Listener for react section.
 * @author ItstheSky (12/04/2021)
 */
public class ReactListener extends ListenerAdapter {

    public static List<ReactWaitingEvent> events = new ArrayList<>();

    /**
     * The main event listener method.
     * Check every waiting event stored in the list, verify their predicate and execute their runnable.
     * Mainly used for SectionReact, but could be transformed for other section.
     */
    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        events.forEach((waiter) -> {
            if (waiter.getVerify().test(event)) waiter.getRunnable().run();
        });
    }

    /**
     * This object store the event itself, the predicate and the runnable to execute.
     */
    public static class ReactWaitingEvent {

        private final Predicate<GuildMessageReactionAddEvent> verify;
        private final Runnable runnable;

        public ReactWaitingEvent(Predicate<GuildMessageReactionAddEvent> verify, Runnable runnable) {
            this.runnable = runnable;
            this.verify = verify;
        }

        public Predicate<GuildMessageReactionAddEvent> getVerify() {
            return verify;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }

}
