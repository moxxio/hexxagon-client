package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.Date;
import java.util.UUID;

public class GameStartedMessage extends AbstractMessage {

    private UUID gameId;
    private Date creationDate;

    public GameStartedMessage(UUID userId
            , UUID gameId
            , Date creationDate) {
        super(MessageType.GameStarted, userId);
        this.gameId = gameId;
        this.creationDate = creationDate;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
