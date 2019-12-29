package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the get available lobbies message.
 */
public class GetAvailableLobbiesMessage extends AbstractMessage {

    /**
     * The standard constructor calls the super constructor which sets the message type and the user uuid.
     *
     * @param userId the user uuid
     */
    public GetAvailableLobbiesMessage(UUID userId) {
        super(MessageType.GetAvailableLobbies, userId);
    }

    /**
     * This method defines whether the given object is equal to this. It is null-pointer proof.
     *
     * @param obj the compared object
     * @return true, if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GetAvailableLobbiesMessage)) return false;

        return super.equals(obj);
    }
}
