package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class InLobbyAsPlayerTwoState implements State {

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state
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
     * @return the next state
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
     * @return the next state
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
     * @return the next state
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
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName) {
        return null;
    }

    /**
     * When in lobby as player two state and leave is clicked, leave the lobby, go back to select lobby screen and
     * transition to select lobby state.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        return StateContext.getInLobbyAsPlayerOneState().reactToClickedLeave(context);
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedStart(StateContext context) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state
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
            lobbyHandler.leaveLobby();
            parent.showSelectLobbyScreen();

            return StateContext.getSelectLobbyState();
        }
    }

    /**
     * When in lobby as player two state, then check the lobby status message and transition to the particular state.
     *
     * @param context the context in which this state is used
     * @param message the received lobby status message
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
     * When in lobby as player two state and a game started message is received, show game screen and transition to
     * uninitialized game state.
     *
     * @param context the context in which this state is used
     * @return the next state
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
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        return null;
    }
}
