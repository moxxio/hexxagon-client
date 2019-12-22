package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LobbyStatusMessage extends AbstractMessage {

    private UUID lobbyId;
    private Lobby lobby;

    public LobbyStatusMessage(UUID userId, UUID lobbyId, Lobby lobby) {
        super(MessageType.LobbyStatus, userId);
        this.lobbyId = lobbyId;
        this.lobby = lobby;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
