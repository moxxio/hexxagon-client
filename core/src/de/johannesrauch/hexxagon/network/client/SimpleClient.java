package de.johannesrauch.hexxagon.network.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class SimpleClient extends WebSocketClient {

    private final Logger logger;

    private final MessageReceiver messageReceiver;

    public SimpleClient(URI serverUri, MessageReceiver messageReceiver) {
        super(serverUri);
        logger = LoggerFactory.getLogger(SimpleClient.class);
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        logger.info("Client connected to: " + getConnection().getRemoteSocketAddress().toString());
    }

    @Override
    public void onMessage(String message) {
        messageReceiver.handleMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Client disconnected from: " + this.getConnection().getRemoteSocketAddress().toString());
    }

    @Override
    public void onError(Exception ex) {
        logger.error("Client error: " + ex.getMessage());
    }
}
