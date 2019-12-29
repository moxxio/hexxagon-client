package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This method represents a message basis.
 */
public abstract class AbstractMessage {

    private MessageType messageType;
    private UUID userId;

    /**
     * This is the standard constructor, which sets the message type and user id.
     *
     * @param messageType the message type
     * @param userId      the user uuid
     */
    public AbstractMessage(MessageType messageType, UUID userId) {
        this.messageType = messageType;
        this.userId = userId;
    }

    /**
     * This method returns the message type.
     *
     * @return the message type
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * This method returns the user uuid.
     *
     * @return the user uuid
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * TODO: maybe override equals in all messages, not just the sent ones
     * This method defines whether the given object is equal to this. It is null-pointer proof.
     *
     * @param obj the compared object
     * @return true, if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractMessage)) return false;

        boolean equal;
        AbstractMessage message = (AbstractMessage) obj;

        if (messageType == null) equal = message.messageType == null;
        else equal = messageType.equals(message.messageType);
        if (userId == null) equal &= message.userId == null;
        else equal &= userId.equals(message.userId);

        return equal;
    }
}
