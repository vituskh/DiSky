package info.itsthesky.DiSky.managers;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import info.itsthesky.DiSky.DiSky;
import net.dv8tion.jda.api.entities.Webhook;

import java.util.HashMap;
import java.util.logging.Logger;

public class WebhookManager {

    private static final Logger logger = DiSky.getInstance().getLogger();
    private static final HashMap<String, WebhookClient> clients = new HashMap<>();

    public static HashMap<String, WebhookClient> getClients() { return clients; }

    public static void addClient(final String name, final Webhook webhook) {
        addClient(name, webhook.getUrl());
    }

    public static void addClient(final String name, final String url) {
        if (clients.containsKey(name)) {
            logger.warning("The webhook client named '"+name+"' is already exist! Choose another name.");
            return;
        }

        WebhookClientBuilder builder = new WebhookClientBuilder(url);
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName(name);
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();

        clients.put(name, client);
        logger.info("The webhook client named '"+name+"' seems to be loaded correctly!");
    }

    public static void close(final String name) {
        if (!clients.containsKey(name)) {
            logger.warning("The webhook client named '"+name+"' is not loaded!");
            return;
        }
        clients.get(name).close();
        logger.warning("The webhook client '"+name+"' has been closed!");
        clients.remove(name);
    }

    public static void clearClients() {
        clients.forEach((name, client) -> {
            client.close();
            logger.warning("The webhook client '"+name+"' has been closed!");
        });
        clients.clear();
    }

    public static WebhookClient getClient(final String name) {
        if (!clients.containsKey(name)) {
            logger.warning("The webhook client '"+name+"' is not loaded, but you're trying to use it into an effect / expression!");
            return null;
        }
        return clients.get(name);
    }

}
