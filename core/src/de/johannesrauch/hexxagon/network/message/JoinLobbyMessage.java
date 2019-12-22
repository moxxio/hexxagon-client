package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class JoinLobbyMessage extends AbstractMessage {

    private UUID lobbyId;
    private String userName;

    public JoinLobbyMessage(UUID userId, UUID lobbyId, String userName) {
        super(MessageType.JoinLobby, userId);
        this.lobbyId = lobbyId;
        this.userName = userName;
    }

	public UUID getLobbyId() {
		return lobbyId;
	}

	public String getUserName() {
		return userName;
	}
}
