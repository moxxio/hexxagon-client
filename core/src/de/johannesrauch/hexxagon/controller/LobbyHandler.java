package de.johannesrauch.hexxagon.controller;

import java.util.ArrayList;
import java.util.UUID;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class maintains the condition of the lobby.
 *
 * @author Dennis Jehle
 */
public class LobbyHandler {

    final Logger logger = LoggerFactory.getLogger(LobbyHandler.class);

    private Hexxagon parent;

    public ArrayList<Lobby> availableLobbies;
    public boolean availableLobbiesUpdated;
    public boolean lobbyIsClosed;

    private UUID userId;
    public UUID lobbyId;

    private Lobby lobby;

    private boolean isClientPlayerOne;
    private boolean isClientPlayerTwo;
    private boolean isLobbyReady;
    private boolean isInitComplete;
    private boolean isJoinedLobbyUpdated;

    public LobbyHandler(Hexxagon parent) {
        this.parent = parent;
        availableLobbies = new ArrayList<>();
        setDefaultValues();
    }

    public String getPlayerOneUserName() {
        return lobby.getPlayerOneUserName() != null ? lobby.getPlayerOneUserName() : "";
    }

    public String getPlayerTwoUserName() {
        return lobby.getPlayerTwoUserName() != null ? lobby.getPlayerTwoUserName() : "";
    }

    public boolean isClientPlayerOne() {
        return isClientPlayerOne;
    }

    public boolean isLobbyReady() {
        return isLobbyReady;
    }

    public boolean isInitComplete() {
        return isInitComplete;
    }

    public boolean isJoinedLobbyUpdated() {
        return isJoinedLobbyUpdated;
    }

    public void lobbyJoined(UUID userId, UUID lobbyId) {
        lobbyIsClosed = false;
        this.userId = userId;
        this.lobbyId = lobbyId;
    }

    public void lobbyStatusUpdate(Lobby lobby) {
        if (lobby.isClosed()) {
            setDefaultValues();
        } else {
            this.lobby = lobby;

            isClientPlayerOne = userId.equals(lobby.getPlayerOne());
            isClientPlayerTwo = userId.equals(lobby.getPlayerTwo());
            isLobbyReady = lobby.getPlayerOne() != null && lobby.getPlayerTwo() != null;
            if (!isInitComplete) {
                isInitComplete = true;
            }
            isJoinedLobbyUpdated = true;
        }
    }

    public void sendLeaveLobbyMessage() {
        if (lobbyId != null) {
            parent.getMessageEmitter().sendLeaveLobbyMessage(lobbyId);
            setDefaultValues();
        }
    }

    public void sendStartGameMessage() {
        if (isClientPlayerOne && lobby != null && lobby.getPlayerTwo() != null) {
            parent.getMessageEmitter().sendStartGameMessage(lobbyId);
        }
    }

    private void setDefaultValues() {
        lobbyIsClosed = true;
        lobbyId = null;
        lobby = null;
        isClientPlayerOne = false;
        isClientPlayerTwo = false;
        isLobbyReady = false;
        isInitComplete = false;
        isJoinedLobbyUpdated = false;
    }
}
