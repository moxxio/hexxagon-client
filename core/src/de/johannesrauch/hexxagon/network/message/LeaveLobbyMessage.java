package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LeaveLobbyMessage extends AbstractMessage {

    private UUID lobbyId;

    public LeaveLobbyMessage(UUID userId, UUID lobbyId) {
        super(MessageType.LeaveLobby, userId);
        this.lobbyId = lobbyId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }
}
