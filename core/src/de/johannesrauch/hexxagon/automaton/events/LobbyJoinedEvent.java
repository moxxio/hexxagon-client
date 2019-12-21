package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;
import de.johannesrauch.hexxagon.network.messages.LobbyJoined;

/**
 * This class represents a received joined lobby event from the join button in the lobby select menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
 *
 * @author Johannes Rauch
 */
public class LobbyJoinedEvent implements AbstractEvent {

    private LobbyJoined message;

    public LobbyJoinedEvent(LobbyJoined message) {
        this.message = message;
    }

    /**
     * This method gets called by the current state from the state context.
     * It executes the received joined lobby event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public State reactOnEvent(StateContext context) {
        Hexxagon parent = context.getParent();

        if (message.getSuccessfullyJoined()) {
            parent.getLobbyHandler().lobbyJoined(message.getUserId(), message.getLobbyId());
            return null;
        } else {
            parent.getLobbySelectScreen().hideProgressBar(0);
            parent.showLobbySelectScreen();
            return context.getSearchLobbyState();
        }
    }
}
