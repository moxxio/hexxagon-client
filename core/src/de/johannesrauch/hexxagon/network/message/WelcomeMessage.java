package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the welcome message.
 */
public class WelcomeMessage extends AbstractMessage {

    private String welcomeMessage;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId         the user uuid
     * @param welcomeMessage the welcome message
     */
    public WelcomeMessage(UUID userId, String welcomeMessage) {
        super(MessageType.Welcome, userId);
        this.welcomeMessage = welcomeMessage;
    }

    /**
     * This method returns the welcome message.
     *
     * @return the welcome message
     */
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
