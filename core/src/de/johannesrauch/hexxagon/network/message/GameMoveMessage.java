package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the game move message.
 */
public class GameMoveMessage extends AbstractMessage {

    private UUID gameId;
    private TileEnum moveFrom;
    private TileEnum moveTo;

    /**
     * This standard constructors sets the attributes of the message.
     *
     * @param userId   the user uuid
     * @param gameId   the game uuid
     * @param moveFrom the tile which gets moved
     * @param moveTo   the tile where the moved from tile gets moved to
     */
    public GameMoveMessage(UUID userId, UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
        super(MessageType.GameMove, userId);
        this.gameId = gameId;
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
    }

    /**
     * This method returns the game uuid.
     *
     * @return the game uuid
     */
    public UUID getGameId() {
        return gameId;
    }

    /**
     * This method returns the move from tile.
     *
     * @return the move from tile
     */
    public TileEnum getMoveFrom() {
        return moveFrom;
    }

    /**
     * This method returns the move to tile.
     *
     * @return the move to tile
     */
    public TileEnum getMoveTo() {
        return moveTo;
    }
}
