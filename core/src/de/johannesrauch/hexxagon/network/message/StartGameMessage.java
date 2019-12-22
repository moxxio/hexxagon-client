package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class StartGameMessage extends AbstractMessage {

    private UUID lobbyId;

    public StartGameMessage(UUID userId, UUID lobbyId) {
        super(MessageType.StartGame, userId);
        this.lobbyId = lobbyId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }
}
