package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class Welcome extends AbstractMessage {
	
	public String welcomeMessage;
	
	public Welcome(UUID userId, String welcomeMessage) {
		super(MessageType.Welcome, userId);
		this.welcomeMessage = welcomeMessage;
	}

}
