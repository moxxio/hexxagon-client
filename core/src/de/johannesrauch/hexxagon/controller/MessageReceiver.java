package de.johannesrauch.hexxagon.controller;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.johannesrauch.hexxagon.network.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messages.LobbyCreated;
import de.johannesrauch.hexxagon.network.messages.LobbyJoined;
import de.johannesrauch.hexxagon.view.GameScreen;
import de.johannesrauch.hexxagon.view.LobbySelectScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.johannesrauch.hexxagon.network.abstractmessage.BaseMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;
import de.johannesrauch.hexxagon.network.messages.AvailableLobbies;
import de.johannesrauch.hexxagon.network.messages.GameStarted;
import de.johannesrauch.hexxagon.network.messages.LobbyStatus;
import de.johannesrauch.hexxagon.network.messages.Strike;
import de.johannesrauch.hexxagon.network.messages.Welcome;

public class MessageReceiver {

    final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    final ExecutorService executorService = Executors.newFixedThreadPool(1);
    final Gson gson = new Gson();

    private Hexxagon parent;
    private ConnectionHandler connectionHandler;
    private LobbyHandler lobbyHandler;
    private GameHandler gameHandler;

    private LobbySelectScreen lobbySelectScreen;
    private GameScreen gameScreen;

    public void setParent(Hexxagon parent) {
        this.parent = parent;
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void setLobbyHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void setLobbySelectScreen(LobbySelectScreen lobbySelectScreen) {
        this.lobbySelectScreen = lobbySelectScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public MessageReceiver() {
        connectionHandler = null;
        gameHandler = null;
        lobbySelectScreen = null;
    }

    public void handleMessage(String json) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

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
                        // TODO: implement
                    } else if (messageType.equals(MessageType.Strike)) {
                        receiveStrikeMessage(gson.fromJson(json, Strike.class));
                    }

                } catch (JsonSyntaxException jse) {
                    logger.error("JsonSyntaxException: " + jse.getMessage());
                }

            }
        });
    }

    private void receiveWelcomeMessage(Welcome message) {
        UUID userId = message.userId;
        connectionHandler.setUserId(userId);
    }

    private void receiveAvailableLobbiesMessage(AvailableLobbies message) {
        ArrayList<Lobby> availableLobbies = (ArrayList<Lobby>) message.getAvailableLobbies();
        lobbySelectScreen.updateAvailableLobbies(availableLobbies);
    }

    private void receiveLobbyCreatedMessage(LobbyCreated message) {
        // TODO: An dieser Stelle könnte etwas passieren um die UX zu verbessern.
        //       Es könnte zum Beispiel der LobbySelectScreen aktualisiert werden
        //       um nicht extra auf den "REFRESH" Button klicken zu müssen.
    }

    /**
     * Falls Sie die Animation des Ladebalkens beim joinen einer Lobby nicht sehen wollen
     * können Sie hier den Wert des Parameters für lobbySelectScreen.hideProgressBar(xyz)
     * anpassen.
     *
     * @param message die LobbyJoined Message welche vom Server gesendet wurde
     */
    private void receiveLobbyJoinedMessage(LobbyJoined message) {
        lobbyHandler.lobbyJoined(message.userId, message.getLobbyId());
        lobbySelectScreen.hideProgressBar(1000);
        parent.showLobbyJoinedScreen();
    }

    private void receiveLobbyStatusMessage(LobbyStatus message) {
        lobbyHandler.lobbyStatusUpdate(message.getLobby().isClosed,
                message.getLobby().playerOne,
                message.getLobby().playerTwo,
                message.getLobby().playerOneUserName,
                message.getLobby().playerTwoUserName);
    }

    private void receiveGameStartedMessage(GameStarted message) {
        // initialize gameHandler
        gameHandler.gameStarted(message.userId, message.getGameId(), message.getCreationDate());
        // change screen to GAMESCREEN
        parent.showGameScreen();
    }

    private void receiveStrikeMessage(Strike message) {
        // TODO: Falls Sie eine illegale Nachricht an den Server geschickt haben, bekommen Sie wie im Netzwerkstandard definiert eine
        // Strike Message, falls Sie darüber informiert werden wollen, können Sie hier die entsprechende Logik dafür implementieren.
    }

}
