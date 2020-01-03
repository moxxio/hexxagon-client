package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.Date;
import java.util.UUID;

/**
 * This class represents the game started message.
 */
public class GameStartedMessage extends AbstractMessage {

    private UUID gameId;
    private Date creationDate;

    /**
     * This standard constructor calls the super constructor and sets the class attributes.
     *
     * @param userId       the user uuid
     * @param gameId       the game uuid
     * @param creationDate the creation date
     */
    public GameStartedMessage(UUID userId, UUID gameId, Date creationDate) {
        super(MessageType.GameStarted, userId);
        this.gameId = gameId;
        this.creationDate = creationDate;
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
     * This method returns the creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }
}
