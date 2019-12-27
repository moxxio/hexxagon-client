package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;

import java.util.UUID;

public class ConnectionAttemptState implements State {

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
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
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
    public State reactToClickedStart(StateContext context) {
        return null;
    }

    /**
     * When in connection attempt state and a connection error occurs, then transition to the disconnected state.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedConnectionError(StateContext context) {
        Hexxagon parent = context.getParent();
        MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

        mainMenuScreen.setConnStatusLabel("DISCONNECTED");
        mainMenuScreen.setConnectButtonTouchable(true);
        mainMenuScreen.setPlayButtonVisible(false);
        mainMenuScreen.setDisconnectButtonTouchable(false);

        return StateContext.getDisconnectedState();
    }

    /**
     * When you receive a welcome message in the connection attempt state, then the client is connected and
     * authenticated by the server.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message) {
        Hexxagon parent = context.getParent();
        MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

        parent.getConnectionHandler().setUserId(message.getUserId());
        mainMenuScreen.setConnStatusLabel("CONNECTED");
        mainMenuScreen.setDisconnectButtonTouchable(true);
        mainMenuScreen.setPlayButtonVisible(true);

        return StateContext.getConnectedState();
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @return the next state
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
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
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
    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message) {
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
    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
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
    public State reactToReceivedServerDisconnect(StateContext context) {
        return null;
    }
}
