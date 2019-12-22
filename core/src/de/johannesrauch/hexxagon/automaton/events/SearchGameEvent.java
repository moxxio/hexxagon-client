package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;

/**
 * This class represents a pressed search game event from the search game button in the main menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
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
            parent.showLobbySelectScreen();

            return StateEnum.SearchLobby;
        }

        return currentState;
    }
}
