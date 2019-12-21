package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class CreateNewLobby extends AbstractMessage {

    private String lobbyName;

    public CreateNewLobby(UUID userId, String lobbyName) {
        super(MessageType.CreateNewLobby, userId);
        this.lobbyName = lobbyName;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}
