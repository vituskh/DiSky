package info.itsthesky.DiSky.skript.sections;

import info.itsthesky.DiSky.tools.Utils;
import info.itsthesky.DiSky.tools.object.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
            if (waiter.getVerify().test(event)) waiter.getConsumer().accept(event);
        });
    }

    /**
     * This object store the event itself, the predicate and the runnable to execute.
     */
    public static class ReactWaitingEvent {

        private Consumer<GuildMessageReactionAddEvent> consumer;
        private Predicate<GuildMessageReactionAddEvent> verify;

        public ReactWaitingEvent(Predicate<GuildMessageReactionAddEvent> verify, Consumer<GuildMessageReactionAddEvent> consumer) {
            this.consumer = consumer;
            this.verify = verify;
        }


        public Consumer<GuildMessageReactionAddEvent> getConsumer() {
            return consumer;
        }
        public void setConsumer(Consumer<GuildMessageReactionAddEvent> consumer) {
            this.consumer = consumer;
        }
        public Predicate<GuildMessageReactionAddEvent> getVerify() {
            return verify;
        }
    }

}
