package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the lobby joined message.
 */
public class LobbyJoinedMessage extends AbstractMessage {

    private UUID lobbyId;
    private Boolean successfullyJoined;

    /**
     * This standard constructor sets the attributes of the class.
     *
     * @param userId             the user uuid
     * @param lobbyId            the lobby uuid
     * @param successfullyJoined the successfully joined boolean
     */
    public LobbyJoinedMessage(UUID userId, UUID lobbyId, Boolean successfullyJoined) {
        super(MessageType.LobbyJoined, userId);
        this.lobbyId = lobbyId;
        this.successfullyJoined = successfullyJoined;
    }

    /**
     * This method returns the lobby uuid.
     *
     * @return the lobby uuid
     */
    public UUID getLobbyId() {
        return lobbyId;
    }

    /**
     * This method returns whether the lobby was successfully created.
     *
     * @return true, if the lobby was successfully created, false otherwise
     */
    public Boolean getSuccessfullyJoined() {
        return successfullyJoined;
    }
}
