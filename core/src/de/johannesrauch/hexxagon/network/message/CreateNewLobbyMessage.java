package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents a create new lobby message.
 */
public class CreateNewLobbyMessage extends AbstractMessage {

    private String lobbyName;

    /**
     * This is the standard constructor which calls the super constructor to set message type and user uuid.
     *
     * @param userId    the user uuid
     * @param lobbyName the lobby name
     */
    public CreateNewLobbyMessage(UUID userId, String lobbyName) {
        super(MessageType.CreateNewLobby, userId);
        this.lobbyName = lobbyName;
    }

    /**
     * This method returns the lobby name.
     *
     * @return the lobby name
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * This method defines whether the given object is equal to this. It is null-pointer proof.
     *
     * @param obj the compared object
     * @return true, if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CreateNewLobbyMessage)) return false;

        CreateNewLobbyMessage message = (CreateNewLobbyMessage) obj;
        boolean equal = super.equals(obj);

        if (lobbyName == null) equal &= message.lobbyName == null;
        else equal &= lobbyName.equals(message.lobbyName);

        return equal;
    }
}
