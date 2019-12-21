package de.johannesrauch.hexxagon.automaton.states;

import de.johannesrauch.hexxagon.automaton.events.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.events.LeaveEvent;
import de.johannesrauch.hexxagon.automaton.events.LobbyStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the in full lobby state with the client as player one of the finite-state machine of the client.
 * For an overview of the states of the finite-state machine, see docs/fsm/fsm.pdf.
 *
 * @author Johannes Rauch
 */
public class ReadyLobbyPlayerOneState implements State {

    private final Logger logger = LoggerFactory.getLogger(ReadyLobbyPlayerOneState.class);

    /**
     * This method handles all possible state transition from the in full lobby with the client as player one state.
     *
     * @param event  the event that possibly triggers a state transition
     * @param context the state context in which this state object is used in
     */
    @Override
    public void reactOnEvent(AbstractEvent event, StateContext context) {
        if (event instanceof LobbyStatusEvent) {
            State nextState = event.reactOnEvent(context);
            if (nextState != null) context.setState(nextState);
        } else if (event instanceof LeaveEvent) {
            State nextState = event.reactOnEvent(context);
            if (nextState != null) context.setState(nextState);
        } else {
            logger.warn("Unhandled event " + event.getClass().toString() + " in state " + this.getClass().toString());
        }
    }
}
