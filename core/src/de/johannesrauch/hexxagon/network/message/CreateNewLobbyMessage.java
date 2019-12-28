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
}
