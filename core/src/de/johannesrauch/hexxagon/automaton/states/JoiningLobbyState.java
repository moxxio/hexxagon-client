package de.johannesrauch.hexxagon.automaton.states;

import de.johannesrauch.hexxagon.automaton.events.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.events.LobbyJoinedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the joining lobby state of the finite-state machine of the client.
 * For an overview of the states of the finite-state machine, see docs/fsm/fsm.pdf.
 *
 * @author Johannes Rauch
 */
public class JoiningLobbyState implements State {
    private final Logger logger = LoggerFactory.getLogger(JoiningLobbyState.class);

    /**
     * This method handles all possible state transition from the search lobby state.
     *
     * @param event  the event that possibly triggers a state transition
     * @param context the state context in which this state object is used in
     */
    @Override
    public void reactOnEvent(AbstractEvent event, StateContext context) {
        if (event instanceof LobbyJoinedEvent) {
            State nextState = event.reactOnEvent(context);
            if (nextState != null) context.setState(nextState);
        } else {
            logger.warn("Unhandled event " + event.getClass().toString() + " in state " + this.getClass().toString());
        }
    }
}
