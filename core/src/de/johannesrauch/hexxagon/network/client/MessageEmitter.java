package de.johannesrauch.hexxagon.network.client;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.message.*;
import org.java_websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * This class constructs all messages.
 * It converts the java objects to json strings and sends them via the websocket client of the connection.
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class MessageEmitter {

    private final Logger logger = LoggerFactory.getLogger(MessageEmitter.class);
    private final Gson gson = new Gson();

    private final ConnectionHandler connection;

    /**
     * This is the standard constructor which sets the connection handler.
     *
     * @param connection the connection handler
     */
    public MessageEmitter(ConnectionHandler connection) {
        this.connection = connection;
    }

    /**
     * This method constructs the get available lobbies message.
     * Afterwards it calls send message with the constructed message.
     */
    public void sendGetAvailableLobbiesMessage() {
        UUID userId = connection.getUserId();
        GetAvailableLobbiesMessage getAvailableLobbies = new GetAvailableLobbiesMessage(userId);
        String jsonMessage = gson.toJson(getAvailableLobbies);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method constructs sends the create new lobby message.
     * Afterwards it calls send message with the constructed message.
     *
     * @param lobbyName the name of the lobby to be created
     */
    public void sendCreateNewLobbyMessage(String lobbyName) {
        UUID userId = connection.getUserId();
        CreateNewLobbyMessage createNewLobby = new CreateNewLobbyMessage(userId, lobbyName);
        String jsonMessage = gson.toJson(createNewLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method constructs the join lobby message.
     * Afterwards it calls send message with the constructed message.
     *
     * @param lobbyId  the uuid of the lobby to join
     * @param userName the username
     */
    public void sendJoinLobbyMessage(UUID lobbyId, String userName) {
        UUID userId = connection.getUserId();
        JoinLobbyMessage joinLobby = new JoinLobbyMessage(userId, lobbyId, userName);
        String jsonMessage = gson.toJson(joinLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method constructs the leave lobby message.
     * Afterwards it calls send message with the constructed message.
     *
     * @param lobbyId the uuid of the lobby to leave
     */
    public void sendLeaveLobbyMessage(UUID lobbyId) {
        UUID userId = connection.getUserId();
        LeaveLobbyMessage leaveLobby = new LeaveLobbyMessage(userId, lobbyId);
        String jsonMessage = gson.toJson(leaveLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method constructs the start game message.
     * Afterwards it calls send message with the constructed message.
     *
     * @param lobbyId the uuid of the lobby, which is ready to start
     */
    public void sendStartGameMessage(UUID lobbyId) {
        UUID userId = connection.getUserId();
        StartGameMessage startGame = new StartGameMessage(userId, lobbyId);
        String jsonMessage = gson.toJson(startGame);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method constructs the game move message.
     * It does not validate the game move. This should be done beforehand with the game handler method.
     * Afterwards it calls send message with the constructed message.
     *
     * @param gameId   the uuid of the game
     * @param moveFrom the tile that gets moved
     * @param moveTo   the tile goal position
     */
    public void sendGameMoveMessage(UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
        UUID userId = connection.getUserId();
        GameMoveMessage gameMove = new GameMoveMessage(userId, gameId, moveFrom, moveTo);
        String jsonMessage = gson.toJson(gameMove);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This methods constructs the leave game message.
     * Afterwards it calls send message with the constructed message.
     *
     * @param gameId the uuid of the game to leave
     */
    public void sendLeaveGameMessage(UUID gameId) {
        UUID userId = connection.getUserId();
        LeaveGameMessage leaveGame = new LeaveGameMessage(userId, gameId);
        String jsonMessage = gson.toJson(leaveGame);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    /**
     * This method checks if the user uuid is null. If it is null, it logs a warning and the
     * message.
     *
     * @param userId      the user uuid
     * @param jsonMessage a constructed json message
     */
    private void checkUserId(UUID userId, String jsonMessage) {
        if (userId == null) {
            logger.warn("UserId is null! Message to be sent: " + jsonMessage);
        }
    }

    /**
     * This method gets called by all the public send message methods. It retrieves the websocket client
     * of the current connection and sends the given message, if the client is not null.
     *
     * @param jsonMessage the message to be sent
     */
    private void sendMessage(String jsonMessage) {
        WebSocketClient client = connection.getClient();
        if (client == null) {
            logger.warn("Client is null! Message to be sent: " + jsonMessage);
            return;
        }
        logger.info("Sent message: " + jsonMessage);
        client.send(jsonMessage);
    }
}
