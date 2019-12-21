package de.johannesrauch.hexxagon.automaton.states;

import de.johannesrauch.hexxagon.automaton.actions.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.context.StateContext;

public interface State {
    public abstract void handleAction(AbstractEvent event, StateContext context);
}
