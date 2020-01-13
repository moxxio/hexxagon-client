package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

public class InGameOpponentsTurnState implements State {

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

    /**
     * When in game my turn state and a received game status message is received, then update the game handler,
     * check if game is over and transition to the particular state.
     *
     * @param context the context in which this state is used
     * @param message the received game status message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        return StateContext.getInGameMyTurnState().reactToReceivedGameStatus(context, message);
    }
}
