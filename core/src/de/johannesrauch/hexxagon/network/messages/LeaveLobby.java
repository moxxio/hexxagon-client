package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class LeaveLobby extends AbstractMessage {

	public UUID lobbyId;
	
	public LeaveLobby(UUID userId, UUID lobbyId) {
		super(MessageType.LeaveLobby, userId);
		this.lobbyId = lobbyId;
	}

}
