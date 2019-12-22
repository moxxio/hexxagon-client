package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class WelcomeMessage extends AbstractMessage {

    private String welcomeMessage;

    public WelcomeMessage(UUID userId, String welcomeMessage) {
        super(MessageType.Welcome, userId);
        this.welcomeMessage = welcomeMessage;
    }

	public String getWelcomeMessage() {
		return welcomeMessage;
	}
}
