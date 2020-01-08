package de.johannesrauch.hexxagon.network.client;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * This class extends a websocket client.
 */
public class Client extends WebSocketClient {

    private final Logger logger;

    private final MessageReceiver messageReceiver;
    private final StateContext context;

    /**
     * This is the standard constructor. With the given URI, it calls the websocket client constructor.
     * It also sets the message receiver.
     *
     * @param serverUri       the uri to connect to
     * @param messageReceiver the message receiver
     */
    public Client(URI serverUri, MessageReceiver messageReceiver, StateContext context) {
        super(serverUri);
        logger = LoggerFactory.getLogger(Client.class);
        this.messageReceiver = messageReceiver;
        this.context = context;
    }

    /**
     * This method gets called when the connection gets opened.
     *
     * @param handshake the handshake data
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        logger.info("Client connected to: " + getConnection().getRemoteSocketAddress().toString());
    }

    /**
     * This method gets called when a message is received.
     *
     * @param message the received string message
     */
    @Override
    public void onMessage(String message) {
        messageReceiver.handleMessage(message);
    }

    /**
     * This method gets called when the connection gets closed.
     *
     * @param code   the close code
     * @param reason the close reason
     * @param remote the remote
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Client disconnected from: " + this.getConnection().getRemoteSocketAddress().toString());
        context.reactToReceivedServerDisconnect();
    }

    /**
     * This method gets called when a error occurs.
     *
     * @param ex the exception
     */
    @Override
    public void onError(Exception ex) {
        if (ex instanceof NullPointerException)
            return; // websocket implementation throws weird null-pointer exception: ignore this
        logger.error("Client error: " + ex.getMessage());
        context.reactToReceivedConnectionError();
    }
}
