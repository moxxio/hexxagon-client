package de.johannesrauch.hexxagon.network.messages;

import de.johannesrauch.hexxagon.network.board.TileEnum;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class GameMove extends AbstractMessage {

    private UUID gameId;
    private TileEnum moveFrom;
    private TileEnum moveTo;

    public GameMove(UUID userId) {
        super(MessageType.GameMove, userId);
    }

    public UUID getGameId() {
        return gameId;
    }

    public TileEnum getMoveFrom() {
        return moveFrom;
    }

    public TileEnum getMoveTo() {
        return moveTo;
    }
}
