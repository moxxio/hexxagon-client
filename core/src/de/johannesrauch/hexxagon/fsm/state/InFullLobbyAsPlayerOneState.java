package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class InFullLobbyAsPlayerOneState implements State {

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
     * When in full lobby as player one state and the user clicked leave, then leave the lobby, show the select lobby
     * screen and transition to select lobby screen.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        return StateContext.getInGameMyTurnState().reactToClickedLeave(context);
    }

    /**
     * When in full lobby as player one state and the user clicks start, then send a start game message,
     * show game screen and transition to uninitialized game state.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedStart(StateContext context) {
        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = parent.getLobbyHandler();

        context.getExecutorService().submit(lobbyHandler::startGame);

        return StateContext.getUninitializedGameState();
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
     * When in full lobby as player one state and the lobby joined message is received after the lobby status message,
     * then update the lobby.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = parent.getLobbyHandler();

        if (message.getSuccessfullyJoined()) {
            lobbyHandler.joinedLobby(message.getUserId(), message.getLobbyId());

            return null;
        } else {
            context.getExecutorService().submit(lobbyHandler::leaveLobby);
            parent.showSelectLobbyScreen();

            return StateContext.getSelectLobbyState();
        }
    }

    /**
     * When in full lobby as player one state and a lobby status message occurs, then check the lobby and transition
     * to the particular state.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = parent.getLobbyHandler();

        lobbyHandler.updateLobby(message.getLobby());

        if (!lobbyHandler.isLobbyReady()) return StateContext.getInLobbyAsPlayerOneState();
        return null;
    }

    /**
     * When in full lobby as player one state and a game started message occurs, then transition to the uninitialized
     * game state.
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

        return StateContext.getUninitializedGameState();
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        return null;
    }
}
