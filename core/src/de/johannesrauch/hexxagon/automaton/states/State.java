package de.johannesrauch.hexxagon.automaton.states;

import de.johannesrauch.hexxagon.automaton.events.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.context.StateContext;

public interface State {
    public abstract void reactOnEvent(AbstractEvent event, StateContext context);
}
