package de.johannesrauch.hexxagon.network.messages;

import java.util.Date;
import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class GameStarted extends AbstractMessage {

    private UUID gameId;
    private Date creationDate;

    public GameStarted(UUID userId
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
