package de.johannesrauch.hexxagon.state.event;

import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.state.context.StateEnum;

/**
 * Every class implementing the abstract event interface is an transition event class for the finite-state machine.
 * These events have an effect on the states and may trigger a state transition.
 * For an overview of the transition events of the finite-state machine, see docs/fsm/fsm.pdf.
 *
 * @author Johannes Rauch
 */
public interface AbstractEvent {
    public abstract StateEnum reactOnEvent(StateContext context);
}
