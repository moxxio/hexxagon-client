package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;

/**
 * Every class implementing the abstract event interface is an transition event class for the finite-state machine.
 * These events have an effect on the states and may trigger a state transition.
 * For an overview of the transition events of the finite-state machine, see docs/fsm/fsm.pdf.
 *
 * @author Johannes Rauch
 */
public interface AbstractEvent {
    public abstract State reactOnEvent(StateContext context);
}
