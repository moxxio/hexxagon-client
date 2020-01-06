package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class InGameMyTurnState implements State {

    /**
     * When in game my turn state and the user clicked leave, send a leave game message and transition.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        Hexxagon parent = context.getParent();

        context.getLobbyHandler().leaveLobby();
        context.getGameHandler().leaveGame();
        context.getMessageEmitter().sendGetAvailableLobbiesMessage();
        parent.showSelectLobbyScreen(false);

        return StateContext.getSelectLobbyState();
    }

    /**
     * When in game my turn state and a received game status message is received, then update the game handler,
     * check if game is over and transition to the particular state.
     *
     * @param context the context in which this state is used
     * @param message the received game status message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        GameHandler gameHandler = context.getGameHandler();

        gameHandler.updateGame(message);

        if (gameHandler.isGameOver()) {
            if (gameHandler.isTie()) return StateContext.getTieState();
            if (gameHandler.isWinnerMe()) return StateContext.getWinnerState();
            return StateContext.getLoserState();
        }
        if (gameHandler.isMyTurn()) return StateContext.getInGameMyTurnState();
        return StateContext.getInGameOpponentsTurnState();
    }
}
