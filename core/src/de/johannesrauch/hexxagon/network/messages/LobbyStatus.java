package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.lobby.Lobby;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class LobbyStatus extends AbstractMessage {

    private UUID lobbyId;
    private Lobby lobby;

    public LobbyStatus(UUID userId, UUID lobbyId, Lobby lobby) {
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
