package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class WinnerState implements State {

    /**
     * When in game my turn state and the user clicked leave, send a leave game message and transition.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        return StateContext.getInGameMyTurnState().reactToClickedLeave(context);
    }
}
