package de.johannesrauch.hexxagon.test;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.message.*;
import org.apache.log4j.BasicConfigurator;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * This class tests the validity of the messages sent by the message emitter.
 */
public class EmitterTest {

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This websocket client instantiation is used to test the send messages.
     * You should override the send method in order to check the validity of the messages.
     * This trick is used to "catch" the messages before they will be sent over the socket.
     */
    class TestClient extends WebSocketClient {

        protected final Gson gson = new Gson();

        protected AbstractMessage message;

        public TestClient(AbstractMessage message) throws URISyntaxException {
            super(new URI("ws://localhost:4444"));
            this.message = message;
        }

        // We do not need this methods. We just override send.
        @Override
        public void onOpen(ServerHandshake handshake) {

        }

        @Override
        public void onMessage(String message) {

        }

        @Override
        public void onClose(int code, String reason, boolean remote) {

        }

        @Override
        public void onError(Exception ex) {

        }
    }

    // TODO: implement tests

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
        connectionHandler.setClient(new TestClient(new GetAvailableLobbiesMessage(userId)) {
            @Override
            public void send(String text) {
                GetAvailableLobbiesMessage message = gson.fromJson(text, GetAvailableLobbiesMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendGetAvailableLobbiesMessage();
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
        connectionHandler.setClient(new TestClient(new CreateNewLobbyMessage(userId, lobbyName)) {
            @Override
            public void send(String text) {
                CreateNewLobbyMessage message = gson.fromJson(text, CreateNewLobbyMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendCreateNewLobbyMessage(lobbyName);
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
        connectionHandler.setClient(new TestClient(new JoinLobbyMessage(userId, lobbyId, userName)) {
            @Override
            public void send(String text) {
                JoinLobbyMessage message = gson.fromJson(text, JoinLobbyMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendJoinLobbyMessage(lobbyId, userName);
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
        connectionHandler.setClient(new TestClient(new LeaveLobbyMessage(userId, lobbyId)) {
            @Override
            public void send(String text) {
                LeaveLobbyMessage message = gson.fromJson(text, LeaveLobbyMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendLeaveLobbyMessage(lobbyId);
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
        connectionHandler.setClient(new TestClient(new StartGameMessage(userId, lobbyId)) {
            @Override
            public void send(String text) {
                StartGameMessage message = gson.fromJson(text, StartGameMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendStartGameMessage(lobbyId);
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
        connectionHandler.setClient(new TestClient(new GameMoveMessage(userId, gameId, TileEnum.TILE_1, TileEnum.TILE_2)) {
            @Override
            public void send(String text) {
                GameMoveMessage message = gson.fromJson(text, GameMoveMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendGameMoveMessage(gameId, TileEnum.TILE_1, TileEnum.TILE_2);
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
        connectionHandler.setClient(new TestClient(new LeaveGameMessage(userId, gameId)) {
            @Override
            public void send(String text) {
                LeaveGameMessage message = gson.fromJson(text, LeaveGameMessage.class);
                Assert.assertEquals(this.message, message);
            }
        });
        connectionHandler.getMessageEmitter().sendLeaveGameMessage(gameId);
    }
}
