package de.johannesrauch.hexxagon.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.johannesrauch.hexxagon.network.clients.MessageEmitter;
import de.johannesrauch.hexxagon.network.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse verwaltet den Zustand einer Lobby.
 * Diese Klasse ist für Sie nicht weiter relevant, da Sie alles bis zur Lobby bereits implementiert bekommen haben.
 *
 * @author Dennis Jehle
 */
public class LobbyHandler {

    final Logger logger = LoggerFactory.getLogger(LobbyHandler.class);

    private MessageEmitter messageEmitter;

    public ArrayList<Lobby> availableLobbies;

    public boolean availableLobbiesUpdated;

    public boolean lobbyIsClosed;

    private UUID userId;

    public UUID lobbyId;

    public UUID playerOne;
    public UUID playerTwo;

    public String playerOneUserName;
    public String playerTwoUserName;

    public boolean clientIsPlayerOne;
    public boolean clientIsPlayerTwo;

    public boolean lobbyReady;

    public boolean initializationCompleted;
    public boolean joinedLobbyUpdated;

    public LobbyHandler() {
        availableLobbies = new ArrayList<>();
    }

    public void updateAvailableLobbies(List<Lobby> availableLobbies) {
        availableLobbies.clear();
        this.availableLobbies.addAll(availableLobbies);
        availableLobbiesUpdated = true;
    }

    public void lobbyJoined(UUID userId, UUID lobbyId) {
        lobbyIsClosed = false;
        this.userId = userId;
        this.lobbyId = lobbyId;
    }

    public void lobbyStatusUpdate(boolean isClosed,
								  UUID playerOne,
								  UUID playerTwo,
								  String playerOneUserName,
								  String playerTwoUserName) {
        if (isClosed) {
            setDefaultValues();
            return;
        }

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.playerOneUserName = (playerOne == null) ? "" : playerOneUserName;
        this.playerTwoUserName = (playerTwo == null) ? "" : playerTwoUserName;

        clientIsPlayerOne = userId.equals(playerOne);
        clientIsPlayerTwo = userId.equals(playerTwo);

        lobbyReady = playerOne != null && playerTwo != null;

        if (!initializationCompleted) {
            initializationCompleted = true;
        }

        joinedLobbyUpdated = true;
    }

    public void sendLeaveLobbyMessage() {
        if (lobbyId != null) {
            messageEmitter.sendLeaveLobbyMessage(lobbyId);
        }
    }

    public void sendStartGameMessage() {
        if (clientIsPlayerOne && playerTwo != null) {
            messageEmitter.sendStartGameMessage(lobbyId);
        }
    }

    private void setDefaultValues() {
        lobbyIsClosed = true;
        lobbyId = null;
        playerOne = null;
        playerTwo = null;
        playerOneUserName = null;
        playerTwoUserName = null;
        clientIsPlayerOne = false;
        clientIsPlayerTwo = false;
        lobbyReady = false;
        initializationCompleted = false;
        joinedLobbyUpdated = false;
    }

    public void setMessageEmitter(MessageEmitter messageEmitter) {
        this.messageEmitter = messageEmitter;
    }
}
