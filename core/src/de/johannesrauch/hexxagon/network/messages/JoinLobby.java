package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class JoinLobby extends AbstractMessage {
	
	public UUID lobbyId;
	public String userName;
	
	public JoinLobby(UUID userId, UUID lobbyId, String userName) {
		super(MessageType.JoinLobby, userId);
		this.lobbyId = lobbyId;
		this.userName = userName;
	}
	
}
