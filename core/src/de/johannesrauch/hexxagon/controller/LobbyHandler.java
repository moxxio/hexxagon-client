package de.johannesrauch.hexxagon.controller;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LobbyHandler {

    private final Hexxagon parent;

    private final Logger logger;

    private java.util.List<Lobby> availableLobbies;
    private boolean updatedLobbies;
    private Lobby lobby;

    private UUID userId;
    private UUID lobbyId;

    public LobbyHandler(Hexxagon parent) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(Hexxagon.class);
        availableLobbies = new ArrayList<>();
        reset();
    }

    public void clearAvailableLobbies() {
        availableLobbies.clear();
    }

    public boolean areLobbiesUpdated() {
        if (updatedLobbies) {
            updatedLobbies = false;
            return true;
        } else return false;
    }

    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }

    public boolean isClientPlayerOne() {
        if (lobby == null) return false;
        return lobby.getPlayerOne().equals(userId);
    }

    public boolean isLobbyReady() {
        if (lobby == null) return false;
        return lobby.getPlayerOne() != null && lobby.getPlayerTwo() != null;
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

    public void updateLobbies(List<Lobby> availableLobbies) {
        this.availableLobbies.clear();
        this.availableLobbies.addAll(availableLobbies);
        updatedLobbies = true;
    }

    private void reset() {
        lobby = null;
        userId = null;
        lobbyId = null;
    }
}
