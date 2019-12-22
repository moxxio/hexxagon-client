package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class CreateNewLobbyMessage extends AbstractMessage {

    private String lobbyName;

    public CreateNewLobbyMessage(UUID userId, String lobbyName) {
        super(MessageType.CreateNewLobby, userId);
        this.lobbyName = lobbyName;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}
