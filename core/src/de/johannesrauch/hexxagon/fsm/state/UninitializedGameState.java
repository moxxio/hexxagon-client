package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class UninitializedGameState implements State {

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedConnect(StateContext context, String hostName, String port) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedDisconnect(StateContext context) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedPlay(StateContext context) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedBack(StateContext context) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName) {
        return null;
    }

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

        parent.getLobbyHandler().leaveLobby();
        parent.getGameHandler().leaveGame();
        parent.getMessageEmitter().sendGetAvailableLobbiesMessage();
        parent.showSelectLobbyScreen();

        return StateContext.getSelectLobbyState();
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedStart(StateContext context) {
        return null;
    }

    /**
     * TODO: implement uninitialized game state reaction to connection error
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedConnectionError(StateContext context) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        return null;
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
        GameHandler gameHandler = parent.getGameHandler();

        gameHandler.startedGame(message.getUserId(), message.getGameId());
        parent.showGameScreen();

        return null;
    }

    /**
     * When in uninitialized game state and a game started message is received, then the game is initialized.
     * After that, transition to in game my or opponents turn state.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        Hexxagon parent = context.getParent();
        GameHandler gameHandler = parent.getGameHandler();

        gameHandler.startedGame(message.getUserId(), message.getGameId());
        gameHandler.updateGame(message);
        parent.showGameScreen();

        if (gameHandler.isMyTurn()) return StateContext.getInGameMyTurnState();
        return StateContext.getInGameOpponentsTurnState();
    }

    /**
     * TODO: implement reaction of uninitialized game state reaction to server disconnect
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedServerDisconnect(StateContext context) {
        return null;
    }
}
