package de.johannesrauch.hexxagon.network.messages;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LeaveGame extends AbstractMessage {

    private UUID gameId;

    public LeaveGame(UUID userId) {
        super(MessageType.LeaveGame, userId);
    }

    public UUID getGameId() {
        return gameId;
    }
}
