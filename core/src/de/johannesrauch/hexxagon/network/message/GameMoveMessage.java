package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class GameMoveMessage extends AbstractMessage {

    private UUID gameId;
    private TileEnum moveFrom;
    private TileEnum moveTo;

    public GameMoveMessage(UUID userId, UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
        super(MessageType.GameMove, userId);
        this.gameId = gameId;
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
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
