package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.model.board.Board;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.client.MessageReceiver;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;
import de.johannesrauch.hexxagon.test.tools.ClientDummy;
import de.johannesrauch.hexxagon.test.tools.TestKit;
import de.johannesrauch.hexxagon.test.tools.TilesExample;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This class will test the reaction to received messages.
 * Since the finite-state machine ignores unexpected messages in its particular states,
 * only the messages expected in the particular states will be tested.
 */
public class ReceivedMessagesTest {

    @Before
    public void setUp() {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This method tests the reaction to the welcome message.
     * The only state this message has an effect on is the connection attempt state.
     */
    @Test
    public void testReceivedWelcomeMessage() {
        // The fsm is in connection attempt state and receives a welcome message.
        // It should transition to the connected state.
        TestKit kit = new TestKit(StateContext.getConnectionAttemptState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        WelcomeMessage message = new WelcomeMessage(UUID.randomUUID(), "example welcome message");
        receiver.handleMessage(kit.gson.toJson(message, WelcomeMessage.class));
        Assert.assertEquals(StateContext.getConnectedState(), kit.context.getState());
    }

    /**
     * This method tests whether all sent lobbies are available in the lobby handler.
     */
    @Test
    public void testReceivedAvailableLobbies() {
        TestKit kit = new TestKit(StateContext.getSelectLobbyState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        receiver.setLobbyHandler(kit.lobbyHandler);
        List<Lobby> lobbies = new ArrayList<>();
        UUID userId = UUID.randomUUID();
        for (int i = 0; i < 10; i++) {
            lobbies.add(new Lobby(UUID.randomUUID(), "example lobby name " + i,
                    userId, UUID.randomUUID(),
                    "example player one username " + i, "example player two username " + i,
                    new Date(), false));
        }
        AvailableLobbiesMessage message = new AvailableLobbiesMessage(userId, lobbies);
        receiver.handleMessage(kit.gson.toJson(message, AvailableLobbiesMessage.class));
        Assert.assertEquals(lobbies, kit.lobbyHandler.getAvailableLobbies());
    }

    /**
     * This method tests whether the client automatically sends a get available lobbies message if it receives a created lobby message.
     * The user should not have to refresh manually every time.
     *
     * @throws URISyntaxException ignore
     */
    @Test
    public void testReceivedLobbyCreated() throws URISyntaxException {
        TestKit kit = new TestKit(StateContext.getSelectLobbyState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        UUID userId = UUID.randomUUID();
        kit.connectionHandler.setUserId(userId);
        kit.connectionHandler.setClient(new ClientDummy(null) {
            @Override
            public void send(String text) {
                BaseMessage message = gson.fromJson(text, BaseMessage.class);
                Assert.assertEquals(MessageType.GetAvailableLobbies, message.getMessageType());
                sent = true;
            }
        });
        LobbyCreatedMessage message = new LobbyCreatedMessage(userId, UUID.randomUUID(), true);
        receiver.handleMessage(kit.gson.toJson(message, LobbyCreatedMessage.class));
        Assert.assertTrue(((ClientDummy) kit.connectionHandler.getClient()).hasSent());
    }

    /**
     * This method tests the reaction to the lobby joined message.
     * It tests the reaction in the joining lobby state and the in lobby as player x state.
     */
    @Test
    public void testReceivedLobbyJoinedMessage() {
        // The fsm is in joining lobby state and receives a lobby joined message indicating joining a lobby failed.
        // It should transition to the select lobby state.
        TestKit kit = new TestKit(StateContext.getJoiningLobbyState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        LobbyJoinedMessage message = new LobbyJoinedMessage(UUID.randomUUID(), UUID.randomUUID(), false);
        receiver.handleMessage(kit.gson.toJson(message, LobbyJoinedMessage.class));
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        // The fsm is in joining lobby state and receives a lobby joined message indicating joining a lobby succeeded.
        // It should stay in the joining lobby state and wait for a lobby status message.
        kit = new TestKit(StateContext.getJoiningLobbyState());
        receiver = kit.connectionHandler.getMessageReceiver();
        message = new LobbyJoinedMessage(UUID.randomUUID(), UUID.randomUUID(), true);
        receiver.handleMessage(kit.gson.toJson(message, LobbyJoinedMessage.class));
        Assert.assertEquals(StateContext.getJoiningLobbyState(), kit.context.getState());

        // The lobby joined message arrives after the lobby status message: The fsm is in lobby as player x state.
        // It should stay in the in lobby as player x state.
        // (This message should indicate a successful join, otherwise it would not make sense.)
        kit = new TestKit(StateContext.getInLobbyAsPlayerOneState());
        receiver = kit.connectionHandler.getMessageReceiver();
        receiver.handleMessage(kit.gson.toJson(message, LobbyJoinedMessage.class));
        Assert.assertEquals(StateContext.getInLobbyAsPlayerOneState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to the lobby status message.
     * It tests the reaction in the joining lobby, in lobby as player x and in the full in lobby as player one state.
     */
    @Test
    public void testReceivedLobbyStatusMessage() {
        // The fsm is in joining lobby state and receives a lobby status message indicating the user joined a lobby as player two.
        // It should transition to in lobby as player two state.
        TestKit kit = new TestKit(StateContext.getJoiningLobbyState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        Lobby lobby1 = new Lobby(lobbyId, "example lobby name",
                UUID.randomUUID(), userId,
                "example player one username", "example player two username",
                new Date(), false);
        LobbyStatusMessage message1 = new LobbyStatusMessage(userId, lobbyId, lobby1);
        receiver.handleMessage(kit.gson.toJson(message1, LobbyStatusMessage.class));
        Assert.assertEquals(StateContext.getInLobbyAsPlayerTwoState(), kit.context.getState());

        // The fsm is in lobby as player two state and receives a lobby status message indicating the first player has left.
        // It should transition to in lobby as player one state.
        Lobby lobby2 = new Lobby(lobbyId, "example lobby name",
                userId, null,
                "example player one username", null,
                new Date(), false);
        LobbyStatusMessage message2 = new LobbyStatusMessage(userId, lobbyId, lobby2);
        receiver.handleMessage(kit.gson.toJson(message2, LobbyStatusMessage.class));
        Assert.assertEquals(StateContext.getInLobbyAsPlayerOneState(), kit.context.getState());

        // The fsm is in lobby as player one state and a lobby status message indicating a second player has joined is received.
        // It should transition to in full lobby as player one state.
        Lobby lobby3 = new Lobby(lobbyId, "example lobby name",
                userId, UUID.randomUUID(),
                "example player one username", "example player two username",
                new Date(), false);
        LobbyStatusMessage message3 = new LobbyStatusMessage(userId, lobbyId, lobby3);
        receiver.handleMessage(kit.gson.toJson(message3, LobbyStatusMessage.class));
        Assert.assertEquals(StateContext.getInFullLobbyAsPlayerOneState(), kit.context.getState());

        // The fsm is in full lobby as player one state and receives a lobby status message indicating the second player has left.
        // It should transition back to in lobby as player one state.
        receiver.handleMessage(kit.gson.toJson(message2, LobbyStatusMessage.class));
        Assert.assertEquals(StateContext.getInLobbyAsPlayerOneState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to the game started message.
     * It tests the reaction in lobby as player x and in the full in lobby as player one state.
     */
    @Test
    public void testReceivedGameStartedMessage() {
        // The fsm is in in lobby as player one state and receives a game started message.
        // This should not occur. Thus, the messages gets ignored. The fsm should stay in its state.
        TestKit kit = new TestKit(StateContext.getInLobbyAsPlayerOneState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        Lobby lobby = new Lobby(lobbyId, "example lobby name",
                userId, null,
                "example player one username", null,
                new Date(), false);
        kit.lobbyHandler.updateLobby(lobby);
        UUID gameId = UUID.randomUUID();
        GameStartedMessage message = new GameStartedMessage(userId, gameId, new Date());
        receiver.handleMessage(kit.gson.toJson(message, GameStartedMessage.class));
        Assert.assertEquals(StateContext.getInLobbyAsPlayerOneState(), kit.context.getState());

        // The fsm is in full lobby as player one state and receives a game started message.
        // It should transition to uninitialized game state.
        kit = new TestKit(StateContext.getInFullLobbyAsPlayerOneState());
        receiver = kit.connectionHandler.getMessageReceiver();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        lobby = new Lobby(lobbyId, "example lobby name",
                userId, UUID.randomUUID(),
                "example player one username", "example player two username",
                new Date(), false);
        kit.lobbyHandler.updateLobby(lobby);
        receiver.handleMessage(kit.gson.toJson(message, GameStartedMessage.class));
        Assert.assertEquals(StateContext.getUninitializedGameState(), kit.context.getState());

        // The fsm is in lobby as player two state and receives a game started message.
        // It should transition to uninitialized game state.
        kit = new TestKit(StateContext.getInLobbyAsPlayerTwoState());
        receiver = kit.connectionHandler.getMessageReceiver();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        lobby = new Lobby(lobbyId, "example lobby name",
                UUID.randomUUID(), userId,
                "example player one username", "example player two username",
                new Date(), false);
        kit.lobbyHandler.updateLobby(lobby);
        receiver.handleMessage(kit.gson.toJson(message, GameStartedMessage.class));
        Assert.assertEquals(StateContext.getUninitializedGameState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to the lobby status message.
     * It tests the reaction in uninitialized game, in game my turn and in game opponent's turn state.
     * This test only tests as win conditions only the winner and tie field of the message.
     * Further win conditions will be tested in another class.
     */
    @Test
    public void testReceivedGameStatusMessage() {
        // The fsm is in uninitialized game state and receives a game status message.
        // The game is now initialized and the fsm should transition to a in game my turn state.
        TestKit kit = new TestKit(StateContext.getUninitializedGameState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        UUID userId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        Lobby lobby = new Lobby(lobbyId, "example lobby name",
                userId, UUID.randomUUID(),
                "example player one username", "example player two username",
                new Date(), false);
        kit.lobbyHandler.updateLobby(lobby);
        UUID gameId = UUID.randomUUID();
        kit.gameHandler.startedGame(userId, gameId);
        Board board = kit.gson.fromJson(TilesExample.initTiles, Board.class);
        GameStatusMessage message1 = new GameStatusMessage(userId, gameId,
                userId, lobby.getPlayerTwo(),
                6, 6, board,
                userId, false, null);
        receiver.handleMessage(kit.gson.toJson(message1, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getInGameMyTurnState(), kit.context.getState());

        // The fsm is in in game my turn state and receives a game status message indicating a winner.
        // It should transition to the winner state.
        GameStatusMessage message2 = new GameStatusMessage(userId, gameId,
                userId, lobby.getPlayerTwo(),
                6, 6, board,
                lobby.getPlayerTwo(), false, userId);
        receiver.handleMessage(kit.gson.toJson(message2, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getWinnerState(), kit.context.getState());

        // Same thing as player two. The fsm should transition to in game opponents turn state.
        kit = new TestKit(StateContext.getUninitializedGameState());
        receiver = kit.connectionHandler.getMessageReceiver();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        lobby = new Lobby(lobbyId, "example lobby name",
                UUID.randomUUID(), userId,
                "example player one username", "example player two username",
                new Date(), false);
        kit.lobbyHandler.updateLobby(lobby);
        gameId = UUID.randomUUID();
        kit.gameHandler.startedGame(userId, gameId);
        board = kit.gson.fromJson(TilesExample.initTiles, Board.class);
        GameStatusMessage message3 = new GameStatusMessage(userId, gameId,
                lobby.getPlayerOne(), userId,
                6, 6, board,
                lobby.getPlayerOne(), false, null);
        receiver.handleMessage(kit.gson.toJson(message3, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getInGameOpponentsTurnState(), kit.context.getState());

        // The fsm is in in game opponents turn state and receives a game status message which indicates that it is the user's turn.
        // It should transition to in game my turn state.
        GameStatusMessage message4 = new GameStatusMessage(userId, gameId,
                lobby.getPlayerOne(), userId,
                6, 6, board,
                userId, false, null);
        receiver.handleMessage(kit.gson.toJson(message4, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getInGameMyTurnState(), kit.context.getState());

        // The fsm is in in game my turn state and receives a game status message which indicates that it is the opponent's turn.
        // It should transition back to in game opponents turn state.
        receiver.handleMessage(kit.gson.toJson(message3, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getInGameOpponentsTurnState(), kit.context.getState());

        // The fsm is in in game opponents turn state and receives a message indicating the opponent is the winner.
        // It should transition to the loser state.
        GameStatusMessage message5 = new GameStatusMessage(userId, gameId,
                lobby.getPlayerOne(), userId,
                6, 6, board,
                userId, false, lobby.getPlayerOne());
        receiver.handleMessage(kit.gson.toJson(message5, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getLoserState(), kit.context.getState());

        // The fsm is in in my turn state and receives a game status message with a set tie flag.
        // It should transition to the tie state.
        kit = new TestKit(StateContext.getInGameMyTurnState());
        receiver = kit.connectionHandler.getMessageReceiver();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        kit.lobbyHandler.updateLobby(lobby); // user is player two
        kit.gameHandler.startedGame(userId, gameId);
        GameStatusMessage message6 = new GameStatusMessage(userId, gameId,
                lobby.getPlayerOne(), userId,
                6, 6, board,
                lobby.getPlayerOne(), true, null);
        receiver.handleMessage(kit.gson.toJson(message6, GameStatusMessage.class));
        Assert.assertEquals(StateContext.getTieState(), kit.context.getState());
    }
}
