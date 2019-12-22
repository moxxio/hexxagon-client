package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LobbyCreatedMessage extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyCreated;

    public LobbyCreatedMessage(UUID userId, UUID lobbyId, Boolean successfullyCreated) {
        super(MessageType.LobbyCreated, userId);
        this.lobbyId = lobbyId;
        this.successfullyCreated = successfullyCreated;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public Boolean getSuccessfullyCreated() {
        return successfullyCreated;
    }
}
