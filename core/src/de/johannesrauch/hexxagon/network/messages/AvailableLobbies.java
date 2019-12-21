package de.johannesrauch.hexxagon.network.messages;

import java.util.List;
import java.util.UUID;

import de.johannesrauch.hexxagon.network.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class AvailableLobbies extends AbstractMessage {

    private List<Lobby> availableLobbies;

    public AvailableLobbies(UUID userId, List<Lobby> availableLobbies) {
        super(MessageType.AvailableLobbies, userId);
        this.availableLobbies = availableLobbies;
    }

    public List<Lobby> getAvailableLobbies() {
        return availableLobbies;
    }
}
