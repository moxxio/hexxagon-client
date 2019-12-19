package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class CreateNewLobby extends AbstractMessage {
	
	public String lobbyName;
	
	public CreateNewLobby(UUID userId, String lobbyName) {
		super(MessageType.CreateNewLobby, userId);
		this.lobbyName = lobbyName;
	}
	
}
