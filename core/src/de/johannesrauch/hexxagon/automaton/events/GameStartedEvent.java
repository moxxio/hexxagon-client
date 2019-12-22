package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.network.messages.GameStarted;

/**
 * This class represents a received game start event in a in lobby state.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class GameStartedEvent implements AbstractEvent {

    private GameStarted message;

    public GameStartedEvent(GameStarted message) {
        this.message = message;
    }

    /**
     * This method gets called by the current state from the state context.
     * It executes the received lobby status event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.InFullLobbyAsPlayerOne || currentState == StateEnum.InLobbyAsPlayerTwo) {
            Hexxagon parent = context.getParent();

            parent.getGameHandler().gameStarted(message.getUserId(), message.getGameId(), message.getCreationDate());
            parent.showGameScreen();

            return StateEnum.UninitializedGame;
        }
        return null;
    }
}
