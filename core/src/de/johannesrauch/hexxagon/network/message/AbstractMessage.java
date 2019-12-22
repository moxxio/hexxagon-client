package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public abstract class AbstractMessage {
	private MessageType messageType;
	private UUID userId;

	public AbstractMessage(MessageType messageType, UUID userId) {
		this.messageType = messageType;
		this.userId = userId;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public UUID getUserId() {
		return userId;
	}
}
