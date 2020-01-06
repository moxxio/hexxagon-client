package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.controller.handler.AwaitingWelcome;
import de.johannesrauch.hexxagon.test.tools.StateDummy;
import de.johannesrauch.hexxagon.test.tools.TestKit;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests the awaiting welcome class.
 */
public class AwaitingWelcomeTest {

    /**
     * This method does the testing. It tests the actions
     */
    @Test
    public void testAwaitingWelcome() {
        // The client is waiting for a welcome message but none is received.
        StateDummy state = new StateDummy();
        TestKit kit = new TestKit(state);
        AwaitingWelcome awaitingWelcome = new AwaitingWelcome(kit.context, -1);
        awaitingWelcome.run();
        Assert.assertTrue(state.receivedConnectionError);

        // The client is waiting for a welcome message and one is received.
        state.receivedConnectionError = false;
        awaitingWelcome.setWelcomed(true);
        awaitingWelcome.run();
        Assert.assertFalse(state.receivedConnectionError);

        // The client is waiting for a welcome message but the client cancels.
        state.receivedConnectionError = false;
        awaitingWelcome.setWelcomed(false);
        awaitingWelcome.setCanceled(true);
        awaitingWelcome.run();
        Assert.assertFalse(state.receivedConnectionError);

        // The client is received a welcome message but the client cancels.
        state.receivedConnectionError = false;
        awaitingWelcome.setWelcomed(true);
        awaitingWelcome.run();
        Assert.assertFalse(state.receivedConnectionError);
    }
}
