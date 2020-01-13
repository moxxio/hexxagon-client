package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the lobby created message.
 */
public class LobbyCreatedMessage extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyCreated;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId              the user uuid
     * @param lobbyId             the lobby uuid
     * @param successfullyCreated the successfully created boolean
     */
    public LobbyCreatedMessage(UUID userId, UUID lobbyId, Boolean successfullyCreated) {
        super(MessageType.LobbyCreated, userId);
        this.lobbyId = lobbyId;
        this.successfullyCreated = successfullyCreated;
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
     * This method returns whether the lobby was successfully created.
     *
     * @return true, if the lobby was successfully created, false otherwise
     */
    public Boolean getSuccessfullyCreated() {
        return successfullyCreated;
    }
}
