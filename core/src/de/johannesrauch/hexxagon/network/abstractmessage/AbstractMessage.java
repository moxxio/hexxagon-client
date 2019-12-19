package de.johannesrauch.hexxagon.network.abstractmessage;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public abstract class AbstractMessage {
	
	public MessageType messageType;
	public UUID userId;
	
	public AbstractMessage(MessageType messageType, UUID userId) {
		this.messageType = messageType;
		this.userId = userId;
	}
	
}
