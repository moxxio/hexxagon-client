package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class GetAvailableLobbiesMessage extends AbstractMessage {

    public GetAvailableLobbiesMessage(UUID userId) {
        super(MessageType.GetAvailableLobbies, userId);
    }
}
