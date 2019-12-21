package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class BaseMessage extends AbstractMessage {

    public BaseMessage(MessageType messageType, UUID userId) {
        super(messageType, userId);
    }
}
