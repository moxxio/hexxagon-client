package de.johannesrauch.hexxagon.state;

public class StateContext {
    private State state;

    public StateContext(State initialState) {
        state = initialState;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
