package de.johannesrauch.hexxagon.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;
import de.johannesrauch.hexxagon.fsm.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private final Hexxagon parent;
    private final Gson gson;

    public MessageReceiver(Hexxagon parent) {
        this.parent = parent;
        gson = new Gson();
    }

    public void handleMessage(String json) {
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

    private void receivedWelcome(WelcomeMessage message) {
        logger.info("Received welcome message");
        parent.getContext().reactOnEvent(new WelcomeEvent(message));
    }

    private void receivedAvailableLobbies(AvailableLobbiesMessage message) {
        logger.info("Received available lobbies");
        parent.getLobbyHandler().updateLobbies(message.getAvailableLobbies());
    }

    private void receivedLobbyCreated(LobbyCreatedMessage message) {
        logger.info("Received created lobby");
        if (message.getSuccessfullyCreated()) parent.getMessageEmitter().sendGetAvailableLobbiesMessage();
    }

    private void receivedLobbyJoined(LobbyJoinedMessage message) {
        logger.info("Received joined lobby");
        parent.getContext().reactOnEvent(new JoinedLobbyEvent(message));
    }

    private void receivedLobbyStatus(LobbyStatusMessage message) {
        logger.info("Received lobby status");
        parent.getContext().reactOnEvent(new LobbyStatusEvent(message));
    }

    private void receivedGameStarted(GameStartedMessage message) {
        logger.info("Received start game");
        parent.getContext().reactOnEvent(new GameStartedEvent(message));
    }

    private void receivedGameStatus(GameStatusMessage message) {
        logger.info("Received game status");
        parent.getContext().reactOnEvent(new GameStatusEvent(message));
    }

    private void receivedStrike(StrikeMessage message) {
        logger.warn("Received strike " + message.getStrikeCount() + " of " + message.getMaxStrikeCount());
    }
}
