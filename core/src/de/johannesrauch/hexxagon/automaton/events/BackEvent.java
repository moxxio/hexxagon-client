package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;

/**
 * This class represents a pressed back event from the back button in the lobby select menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
 *
 * @author Johannes Rauch
 */
public class BackEvent implements AbstractEvent {

    /**
     * This method gets called by the current state from the state context.
     * It executes the pressed back event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public State reactOnEvent(StateContext context) {
        Hexxagon parent = context.getParent();

        parent.showMainMenuScreen();

        return context.getConnectedState();
    }
}
