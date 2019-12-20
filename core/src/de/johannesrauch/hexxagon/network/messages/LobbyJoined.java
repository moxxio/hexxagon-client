package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class LobbyJoined extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyJoined;

    public LobbyJoined(UUID userId, UUID lobbyId, Boolean successfullyJoined) {
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
