package de.johannesrauch.hexxagon.state.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.network.message.GameStatusMessage;
import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.state.context.StateEnum;

/**
 * This class represents a received game status event from the server in the game screen.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class GameStatusEvent implements AbstractEvent {

    private GameStatusMessage message;

    public GameStatusEvent(GameStatusMessage message) {
        this.message = message;
    }

    /**
     * This method gets called by the current state from the state context.
     * It executes the reaction on the received game status event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();
        Hexxagon parent = context.getParent();
        GameHandler gameHandler = parent.getGameHandler();

        boolean gameStatusArrivedBeforeGameStarted = false;
        if (currentState == StateEnum.InFullLobbyAsPlayerOne || currentState == StateEnum.InLobbyAsPlayerTwo) {
            gameHandler.startedGame(message.getUserId(), message.getGameId());
            parent.showGameScreen();
            gameStatusArrivedBeforeGameStarted = true;
        }

        if (currentState == StateEnum.UninitializedGame || gameStatusArrivedBeforeGameStarted) {
            gameHandler.updateGame(message);

            if (gameHandler.isMyTurn()) return StateEnum.InGameMyTurn;
            return StateEnum.InGameOpponentsTurn;
        }

        if (currentState == StateEnum.InGameMyTurn || currentState == StateEnum.InGameOpponentsTurn) {
            gameHandler.updateGame(message);

            if (gameHandler.isGameOver()) {
                if (gameHandler.isTie()) return StateEnum.Tie;
                if (gameHandler.isWinnerMe()) return StateEnum.Winner;
                return StateEnum.Loser;
            }
            if (gameHandler.isMyTurn()) return StateEnum.InGameMyTurn;
            return StateEnum.InGameOpponentsTurn;
        }

        return currentState;
    }
}
