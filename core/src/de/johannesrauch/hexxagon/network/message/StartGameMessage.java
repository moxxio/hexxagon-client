package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the start game message.
 */
public class StartGameMessage extends AbstractMessage {

    private UUID lobbyId;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId  the user uuid
     * @param lobbyId the lobby uuid
     */
    public StartGameMessage(UUID userId, UUID lobbyId) {
        super(MessageType.StartGame, userId);
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
