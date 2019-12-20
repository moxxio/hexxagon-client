package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class StartGame extends AbstractMessage {

    private UUID lobbyId;

    public StartGame(UUID userId, UUID lobbyId) {
        super(MessageType.StartGame, userId);
        this.lobbyId = lobbyId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }
}
