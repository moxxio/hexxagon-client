package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the start game message.
 */
public class StartGameMessage extends AbstractMessage {

    private UUID lobbyId;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId  the user uuid
     * @param lobbyId the lobby uuid
     */
    public StartGameMessage(UUID userId, UUID lobbyId) {
        super(MessageType.StartGame, userId);
        this.lobbyId = lobbyId;
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
     * This method defines whether the given object is equal to this. It is null-pointer proof.
     *
     * @param obj the compared object
     * @return true, if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StartGameMessage)) return false;

        StartGameMessage message = (StartGameMessage) obj;
        boolean equal = super.equals(obj);

        if (lobbyId == null) equal &= message.lobbyId == null;
        else equal &= lobbyId.equals(message.lobbyId);

        return equal;
    }
}
