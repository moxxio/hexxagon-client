package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;
import de.johannesrauch.hexxagon.test.Tools.HexxagonDummy;
import org.junit.Test;

/**
 * This class tests the awaiting welcome class.
 */
public class AwaitingWelcomeTest {

    /**
     * This class is used to check if the awaiting welcome class triggers a received connection error event.
     * It implements the state interface.
     */
    static class TestState implements State {
        public boolean receivedConnectionError = false;

        /**
         * If a connection error is received, set the received connection error flag to true.
         *
         * @param context the state context this method gets called in
         * @return null
         */
        @Override
        public State reactToReceivedConnectionError(StateContext context) {
            receivedConnectionError = true;
            return null;
        }
    }

    /**
     * This method tests the
     */
    @Test
    public void testAwaitingWelcome() {
        StateContext context = new StateContext(new HexxagonDummy());
        context.setState(new TestState());

    }
}
