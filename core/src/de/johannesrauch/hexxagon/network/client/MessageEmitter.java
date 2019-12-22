package de.johannesrauch.hexxagon.network.client;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.controller.ConnectionHandler;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MessageEmitter {

    private final ConnectionHandler connection;

    private final Logger logger;

    private final Gson gson;

    public MessageEmitter(ConnectionHandler connection) {
        this.connection = connection;
        logger = LoggerFactory.getLogger(MessageEmitter.class);
        gson = new Gson();
    }

    private void sendMessage(String jsonMessage) {
        SimpleClient client = connection.getClient();
        if (client == null) {
            logger.warn("Client is null! (Message: " + jsonMessage + ")");
            return;
        }
        logger.info("Sent message: " + jsonMessage);
        client.send(jsonMessage);
    }

    public void sendGetAvailableLobbiesMessage() {
        UUID userId = connection.getUserId();
        GetAvailableLobbiesMessage getAvailableLobbies = new GetAvailableLobbiesMessage(userId);
        String jsonMessage = gson.toJson(getAvailableLobbies);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    public void sendCreateNewLobbyMessage(String lobbyName) {
        UUID userId = connection.getUserId();
        CreateNewLobbyMessage createNewLobby = new CreateNewLobbyMessage(userId, lobbyName);
        String jsonMessage = gson.toJson(createNewLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    public void sendJoinLobbyMessage(UUID lobbyId, String userName) {
        UUID userId = connection.getUserId();
        JoinLobbyMessage joinLobby = new JoinLobbyMessage(userId, lobbyId, userName);
        String jsonMessage = gson.toJson(joinLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    public void sendLeaveLobbyMessage(UUID lobbyId) {
        UUID userId = connection.getUserId();
        LeaveLobbyMessage leaveLobby = new LeaveLobbyMessage(userId, lobbyId);
        String jsonMessage = gson.toJson(leaveLobby);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    public void sendStartGameMessage(UUID lobbyId) {
        UUID userId = connection.getUserId();
        StartGameMessage startGame = new StartGameMessage(userId, lobbyId);
        String jsonMessage = gson.toJson(startGame);
        checkUserId(userId, jsonMessage);
        sendMessage(jsonMessage);
    }

    public void sendGameMoveMessage(UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
        // TODO: implement this
    }

    public void sendLeaveGameMessage(UUID gameId) {
        // TODO: implement this
    }

    private void checkUserId(UUID userId, String jsonMessage) {
        if (userId == null) {
            logger.warn("UserId is null! (Message" + jsonMessage + ")");
        }
    }
}
