package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.test.tools.ClientDummy;
import org.apache.log4j.BasicConfigurator;
import org.junit.*;

import java.net.URISyntaxException;
import java.util.UUID;

/**
 * This class tests the validity of the messages sent by the message emitter.
 */
public class SentMessagesTest {

    @Before
    public void setUp() {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This method tests whether the get available lobbies message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testGetAvailableLobbiesMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new GetAvailableLobbiesMessage(userId)) {
            @Override
            public void send(String text) {
                GetAvailableLobbiesMessage message = gson.fromJson(text, GetAvailableLobbiesMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendGetAvailableLobbiesMessage();
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the create new lobby message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testCreateNewLobbyMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        String lobbyName = "example lobby name";
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new CreateNewLobbyMessage(userId, lobbyName)) {
            @Override
            public void send(String text) {
                CreateNewLobbyMessage message = gson.fromJson(text, CreateNewLobbyMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendCreateNewLobbyMessage(lobbyName);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the join lobby message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testJoinLobbyMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        String userName = "example user name";
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new JoinLobbyMessage(userId, lobbyId, userName)) {
            @Override
            public void send(String text) {
                JoinLobbyMessage message = gson.fromJson(text, JoinLobbyMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendJoinLobbyMessage(lobbyId, userName);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the leave lobby message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testLeaveLobbyMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new LeaveLobbyMessage(userId, lobbyId)) {
            @Override
            public void send(String text) {
                LeaveLobbyMessage message = gson.fromJson(text, LeaveLobbyMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendLeaveLobbyMessage(lobbyId);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the start game message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testStartGameMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new StartGameMessage(userId, lobbyId)) {
            @Override
            public void send(String text) {
                StartGameMessage message = gson.fromJson(text, StartGameMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendStartGameMessage(lobbyId);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the game move message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testGameMoveMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new GameMoveMessage(userId, gameId, TileEnum.TILE_1, TileEnum.TILE_2)) {
            @Override
            public void send(String text) {
                GameMoveMessage message = gson.fromJson(text, GameMoveMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendGameMoveMessage(gameId, TileEnum.TILE_1, TileEnum.TILE_2);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests whether the leave game message is well formed.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testLeaveGameMessage() throws URISyntaxException {
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setUserId(userId);
        connectionHandler.setClient(new ClientDummy(new LeaveGameMessage(userId, gameId)) {
            @Override
            public void send(String text) {
                LeaveGameMessage message = gson.fromJson(text, LeaveGameMessage.class);
                Assert.assertEquals(this.message, message);
                sent = true;
            }
        });
        connectionHandler.getMessageEmitter().sendLeaveGameMessage(gameId);
        Assert.assertTrue(((ClientDummy) connectionHandler.getClient()).hasSent());
    }
}
