package de.johannesrauch.hexxagon.fsm.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.context.StateEnum;

/**
 * This class represents a pressed search game event from the search game button in the main menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class SearchGameEvent implements AbstractEvent {

    /**
     * This method gets called by the state context.
     * It executes the reaction on the pressed search game event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.Connected) {
            Hexxagon parent = context.getParent();

            parent.getMessageEmitter().sendGetAvailableLobbiesMessage();
            parent.showSelectLobbyScreen();

            return StateEnum.SearchLobby;
        }

        return currentState;
    }
}
