package info.itsthesky.DiSky.managers;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

public class WebhookManager {

    public WebhookManager() {

        WebhookClientBuilder builder = new WebhookClientBuilder(""); // or id, token
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Hello");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();

        // Change appearance of webhook message
        WebhookMessageBuilder builder1 = new WebhookMessageBuilder();
        builder1.setUsername("Minn"); // use this username
        builder1.setAvatarUrl("avatarUrl"); // use this avatar
        client.send(builder1.build());

    }

}
