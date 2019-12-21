package de.johannesrauch.hexxagon.controller;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.johannesrauch.hexxagon.network.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messages.*;
import de.johannesrauch.hexxagon.view.LobbySelectScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.johannesrauch.hexxagon.network.abstractmessage.BaseMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class MessageReceiver {

    final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    final ExecutorService executorService = Executors.newFixedThreadPool(1);
    final Gson gson = new Gson();

    private Hexxagon parent;
    private ConnectionHandler connectionHandler;
    private LobbyHandler lobbyHandler;
    private GameHandler gameHandler;
    private MessageEmitter messageEmitter;

    private LobbySelectScreen lobbySelectScreen;

    public MessageReceiver() {
        connectionHandler = null;
        gameHandler = null;
        lobbySelectScreen = null;
    }

    public void handleMessage(String json) {
        executorService.execute(() -> {
            logger.info("Received message: " + json);
            try {
                BaseMessage message = gson.fromJson(json, BaseMessage.class);
                MessageType messageType = message.messageType;
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
                logger.error("JsonSyntaxException: " + jse.getMessage());
            }
        });
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void setLobbyHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public void setMessageEmitter(MessageEmitter messageEmitter) {
        this.messageEmitter = messageEmitter;
    }

    public void setLobbySelectScreen(LobbySelectScreen lobbySelectScreen) {
        this.lobbySelectScreen = lobbySelectScreen;
    }

    public void setParent(Hexxagon parent) {
        this.parent = parent;
    }

    private void receiveAvailableLobbiesMessage(AvailableLobbies message) {
        ArrayList<Lobby> availableLobbies = (ArrayList<Lobby>) message.getAvailableLobbies();
        lobbySelectScreen.updateAvailableLobbies(availableLobbies);
    }

    private void receiveGameStartedMessage(GameStarted message) {
        gameHandler.gameStarted(message.userId, message.getGameId(), message.getCreationDate());
        parent.showGameScreen();
    }

    private void receiveGameStatusMessage(GameStatus message) {
        // TODO: Implement actions for game status message
    }

    private void receiveLobbyCreatedMessage(LobbyCreated ignore) {
        messageEmitter.sendGetAvailableLobbiesMessage();
    }

    private void receiveLobbyJoinedMessage(LobbyJoined message) {
        lobbyHandler.lobbyJoined(message.userId, message.getLobbyId());
        lobbySelectScreen.hideProgressBar(0); // TODO: remove the ugly and unnecessary progress bar
        parent.showLobbyJoinedScreen();
    }

    private void receiveLobbyStatusMessage(LobbyStatus message) {
        lobbyHandler.lobbyStatusUpdate(message.getLobby().isClosed,
                message.getLobby().playerOne,
                message.getLobby().playerTwo,
                message.getLobby().playerOneUserName,
                message.getLobby().playerTwoUserName);
    }

    private void receiveStrikeMessage(Strike message) {
        logger.warn("Received strike: " + message.getStrikeCount() + "of max." + message.getMaxStrikeCount());
    }

    private void receiveWelcomeMessage(Welcome message) {
        UUID userId = message.userId;
        connectionHandler.setUserId(userId);
    }
}
