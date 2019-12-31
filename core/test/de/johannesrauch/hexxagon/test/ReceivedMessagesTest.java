package de.johannesrauch.hexxagon.test;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.client.MessageReceiver;
import de.johannesrauch.hexxagon.network.message.LobbyJoinedMessage;
import de.johannesrauch.hexxagon.network.message.LobbyStatusMessage;
import de.johannesrauch.hexxagon.network.message.WelcomeMessage;
import de.johannesrauch.hexxagon.resource.Resources;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * This class will test the reaction to received messages by the finite-state machine.
 * Only expected messages will be tested since no action will be taken when a unexpected message is received.
 * (The finite-state machine ignores unexpected messages.)
 */
public class ReceivedMessagesTest {

    /**
     * This class provides a hexxagon dummy with overridden methods.
     */
    class TestHexxagon extends Hexxagon {

        // Dummy methods
        public TestHexxagon() {
        }

        @Override
        public void create() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public Resources getResources() {
            return null;
        }

        @Override
        public void showMainMenuScreen() {
        }

        @Override
        public void showSelectLobbyScreen() {
        }

        @Override
        public void showLobbyScreen() {
        }

        @Override
        public void showGameScreen() {
        }
    }

    /**
     * This class sets up objects to test received message behaviour.
     */
    class TestKit {
        TestHexxagon hexxagon;
        StateContext context;
        ConnectionHandler connectionHandler;
        LobbyHandler lobbyHandler;
        GameHandler gameHandler;
        Gson gson;

        public TestKit(State initialState) {
            hexxagon = new TestHexxagon();
            context = new StateContext(hexxagon);
            connectionHandler = new ConnectionHandler();
            lobbyHandler = new LobbyHandler();
            gameHandler = new GameHandler();
            gson = new Gson();

            context.setState(initialState);
            context.setConcurrent(false);
            context.setConnectionHandler(connectionHandler);
            context.setLobbyHandler(lobbyHandler);
            context.setGameHandler(gameHandler);
            connectionHandler.setContext(context);
        }
    }

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This method tests the reaction on the welcome message.
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
     * This method tests the reaction on the lobby joined message.
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

    @Test
    public void testReceivedLobbyStatusMessage() {
        // The fsm is in in lobby as player one state and receives a lobby status message indicating a second player has joined.
        // It should transition to in full lobby as player one state.
        TestKit kit = new TestKit(StateContext.getJoiningLobbyState());
        MessageReceiver receiver = kit.connectionHandler.getMessageReceiver();
        UUID lobbyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        kit.lobbyHandler.joinedLobby(userId, lobbyId);
        Lobby lobby = new Lobby(lobbyId,
                "example lobby name",
                userId,
                UUID.randomUUID(),
                "example player one username",
                "example player two username",
                new Date(),
                false);
        LobbyStatusMessage message = new LobbyStatusMessage(userId, lobbyId, lobby);
    }
}
