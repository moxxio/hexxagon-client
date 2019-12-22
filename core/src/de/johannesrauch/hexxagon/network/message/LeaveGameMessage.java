package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LeaveGameMessage extends AbstractMessage {

    private UUID gameId;

    public LeaveGameMessage(UUID userId) {
        super(MessageType.LeaveGame, userId);
    }

    public UUID getGameId() {
        return gameId;
    }
}
