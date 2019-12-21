package de.johannesrauch.hexxagon.network.lobby;

import java.util.Date;
import java.util.UUID;

public class Lobby {

    private UUID lobbyId;
    private String lobbyName;
    private UUID playerOne;
    private UUID playerTwo;
    private String playerOneUserName;
    private String playerTwoUserName;
    private Date creationDate;
    private Boolean isClosed;

    public UUID getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public UUID getPlayerOne() {
        return playerOne;
    }

    public UUID getPlayerTwo() {
        return playerTwo;
    }

    public String getPlayerOneUserName() {
        return playerOneUserName;
    }

    public String getPlayerTwoUserName() {
        return playerTwoUserName;
    }

    private Date getCreationDate() {
        return creationDate;
    }

    public Boolean isClosed() {
        return isClosed;
    }
}
