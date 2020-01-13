package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the available lobbies message.
 */
public class AvailableLobbiesMessage extends AbstractMessage {

    private List<Lobby> availableLobbies;

    /**
     * This is the standard constructor. It calls the super constructor with the message type and user uuid.
     * It sets the available lobbies.
     *
     * @param userId           the user uuid
     * @param availableLobbies the available lobbies list
     */
    public AvailableLobbiesMessage(UUID userId, List<Lobby> availableLobbies) {
        super(MessageType.AvailableLobbies, userId);
        this.availableLobbies = availableLobbies;
    }

    /**
     * This method returns the available lobbies.
     *
     * @return the available lobbies list
     */
    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }
}
