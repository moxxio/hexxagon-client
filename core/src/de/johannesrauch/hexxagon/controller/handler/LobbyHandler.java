package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * This class manages the available lobbies, the user can join, and the lobby state, the user is in.
 */
public class LobbyHandler {

    private final Logger logger = LoggerFactory.getLogger(Hexxagon.class);

    private java.util.List<Lobby> availableLobbies;
    private boolean lobbiesUpdated;
    private boolean lobbyUpdated;
    private Lobby lobby;

    private UUID userId;
    private UUID lobbyId;

    private ConnectionHandler connectionHandler;

    /**
     * This is the standard constructor. It creates the list for the lobbies and resets the lobby handler.
     */
    public LobbyHandler() {
        availableLobbies = new ArrayList<>();
        reset();
    }

    /**
     * This method returns, if the available lobbies have been updated since the last check.
     *
     * @return true, if the lobbies have been updated, false otherwise
     */
    public boolean areLobbiesUpdated() {
        if (lobbiesUpdated) {
            lobbiesUpdated = false;
            return true;
        }
        return false;
    }

    /**
     * This method returns the available lobbies list.
     *
     * @return the available lobbies list
     */
    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }

    /**
     * This method returns the player one username. If the lobby or the username is null, it returns an empty string.
     *
     * @return the player one username or "", if there is no player one username
     */
    public String getPlayerOneUserName() {
        if (lobby == null) return "";
        if (lobby.getPlayerOneUserName() == null) return "";
        return lobby.getPlayerOneUserName();
    }

    /**
     * This method returns the player two username. If the lobby or the username is null, it returns an empty string.
     *
     * @return the player two username or "", if there is no player two username
     */
    public String getPlayerTwoUserName() {
        if (lobby == null) return "";
        if (lobby.getPlayerTwoUserName() == null) return "";
        return lobby.getPlayerTwoUserName();
    }

    /**
     * This method returns, whether this client is player one. If the lobby or the user uuid is null, it returns false.
     *
     * @return true, if the client is player one, false otherwise
     */
    public boolean isClientPlayerOne() {
        if (lobby == null || userId == null) return false;
        return userId.equals(lobby.getPlayerOne());
    }

    /**
     * This method returns, whether the lobby is ready to start the game.
     * A lobby is ready, if there are two players in it.
     * If the lobby is null, it returns false
     *
     * @return true, if the lobby is ready, false otherwise
     */
    public boolean isLobbyReady() {
        if (lobby == null) return false;
        return lobby.getPlayerOne() != null && lobby.getPlayerTwo() != null;
    }

    /**
     * This method returns, whether the lobby in which the user is in has been updated since the last check.
     *
     * @return true, if the lobby the user is in has been updated, otherwise false
     */
    public boolean isLobbyUpdated() {
        if (lobbyUpdated) {
            lobbyUpdated = false;
            return true;
        }
        return false;
    }

    /**
     * This method informs the lobby handler about a received joined lobby message.
     * It sets the user and lobby uuid.
     *
     * @param userId  the assigned user uuid
     * @param lobbyId the lobby uuid the user joined
     */
    public void joinedLobby(UUID userId, UUID lobbyId) {
        logger.info("Joined lobby: " + lobbyId.toString());

        if (this.userId != null && !this.userId.equals(userId)) logger.warn("Inconsistent user uuids!");
        this.userId = userId;
        if (this.lobbyId != null && !this.lobbyId.equals(lobbyId)) logger.warn("Inconsistent lobby uuids!");
        this.lobbyId = lobbyId;
    }

    /**
     * If the user is in a lobby, then this method will leave it. It sends a leave lobby message.
     * Furthermore, it resets the lobby handler.
     */
    public void leaveLobby() {
        if (lobbyId != null) {
            logger.info("Left lobby: " + lobbyId.toString());
            if (connectionHandler != null) connectionHandler.getMessageEmitter().sendLeaveLobbyMessage(lobbyId);
            else logger.warn("MessageEmitter is null in leaveLobby()!");
        } else logger.warn("LobbyId is null in leaveLobby()!");
        reset();
    }

    /**
     * This method updates the available lobbies and sets the lobbies updated flag.
     *
     * @param availableLobbies the latest available lobbies
     */
    public void updateLobbies(List<Lobby> availableLobbies) {
        this.availableLobbies.clear();
        this.availableLobbies.addAll(availableLobbies);
        lobbiesUpdated = true;
    }

    /**
     * This method updates the lobby the user is in. It also sets the lobby updated flag.
     *
     * @param lobby the latest lobby information
     */
    public void updateLobby(Lobby lobby) {
        this.lobby = lobby;
        lobbyUpdated = true;

        if (!lobbyId.equals(lobby.getLobbyId())) logger.warn("Inconsistent lobby uuids!");
        if (userId == null && connectionHandler != null) userId = connectionHandler.getUserId();
        if (lobbyId == null) lobbyId = lobby.getLobbyId();
    }

    /**
     * This method sets the connection handler.
     *
     * @param connectionHandler the connection handler
     */
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    /**
     * This method starts the game. It checks, whether the user is player one and whether the lobby is ready.
     * If that is the case, it sends a start game message.
     */
    public void startGame() {
        if (isClientPlayerOne() && isLobbyReady()) {
            if (connectionHandler != null) connectionHandler.getMessageEmitter().sendStartGameMessage(lobbyId);
            else logger.warn("MessageEmitter is null in startGame()!");
        }
    }

    /**
     * This method resets the lobby handler. That is, it clears the available lobbies, sets the lobby,
     * user uuid and lobby uuid to null and sets the flags to false.
     */
    public void reset() {
        availableLobbies.clear();
        lobby = null;
        userId = null;
        lobbyId = null;
        lobbiesUpdated = false;
        lobbyUpdated = false;
    }
}
