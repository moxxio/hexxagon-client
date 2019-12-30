package de.johannesrauch.hexxagon.test;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.client.MessageReceiver;
import de.johannesrauch.hexxagon.network.message.WelcomeMessage;
import de.johannesrauch.hexxagon.resource.Resources;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * This class will test the reaction on received messages.
 * Only the implemented reactions will be tested and only messages with effect on the state.
 * (There are dummy methods in all states that will do nothing. This will not be tested.)
 */
public class ReceivedMessagesTest {

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    class HexxagonTest extends Hexxagon {

        // Dummy methods
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
     * This message tests the reaction on the welcome message.
     * The only state this message has an effect on is the connection attempt state.
     */
    @Test
    public void testReceivedWelcomeMessage() {
        HexxagonTest hexxagon = new HexxagonTest();
        StateContext context = new StateContext(hexxagon);
        ConnectionHandler connectionHandler = new ConnectionHandler();

        context.setState(StateContext.getConnectionAttemptState());
        context.setConcurrent(false);
        context.setConnectionHandler(connectionHandler);
        connectionHandler.setContext(context);

        MessageReceiver messageReceiver = connectionHandler.getMessageReceiver();
        WelcomeMessage message = new WelcomeMessage(UUID.randomUUID(), "example welcome message");
        Gson gson = new Gson();
        messageReceiver.handleMessage(gson.toJson(message, WelcomeMessage.class));

        Assert.assertEquals(StateContext.getConnectedState(), context.getState());
    }
}
