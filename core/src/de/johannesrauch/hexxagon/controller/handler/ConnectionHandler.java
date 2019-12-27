package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.network.client.MessageReceiver;
import de.johannesrauch.hexxagon.network.client.SimpleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConnectionHandler {

    private final Hexxagon parent;

    private final Logger logger;

    private MessageReceiver messageReceiver;
    private MessageEmitter messageEmitter;
    private SimpleClient client;

    private UUID userId;

    public ConnectionHandler(Hexxagon parent) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(ConnectionHandler.class);
        messageReceiver = new MessageReceiver(parent);
        messageEmitter = new MessageEmitter(this);
        reset();
    }

    public void connect(String hostName, String port) {
        boolean successful = false;

        SimpleClient client = null;
        try {
            client = new SimpleClient(new URI("ws://" + hostName + ":" + port), messageReceiver);
            successful = true;
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }

        if (successful) {
            successful = false;
            try {
                successful = client.connectBlocking(5, TimeUnit.SECONDS);
            } catch (InterruptedException ignore) { }
        }

        if (successful) this.client = client;
        else parent.getContext().reactToReceivedConnectionError();
    }

    public void disconnect() {
        if (client != null) client.close();
        parent.getLobbyHandler().reset();
        parent.getGameHandler().reset();
        reset();
    }

    public SimpleClient getClient() {
        return client;
    }

    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private void reset() {
        client = null;
        userId = null;
    }
}
