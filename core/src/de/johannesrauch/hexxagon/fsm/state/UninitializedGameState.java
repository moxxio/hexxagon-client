package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

public class UninitializedGameState implements State {

    /**
     * When in uninitialized game state and the user decides to leave, the lobby and game handler get reset
     * and the lobby select screen is shown.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        Hexxagon parent = context.getParent();

        context.getLobbyHandler().leaveLobby();
        context.getGameHandler().leaveGame();
        context.getMessageEmitter().sendGetAvailableLobbiesMessage();
        parent.showSelectLobbyScreen(false, -1);

        return StateContext.getSelectLobbyState();
    }

    /**
     * When in uninitialized game state and a game started message is received, then the game is initialized.
     * After that, transition to in game my or opponents turn state.
     *
     * @param context the context in which this state is used
     * @param message the received game started message
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message) {
        Hexxagon parent = context.getParent();
        GameHandler gameHandler = context.getGameHandler();

        gameHandler.startedGame(message.getUserId(), message.getGameId());
        parent.showGameScreen();

        return null;
    }

    /**
     * When in uninitialized game state and a game started message is received, then the game is initialized.
     * After that, transition to in game my or opponents turn state.
     *
     * @param context the context in which this state is used
     * @param message the received game status message
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        Hexxagon parent = context.getParent();
        GameHandler gameHandler = context.getGameHandler();

        gameHandler.startedGame(message.getUserId(), message.getGameId());
        gameHandler.updateGame(message);
        parent.showGameScreen();

        if (gameHandler.isMyTurn()) return StateContext.getInGameMyTurnState();
        return StateContext.getInGameOpponentsTurnState();
    }
}
