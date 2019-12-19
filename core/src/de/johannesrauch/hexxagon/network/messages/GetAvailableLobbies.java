package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class GetAvailableLobbies extends AbstractMessage {
	
	public GetAvailableLobbies(UUID userId) {
		super(MessageType.GetAvailableLobbies, userId);
	}
	
}
