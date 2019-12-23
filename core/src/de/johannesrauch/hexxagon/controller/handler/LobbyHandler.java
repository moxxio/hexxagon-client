package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LobbyHandler {

    private final Hexxagon parent;

    private final Logger logger;

    private java.util.List<Lobby> availableLobbies;
    private boolean lobbiesUpdated;
    private boolean lobbyUpdated;
    private Lobby lobby;

    private UUID userId;
    private UUID lobbyId;

    public LobbyHandler(Hexxagon parent) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(Hexxagon.class);
        availableLobbies = new ArrayList<>();
        reset();
    }

    public boolean areLobbiesUpdated() {
        if (lobbiesUpdated) {
            lobbiesUpdated = false;
            return true;
        }
        return false;
    }

    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }

    public void updateLobbies(List<Lobby> availableLobbies) {
        this.availableLobbies.clear();
        this.availableLobbies.addAll(availableLobbies);
        lobbiesUpdated = true;
    }

    public String getPlayerOneUserName() {
        if (lobby == null) return "";
        if (lobby.getPlayerOneUserName() == null) return "";
        return lobby.getPlayerOneUserName();
    }

    public String getPlayerTwoUserName() {
        if (lobby == null) return "";
        if (lobby.getPlayerTwoUserName() == null) return "";
        return lobby.getPlayerTwoUserName();
    }

    public boolean isClientPlayerOne() {
        if (lobby == null) return false;
        return lobby.getPlayerOne().equals(userId);
    }

    public boolean isLobbyReady() {
        if (lobby == null) return false;
        return lobby.getPlayerOne() != null && lobby.getPlayerTwo() != null;
    }

    public boolean isLobbyUpdated() {
        if (lobbyUpdated) {
            lobbyUpdated = false;
            return true;
        }
        return false;
    }

    public void joinedLobby(UUID userId, UUID lobbyId) {
        logger.info("Joined lobby: " + lobbyId.toString());
        this.userId = userId;
        this.lobbyId = lobbyId;
    }

    public void leaveLobby() {
        if (lobbyId != null) {
            logger.info("Left lobby: " + lobbyId.toString());
            parent.getMessageEmitter().sendLeaveLobbyMessage(lobbyId);
        } else logger.warn("LobbyId is null in leaveLobby()!");
        reset();
    }

    public void updateLobby(Lobby lobby) {
        this.lobby = lobby;
        lobbyUpdated = true;
    }

    public void startGame() {
        if (isClientPlayerOne() && isLobbyReady()) parent.getMessageEmitter().sendStartGameMessage(lobbyId);
    }

    public void reset() {
        availableLobbies.clear();
        lobby = null;
        userId = null;
        lobbyId = null;
        lobbiesUpdated = false;
        lobbyUpdated = false;
    }
}
