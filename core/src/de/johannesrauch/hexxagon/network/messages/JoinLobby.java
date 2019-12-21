package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class JoinLobby extends AbstractMessage {

    private UUID lobbyId;
    private String userName;

    public JoinLobby(UUID userId, UUID lobbyId, String userName) {
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
