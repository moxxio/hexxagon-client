package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;

import java.util.UUID;

/**
 * This class represents a pressed join lobby event from the join button in the lobby select menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
 *
 * @author Johannes Rauch
 */
public class JoinLobbyEvent implements AbstractEvent {

    private UUID lobbyId;
    private String userName;

    public JoinLobbyEvent(UUID lobbyId, String userName) {
        this.lobbyId = lobbyId;
        this.userName = userName;
    }

    /**
     * This method gets called by the state context.
     * It executes the reaction on the pressed join lobby event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.SearchLobby) {
            Hexxagon parent = context.getParent();

            parent.getMessageEmitter().sendJoinLobbyMessage(lobbyId, userName);
            parent.getLobbySelectScreen().showProgressBar();

            return StateEnum.JoiningLobby;
        }

        return currentState;
    }
}
