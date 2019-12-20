package de.johannesrauch.hexxagon.network.lobby;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class Lobby {

    public UUID lobbyId;
    public String lobbyName;

    public UUID playerOne;
    public UUID playerTwo;

    public String playerOneUserName;
    public String playerTwoUserName;

    public Date creationDate;
    public Boolean isClosed;

    public Date getCreationDate() {
        return creationDate;
    }

    public Lobby(String lobbyName) {
        lobbyId = UUID.randomUUID();
        this.lobbyName = lobbyName;
        playerOne = null;
        playerOneUserName = null;
        playerTwo = null;
        playerTwoUserName = null;
        creationDate = new Date();
        isClosed = false;
    }

    public void close() {
        isClosed = true;
    }

    public boolean addPlayer(UUID player, String userName) {
        if (playerOne == null) {
            playerOne = player;
            playerOneUserName = userName;
            return true;
        } else if (playerTwo == null) {
            playerTwo = player;
            playerTwoUserName = userName;
            return true;
        }
        return false;
    }

    public void removePlayer(UUID player) {
        if (playerOne.equals(player)) {
            playerOne = playerTwo;
            playerOneUserName = playerTwoUserName;
            playerTwo = null;
            playerTwoUserName = null;
        } else if (playerTwo.equals(player)) {
            playerTwo = null;
            playerTwoUserName = null;
        }
    }

    public int getPlayerCount() {

        int count = 0;

        if (playerOne != null) {
            count += 1;
        }

        if (playerTwo != null) {
            count += 1;
        }

        return count;

    }

    public UUID invalidateLobby() throws UnsupportedOperationException {

        Instant cd = getCreationDate().toInstant();

        Instant now = Instant.now();

        if (cd.plusSeconds(60 * 5).isBefore(now)) {
            return lobbyId;
        }

        if (cd.plusSeconds(60).isAfter(now) || getPlayerCount() != 0) {
            throw new UnsupportedOperationException("Lobby with [lobbyId:" + lobbyId + "] is still valid.");
        }

        return lobbyId;

    }

    public boolean contains(UUID userId) {
        return playerOne.equals(userId) || playerTwo.equals(userId);
    }

    public boolean validate() {
        // TODO validate lobby

        // lobby is valid
        return true;
    }

}
