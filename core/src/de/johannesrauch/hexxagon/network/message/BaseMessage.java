package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the basis message. It only contains the message type and the user uuid.
 */
public class BaseMessage extends AbstractMessage {

    /**
     * This is the standard constructor which calls super to set the message type and the user uuid.
     *
     * @param messageType the message type
     * @param userId      the user uuid
     */
    public BaseMessage(MessageType messageType, UUID userId) {
        super(messageType, userId);
    }
}
