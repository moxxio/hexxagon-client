package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class LobbyCreated extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyCreated;

    public LobbyCreated(UUID userId, UUID lobbyId, Boolean successfullyCreated) {
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
