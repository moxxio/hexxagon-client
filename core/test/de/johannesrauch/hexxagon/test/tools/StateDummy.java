package de.johannesrauch.hexxagon.test.tools;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;

/**
 * This class is used to check if the awaiting welcome class triggers a received connection error event.
 * It implements the state interface.
 */
public class StateDummy implements State {

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
