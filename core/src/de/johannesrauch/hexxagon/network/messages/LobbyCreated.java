package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class LobbyCreated extends AbstractMessage {
	
	public UUID lobbyId;
	public Boolean successfullyCreated;
	
	public LobbyCreated(UUID userId
			, UUID lobbyId
			, Boolean successfullyCreated) {
		super(MessageType.LobbyCreated, userId);
		this.lobbyId = lobbyId;
		this.successfullyCreated = successfullyCreated;
	}
	
}
