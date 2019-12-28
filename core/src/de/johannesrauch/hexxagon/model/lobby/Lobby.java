package de.johannesrauch.hexxagon.model.lobby;

import java.util.Date;
import java.util.UUID;

/**
 * This lobby represents a lobby state.
 */
public class Lobby {

    private UUID lobbyId;
    private String lobbyName;
    private UUID playerOne;
    private UUID playerTwo;
    private String playerOneUserName;
    private String playerTwoUserName;
    private Date creationDate;
    private Boolean isClosed;

    /**
     * This method returns the lobby uuid.
     *
     * @return the lobby uuid
     */
    public UUID getLobbyId() {
        return lobbyId;
    }

    /**
     * This method returns the lobby name.
     *
     * @return the lobby name
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * This method returns the player one uuid.
     *
     * @return the player one uuid
     */
    public UUID getPlayerOne() {
        return playerOne;
    }

    /**
     * This method returns the player two uuid.
     *
     * @return the player two uuid
     */
    public UUID getPlayerTwo() {
        return playerTwo;
    }

    /**
     * This method returns the player one username.
     *
     * @return the player one username
     */
    public String getPlayerOneUserName() {
        return playerOneUserName;
    }

    /**
     * This method returns the player two username.
     *
     * @return the player two username
     */
    public String getPlayerTwoUserName() {
        return playerTwoUserName;
    }

    /**
     * This method returns the creation date of the lobby.
     *
     * @return the creation date
     */
    private Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method returns whether the lobby is closed.
     *
     * @return true, if the lobby is closed, false otherwise
     */
    public Boolean isClosed() {
        return isClosed;
    }
}
