package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents a leave game message.
 */
public class LeaveGameMessage extends AbstractMessage {

    private UUID gameId;

    /**
     * This standard constructor sets the attributes.
     *
     * @param userId the user uuid
     * @param gameId the game uuid
     */
    public LeaveGameMessage(UUID userId, UUID gameId) {
        super(MessageType.LeaveGame, userId);
        this.gameId = gameId;
    }

    /**
     * This method returns the game uuid.
     *
     * @return the game uuid
     */
    public UUID getGameId() {
        return gameId;
    }
}
