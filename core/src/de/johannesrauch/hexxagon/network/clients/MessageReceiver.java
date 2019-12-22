package de.johannesrauch.hexxagon.network.clients;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.events.GameStartedEvent;
import de.johannesrauch.hexxagon.automaton.events.LobbyJoinedEvent;
import de.johannesrauch.hexxagon.automaton.events.LobbyStatusEvent;
import de.johannesrauch.hexxagon.automaton.events.WelcomeEvent;
import de.johannesrauch.hexxagon.controller.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.network.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messages.*;
import de.johannesrauch.hexxagon.view.LobbySelectScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.johannesrauch.hexxagon.network.messages.BaseMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class MessageReceiver {

    private final Logger logger;
    private final ExecutorService executorService;
    private final Gson gson;

    private final Hexxagon parent;

    private LobbySelectScreen lobbySelectScreen;

    public MessageReceiver(Hexxagon parent) {
        logger = LoggerFactory.getLogger(MessageReceiver.class);
        executorService = Executors.newFixedThreadPool(1);
        gson = new Gson();
        this.parent = parent;
    }

    public void handleMessage(String json) {
        executorService.execute(() -> {
            logger.info("Received message: " + json);
            try {
                BaseMessage message = gson.fromJson(json, BaseMessage.class);
                MessageType messageType = message.getMessageType();
                if (messageType.equals(MessageType.Welcome)) {
                    receiveWelcomeMessage(gson.fromJson(json, Welcome.class));
                } else if (messageType.equals(MessageType.AvailableLobbies)) {
                    receiveAvailableLobbiesMessage(gson.fromJson(json, AvailableLobbies.class));
                } else if (messageType.equals(MessageType.LobbyCreated)) {
                    receiveLobbyCreatedMessage(gson.fromJson(json, LobbyCreated.class));
                } else if (messageType.equals(MessageType.LobbyJoined)) {
                    receiveLobbyJoinedMessage(gson.fromJson(json, LobbyJoined.class));
                } else if (messageType.equals(MessageType.LobbyStatus)) {
                    receiveLobbyStatusMessage(gson.fromJson(json, LobbyStatus.class));
                } else if (messageType.equals(MessageType.GameStarted)) {
                    receiveGameStartedMessage(gson.fromJson(json, GameStarted.class));
                } else if (messageType.equals(MessageType.GameStatus)) {
                    receiveGameStatusMessage(gson.fromJson(json, GameStatus.class));
                } else if (messageType.equals(MessageType.Strike)) {
                    receiveStrikeMessage(gson.fromJson(json, Strike.class));
                }
            } catch (JsonSyntaxException jse) {
                logger.error(jse.getMessage());
            }
        });
    }

    public void setLobbySelectScreen(LobbySelectScreen lobbySelectScreen) {
        this.lobbySelectScreen = lobbySelectScreen;
    }

    private void receiveAvailableLobbiesMessage(AvailableLobbies message) {
        ArrayList<Lobby> availableLobbies = (ArrayList<Lobby>) message.getAvailableLobbies();
        lobbySelectScreen.updateAvailableLobbies(availableLobbies);
    }

    private void receiveGameStartedMessage(GameStarted message) {
        parent.getStateContext().reactOnEvent(new GameStartedEvent(message));
    }

    private void receiveGameStatusMessage(GameStatus message) {
        // TODO: Implement actions for game status message
    }

    private void receiveLobbyCreatedMessage(LobbyCreated ignore) {
        parent.getMessageEmitter().sendGetAvailableLobbiesMessage();
    }

    private void receiveLobbyJoinedMessage(LobbyJoined message) {
        parent.getStateContext().reactOnEvent(new LobbyJoinedEvent(message));
    }

    private void receiveLobbyStatusMessage(LobbyStatus message) {
        parent.getStateContext().reactOnEvent(new LobbyStatusEvent(message));
    }

    private void receiveStrikeMessage(Strike message) {
        logger.warn("Received strike: " + message.getStrikeCount() + "of max." + message.getMaxStrikeCount());
    }

    private void receiveWelcomeMessage(Welcome message) {
        parent.getStateContext().reactOnEvent(new WelcomeEvent(message));
    }
}
