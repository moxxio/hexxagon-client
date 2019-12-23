package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LeaveGameMessage extends AbstractMessage {

    private UUID gameId;

    public LeaveGameMessage(UUID userId, UUID gameId) {
        super(MessageType.LeaveGame, userId);
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
