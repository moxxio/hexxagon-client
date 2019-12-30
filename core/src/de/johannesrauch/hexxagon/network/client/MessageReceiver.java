package de.johannesrauch.hexxagon.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles all received messages from the websocket client.
 * It converts the json messages back to java objects.
 * It triggers the effects the events may have on the finite-state machine of the client.
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class MessageReceiver {

    private final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private final Gson gson = new Gson();

    private StateContext context;
    private LobbyHandler lobbyHandler;
    private MessageEmitter messageEmitter;

    /**
     * This is the standard constructor which sets the message emitter.
     *
     * @param messageEmitter the message emitter
     */
    public MessageReceiver(MessageEmitter messageEmitter) {
        this.messageEmitter = messageEmitter;
    }

    /**
     * This method handles a message which was received.
     * It identifies the message type and reconstructs the java objects.
     *
     * @param json the received json string
     */
    public void handleMessage(String json) {
        logger.info("Received: " + json);
        try {
            BaseMessage message = gson.fromJson(json, BaseMessage.class);
            MessageType messageType = message.getMessageType();
            if (messageType.equals(MessageType.Welcome)) {
                receivedWelcome(gson.fromJson(json, WelcomeMessage.class));
            } else if (messageType.equals(MessageType.AvailableLobbies)) {
                receivedAvailableLobbies(gson.fromJson(json, AvailableLobbiesMessage.class));
            } else if (messageType.equals(MessageType.LobbyCreated)) {
                receivedLobbyCreated(gson.fromJson(json, LobbyCreatedMessage.class));
            } else if (messageType.equals(MessageType.LobbyJoined)) {
                receivedLobbyJoined(gson.fromJson(json, LobbyJoinedMessage.class));
            } else if (messageType.equals(MessageType.LobbyStatus)) {
                receivedLobbyStatus(gson.fromJson(json, LobbyStatusMessage.class));
            } else if (messageType.equals(MessageType.GameStarted)) {
                receivedGameStarted(gson.fromJson(json, GameStartedMessage.class));
            } else if (messageType.equals(MessageType.GameStatus)) {
                receivedGameStatus(gson.fromJson(json, GameStatusMessage.class));
            } else if (messageType.equals(MessageType.Strike)) {
                receivedStrike(gson.fromJson(json, StrikeMessage.class));
            }
        } catch (JsonSyntaxException jse) {
            logger.error(jse.getMessage());
        }
    }

    /**
     * This method sets the state context which handles the reaction on some of the messages.
     *
     * @param context the state context
     */
    public void setContext(StateContext context) {
        this.context = context;
    }

    /**
     * This method sets the lobby handler so that the available lobbies can get updated directly.
     *
     * @param lobbyHandler the lobby handler
     */
    public void setLobbyHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    /**
     * This method calls the state context to inform the finite-state machine about the welcome message.
     *
     * @param message the welcome message
     */
    private void receivedWelcome(WelcomeMessage message) {
        if (context != null) context.reactToReceivedWelcome(message);
        else logger.warn("Received welcome but context is null!");
    }

    /**
     * This method informs the lobby handler about the most recent available lobbies.
     *
     * @param message the available lobbies message
     */
    private void receivedAvailableLobbies(AvailableLobbiesMessage message) {
        if (lobbyHandler != null) lobbyHandler.updateLobbies(message.getAvailableLobbies());
        else logger.warn("Received available lobbies but lobby handler is null!");
    }

    /**
     * This method advices the message emitter to send a get available lobbies message in order to automatically
     * refresh the available lobbies after the user created one.
     *
     * @param message the lobby created message
     */
    private void receivedLobbyCreated(LobbyCreatedMessage message) {
        if (message.getSuccessfullyCreated()) {
            if (messageEmitter != null) messageEmitter.sendGetAvailableLobbiesMessage();
            else logger.warn("Received lobby created but message emitter is null!");
        }
    }

    /**
     * This method calls the state context to inform the finite-state machine about the joined lobby message.
     *
     * @param message the lobby joined message
     */
    private void receivedLobbyJoined(LobbyJoinedMessage message) {
        if (context != null) context.reactToReceivedLobbyJoined(message);
        else logger.warn("Received lobby joined but context is null!");
    }

    /**
     * This method calls the state context to inform the finite-state machine about the lobby status message.
     *
     * @param message the lobby status message
     */
    private void receivedLobbyStatus(LobbyStatusMessage message) {
        if (context != null) context.reactToReceivedLobbyStatus(message);
        else logger.warn("Received lobby status but context is null!");
    }

    /**
     * This method calls the state context to inform the finite-state machine about the game started message.
     *
     * @param message the game started message
     */
    private void receivedGameStarted(GameStartedMessage message) {
        if (context != null) context.reactToReceivedGameStarted(message);
        else logger.warn("Received game started but context is null!");
    }

    /**
     * This method calls the state context to inform the finite-state machine about the game status message.
     *
     * @param message the game status message
     */
    private void receivedGameStatus(GameStatusMessage message) {
        if (context != null) context.reactToReceivedGameStatus(message);
        else logger.warn("Received game status but context is null!");
    }

    /**
     * This method logs the received strike.
     *
     * @param message the strike message
     */
    private void receivedStrike(StrikeMessage message) {
        logger.warn("Received strike " + message.getStrikeCount() + " of " + message.getMaxStrikeCount());
    }
}
