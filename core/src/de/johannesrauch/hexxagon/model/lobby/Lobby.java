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
     * This is the standard constructor. It sets all the attributes.
     *
     * @param lobbyId           the lobby uuid
     * @param lobbyName         the lobby name
     * @param playerOne         the player one uuid
     * @param playerTwo         the player two uuid
     * @param playerOneUserName the player one username
     * @param playerTwoUserName the player two username
     * @param creationDate      the creation date
     * @param isClosed          true, if the lobby is closed, false otherwise
     */
    public Lobby(UUID lobbyId, String lobbyName, UUID playerOne, UUID playerTwo, String playerOneUserName, String playerTwoUserName, Date creationDate, Boolean isClosed) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerOneUserName = playerOneUserName;
        this.playerTwoUserName = playerTwoUserName;
        this.creationDate = creationDate;
        this.isClosed = isClosed;
    }

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
    public Date getCreationDate() {
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

    /**
     * This method defines whether the given object is equal to this. It is null-pointer proof.
     *
     * @param obj the compared object
     * @return true, if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Lobby)) return false;

        Lobby lobby = (Lobby) obj;
        boolean equal;

        if (lobbyId == null) equal = lobby.lobbyId == null;
        else equal = lobbyId.equals(lobby.lobbyId);
        if (lobbyName == null) equal &= lobby.lobbyName == null;
        else equal &= lobbyName.equals(lobby.lobbyName);
        if (playerOne == null) equal &= lobby.playerOne == null;
        else equal &= playerOne.equals(lobby.playerOne);
        if (playerTwo == null) equal &= lobby.playerTwo == null;
        else equal &= playerTwo.equals(lobby.playerTwo);
        if (playerOneUserName == null) equal &= lobby.playerOneUserName == null;
        else equal &= playerOneUserName.equals(lobby.playerOneUserName);
        if (playerTwoUserName == null) equal &= lobby.playerTwoUserName == null;
        else equal &= playerTwoUserName.equals(lobby.playerTwoUserName);
        if (isClosed == null) equal &= lobby.isClosed == null;
        else equal &= isClosed.equals(lobby.isClosed);

        return equal;
    }
}
