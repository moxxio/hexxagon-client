package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the lobby status message.
 */
public class LobbyStatusMessage extends AbstractMessage {

    private UUID lobbyId;
    private Lobby lobby;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId  the user uuid
     * @param lobbyId the lobby uuid
     * @param lobby   the lobby
     */
    public LobbyStatusMessage(UUID userId, UUID lobbyId, Lobby lobby) {
        super(MessageType.LobbyStatus, userId);
        this.lobbyId = lobbyId;
        this.lobby = lobby;
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
     * This method returns the lobby.
     *
     * @return the lobby
     */
    public Lobby getLobby() {
        return lobby;
    }
}
