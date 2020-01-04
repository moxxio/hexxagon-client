package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;

import java.util.UUID;

public class ConnectionAttemptState implements State {

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
     * @param context the context in which this state is used
     * @param lobbyId the lobby uuid
     * @param userName the username string
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
     * When you receive a welcome message in the connection attempt state, then the client is connected and
     * authenticated by the server.
     *
     * @param context the context in which this state is used
     * @param message the received welcome message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message) {
        Hexxagon parent = context.getParent();

        ConnectionHandler connectionHandler = context.getConnectionHandler();
        connectionHandler.setUserId(message.getUserId());
        parent.showMainMenuScreen();

        return StateContext.getConnectedState();
    }

    /**
     * This case should not occur, if it unexpectedly does, then do nothing.
     *
     * @param context the context in which this state is used
     * @param message the received lobby joined message
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
     * @param message the received lobby status message
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
