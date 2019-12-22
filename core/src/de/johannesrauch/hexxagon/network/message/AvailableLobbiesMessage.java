package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.List;
import java.util.UUID;

public class AvailableLobbiesMessage extends AbstractMessage {

    private List<Lobby> availableLobbies;

    public AvailableLobbiesMessage(UUID userId, List<Lobby> availableLobbies) {
        super(MessageType.AvailableLobbies, userId);
        this.availableLobbies = availableLobbies;
    }

    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }
}
