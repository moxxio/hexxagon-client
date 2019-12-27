package de.johannesrauch.hexxagon.fsm.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.network.message.GameStartedMessage;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.context.StateEnum;

/**
 * This class represents a received game start event in a in lobby state.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class GameStartedEvent implements AbstractEvent {

    private GameStartedMessage message;

    public GameStartedEvent(GameStartedMessage message) {
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
        Hexxagon parent = context.getParent();
        GameHandler gameHandler = parent.getGameHandler();

        if (currentState == StateEnum.InFullLobbyAsPlayerOne || currentState == StateEnum.InLobbyAsPlayerTwo) {
            gameHandler.startedGame(message.getUserId(), message.getGameId());
            parent.showGameScreen();

            return StateEnum.UninitializedGame;
        }

        return currentState;
    }
}
