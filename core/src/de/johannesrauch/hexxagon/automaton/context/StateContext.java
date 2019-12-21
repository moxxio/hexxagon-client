package de.johannesrauch.hexxagon.automaton.context;

import de.johannesrauch.hexxagon.automaton.actions.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.states.State;

public class StateContext {
    private State state;

    public StateContext(State initialState) {
        state = initialState;
    }

    public synchronized void handleAction(AbstractEvent event) {
        state.handleAction(event, this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
