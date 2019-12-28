package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This class represents the join lobby message.
 */
public class JoinLobbyMessage extends AbstractMessage {

    private UUID lobbyId;
    private String userName;

    /**
     * This standard constructor calls the super constructor and sets the attributes.
     *
     * @param userId   the user uuid
     * @param lobbyId  the lobby uuid
     * @param userName the username string
     */
    public JoinLobbyMessage(UUID userId, UUID lobbyId, String userName) {
        super(MessageType.JoinLobby, userId);
        this.lobbyId = lobbyId;
        this.userName = userName;
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
     * This method returns the username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }
}
