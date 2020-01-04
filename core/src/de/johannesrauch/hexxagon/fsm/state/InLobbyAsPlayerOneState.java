package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class InLobbyAsPlayerOneState implements State {

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context  the context in which this state is used
     * @param hostName the hostname of the server
     * @param port     the port of the server
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
     * @param context  the context in which this state is used
     * @param lobbyId  the lobby uuid
     * @param userName the username string
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName) {
        return null;
    }

    /**
     * When in in lobby as player one state and the user clicks leave, then leave the lobby and transition to
     * select lobby state.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        Hexxagon parent = context.getParent();

        context.getLobbyHandler().leaveLobby();
        parent.showSelectLobbyScreen();

        return StateContext.getSelectLobbyState();
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
     * @param message the received welcome message
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
     * @param message the received lobby joined message
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        return StateContext.getJoiningLobbyState().reactToReceivedLobbyJoined(context, message);
    }

    /**
     * When in lobby as player one state and a lobby status message is received, check what has changed and transition
     * to the particular state.
     *
     * @param context the context in which this state is used
     * @param message the received lobby status message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        LobbyHandler lobbyHandler = context.getLobbyHandler();

        lobbyHandler.updateLobby(message.getLobby());

        if (lobbyHandler.isLobbyReady()) return StateContext.getInFullLobbyAsPlayerOneState();
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @param message the received game started message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message) {
        return null;
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @param message the received game status message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        return null;
    }
}
