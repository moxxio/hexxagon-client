package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.network.messages.GameStatus;

/**
 * This class represents a received game status event from the server in the game screen.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class GameStatusEvent implements AbstractEvent {

    private GameStatus message;

    public GameStatusEvent(GameStatus message) {
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
            gameHandler.gameStarted(message.getUserId(), message.getGameId(), message.getCreationDate());
            parent.showGameScreen();
            gameStatusArrivedBeforeGameStarted = true;
        }

        if (currentState == StateEnum.UninitializedGame || gameStatusArrivedBeforeGameStarted) {
            gameHandler.gameStatusInit(message.getPlayerOne(),
                    message.getPlayerTwo(),
                    message.getPlayerOneUserName(),
                    message.getPlayerTwoUserName(),
                    message.getPlayerOnePoints(),
                    message.getPlayerTwoPoints(),
                    message.getBoard(),
                    message.getActivePlayer());

            if (gameHandler.isMyTurn()) return StateEnum.InGameMyTurn;
            else return StateEnum.InGameOpponentsTurn;
        }

        if (currentState == StateEnum.InGameMyTurn || currentState == StateEnum.InGameOpponentsTurn) {
            gameHandler.gameStatusUpdate(message.getLastMoveFrom(),
                    message.getLastMoveTo(),
                    message.getPlayerOnePoints(),
                    message.getPlayerTwoPoints(),
                    message.getBoard(),
                    message.getActivePlayer());

            // TODO: if game is over, go to winner's or loser's state
            if (gameHandler.isMyTurn()) return StateEnum.InGameMyTurn;
            else return StateEnum.InGameOpponentsTurn;
        }

        return currentState;
    }
}
