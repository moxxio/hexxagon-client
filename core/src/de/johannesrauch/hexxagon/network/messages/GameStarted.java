package de.johannesrauch.hexxagon.network.messages;

import java.util.Date;
import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class GameStarted extends AbstractMessage {

	public UUID gameId;
	public Date creationDate;
	
	public GameStarted(UUID userId
			, UUID gameId
			, Date creationDate) {
		super(MessageType.GameStarted, userId);
		this.gameId = gameId;
		this.creationDate = creationDate;
	}

}
