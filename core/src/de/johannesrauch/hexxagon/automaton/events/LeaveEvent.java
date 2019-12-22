package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;

/**
 * This class represents a pressed leave event from the leave button in the lobby menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class LeaveEvent implements AbstractEvent {

    /**
     * This method gets called by the state context.
     * It executes the reaction on the pressed leave event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();
        Hexxagon parent = context.getParent();

        if (currentState == StateEnum.InLobbyAsPlayerOne
                || currentState == StateEnum.InLobbyAsPlayerTwo
                || currentState == StateEnum.InFullLobbyAsPlayerOne) {
            parent.getLobbyHandler().sendLeaveLobbyMessage();
            parent.showLobbySelectScreen();

            return StateEnum.SearchLobby;
        }

        if (currentState == StateEnum.UninitializedGame
                || currentState == StateEnum.InGameMyTurn
                || currentState == StateEnum.InGameOpponentsTurn
                || currentState == StateEnum.Winner
                || currentState == StateEnum.Loser) {
            parent.showLobbySelectScreen();
            parent.getGameHandler().leaveGame();
            parent.getMessageEmitter().sendGetAvailableLobbiesMessage();

            return StateEnum.SearchLobby;
        }

        return currentState;
    }
}
