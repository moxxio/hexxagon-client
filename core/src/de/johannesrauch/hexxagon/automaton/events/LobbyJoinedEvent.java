package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.network.messages.LobbyJoined;

/**
 * This class represents a received joined lobby event from the join button in the lobby select menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class LobbyJoinedEvent implements AbstractEvent {

    private LobbyJoined message;

    public LobbyJoinedEvent(LobbyJoined message) {
        this.message = message;
    }

    /**
     * This method gets called by the state context.
     * It executes the reaction on the received joined lobby event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.JoiningLobby) {
            Hexxagon parent = context.getParent();

            if (message.getSuccessfullyJoined()) {
                parent.getLobbyHandler().lobbyJoined(message.getUserId(), message.getLobbyId());
                return currentState;
            } else {
                parent.getLobbySelectScreen().hideProgressBar(0);
                return StateEnum.SearchLobby;
            }
        }

        return currentState;
    }
}
