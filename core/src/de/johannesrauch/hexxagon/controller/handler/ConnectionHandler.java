package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.network.client.MessageReceiver;
import de.johannesrauch.hexxagon.network.client.SimpleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * This class handles the connection to the server. It contains the message emitter and receiver.
 */
public class ConnectionHandler {

    private final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    private MessageReceiver messageReceiver;
    private MessageEmitter messageEmitter;
    private SimpleClient client;

    private UUID userId;

    private StateContext context;
    private LobbyHandler lobbyHandler;
    private GameHandler gameHandler;

    /**
     * This is the standard constructor that creates the message emitter and receiver and resets the connection.
     */
    public ConnectionHandler() {
        messageEmitter = new MessageEmitter(this);
        messageReceiver = new MessageReceiver(messageEmitter);
        reset();
    }

    /**
     * This method tries to create a simple websocket client and tries to connect it to the specified address.
     * It may block a few seconds to await the connection success or error.
     *
     * @param hostName the hostname of the server to connect to
     * @param port     the port of the server to connect to
     */
    public void connect(String hostName, String port) {
        if (client != null) return;
        try {
            client = new SimpleClient(new URI("ws://" + hostName + ":" + port), messageReceiver, context);
            client.connect();
        } catch (URISyntaxException e) {
            reset();
            logger.error(e.getMessage());
        }
    }

    /**
     * If a connection has been established, this method closes the client websocket and
     * resets the lobby handler, the game handler and the connection.
     */
    public void disconnect() {
        if (client != null) client.close();
        if (lobbyHandler != null) lobbyHandler.reset();
        if (gameHandler != null) gameHandler.reset();
        reset();
    }

    /**
     * This method returns the websocket client.
     *
     * @return the simple websocket client
     */
    public SimpleClient getClient() {
        return client;
    }

    /**
     * This method returns the message emitter of the connection handler.
     *
     * @return the message emitter of the connection handler
     */
    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    /**
     * This method returns the user uuid the server assigned to the client.
     *
     * @return the user uuid
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * This method sets the state context.
     *
     * @param context the state context
     */
    public void setContext(StateContext context) {
        this.context = context;
        messageReceiver.setContext(context);
    }

    /**
     * This method sets the lobby handler.
     *
     * @param lobbyHandler the lobby handler
     */
    public void setLobbyHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
        messageReceiver.setLobbyHandler(lobbyHandler);
    }

    /**
     * This method sets the game handler.
     *
     * @param gameHandler the game handler
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * This method sets the user uuid.
     *
     * @param userId the user uuid
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * This method resets the connection handler, that is, it sets the client and the user uuid to null.
     */
    private void reset() {
        client = null;
        userId = null;
    }
}
