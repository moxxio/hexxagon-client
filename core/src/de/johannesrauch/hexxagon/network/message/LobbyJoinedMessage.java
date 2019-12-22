package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class LobbyJoinedMessage extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyJoined;

    public LobbyJoinedMessage(UUID userId, UUID lobbyId, Boolean successfullyJoined) {
        super(MessageType.LobbyJoined, userId);
        this.lobbyId = lobbyId;
        this.successfullyJoined = successfullyJoined;
    }

	public UUID getLobbyId() {
		return lobbyId;
	}

	public Boolean getSuccessfullyJoined() {
		return successfullyJoined;
	}
}
