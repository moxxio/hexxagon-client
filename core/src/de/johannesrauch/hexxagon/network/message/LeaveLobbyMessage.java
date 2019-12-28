package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This method represents a leave lobby message.
 */
public class LeaveLobbyMessage extends AbstractMessage {

    private UUID lobbyId;

    /**
     * This standard constructor sets the attributes.
     *
     * @param userId  the user uuid
     * @param lobbyId the lobby uuid
     */
    public LeaveLobbyMessage(UUID userId, UUID lobbyId) {
        super(MessageType.LeaveLobby, userId);
        this.lobbyId = lobbyId;
    }

    /**
     * This method returns the lobby uuid.
     *
     * @return the lobby uuid
     */
    public UUID getLobbyId() {
        return lobbyId;
    }
}
