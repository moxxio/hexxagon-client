package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;
import java.util.UUID;

public class BaseMessage extends AbstractMessage {

    public BaseMessage(MessageType messageType, UUID userId) {
        super(messageType, userId);
    }
}
