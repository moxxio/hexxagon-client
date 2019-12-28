package de.johannesrauch.hexxagon.fsm.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.state.*;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This method provides a context for the states of the client's finite-state machine.
 */
public class StateContext {

    private final Hexxagon parent;

    private final ExecutorService executorService;

    private State state;

    private static final DisconnectedState disconnectedState = new DisconnectedState();
    private static final ConnectionAttemptState connectionAttemptState = new ConnectionAttemptState();
    private static final ConnectedState connectedState = new ConnectedState();
    private static final SelectLobbyState selectLobbyState = new SelectLobbyState();
    private static final JoiningLobbyState joiningLobbyState = new JoiningLobbyState();
    private static final InLobbyAsPlayerOneState inLobbyAsPlayerOneState = new InLobbyAsPlayerOneState();
    private static final InLobbyAsPlayerTwoState inLobbyAsPlayerTwoState = new InLobbyAsPlayerTwoState();
    private static final InFullLobbyAsPlayerOneState inFullLobbyAsPlayerOneState = new InFullLobbyAsPlayerOneState();
    private static final UninitializedGameState uninitializedGameState = new UninitializedGameState();
    private static final InGameMyTurnState inGameMyTurnState = new InGameMyTurnState();
    private static final InGameOpponentsTurnState inGameOpponentsTurnState = new InGameOpponentsTurnState();
    private static final WinnerState winnerState = new WinnerState();
    private static final TieState tieState = new TieState();
    private static final LoserState loserState = new LoserState();

    /**
     * TODO: remove parent and set the actual stuff this class needs
     * This is the standard constructor. It sets the parent, creates the executor service and sets the initial state.
     *
     * @param parent the parent
     */
    public StateContext(Hexxagon parent) {
        this.parent = parent;
        executorService = Executors.newFixedThreadPool(1);
        state = disconnectedState;
    }

    /**
     * This method returns the parent.
     *
     * @return the parent
     */
    public Hexxagon getParent() {
        return parent;
    }

    /**
     * This method returns the executor service.
     *
     * @return the executor service
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * This method returns the static disconnected state object.
     *
     * @return the static disconnected state object
     */
    public static DisconnectedState getDisconnectedState() {
        return disconnectedState;
    }

    /**
     * This method returns the static connection attempt state object.
     *
     * @return the static connection attempt state object
     */
    public static ConnectionAttemptState getConnectionAttemptState() {
        return connectionAttemptState;
    }

    /**
     * This method returns the static connected state object.
     *
     * @return the static connected state object
     */
    public static ConnectedState getConnectedState() {
        return connectedState;
    }

    public static SelectLobbyState getSelectLobbyState() {
        return selectLobbyState;
    }

    /**
     * This method returns the static joining lobby state object.
     *
     * @return the static joining lobby state object
     */
    public static JoiningLobbyState getJoiningLobbyState() {
        return joiningLobbyState;
    }

    /**
     * This method returns the static in lobby as player one state object.
     *
     * @return the static in lobby as player one state object
     */
    public static InLobbyAsPlayerOneState getInLobbyAsPlayerOneState() {
        return inLobbyAsPlayerOneState;
    }

    /**
     * This method returns the static in lobby as player two state object.
     *
     * @return the static in lobby as player two state object
     */
    public static InLobbyAsPlayerTwoState getInLobbyAsPlayerTwoState() {
        return inLobbyAsPlayerTwoState;
    }

    /**
     * This method returns the static in full lobby as player one state object.
     *
     * @return the static in full lobby as player one state object
     */
    public static InFullLobbyAsPlayerOneState getInFullLobbyAsPlayerOneState() {
        return inFullLobbyAsPlayerOneState;
    }

    /**
     * This method returns the static in uninitialized game object.
     *
     * @return the static in uninitialized game object
     */
    public static UninitializedGameState getUninitializedGameState() {
        return uninitializedGameState;
    }

    /**
     * This method returns the static in game my turn object.
     *
     * @return the static in game my turn object
     */
    public static InGameMyTurnState getInGameMyTurnState() {
        return inGameMyTurnState;
    }

    /**
     * This method returns the static in game opponent's turn object.
     *
     * @return the static in game opponent's turn object
     */
    public static InGameOpponentsTurnState getInGameOpponentsTurnState() {
        return inGameOpponentsTurnState;
    }

    /**
     * This method returns the static winner state object.
     *
     * @return the static winner state object
     */
    public static WinnerState getWinnerState() {
        return winnerState;
    }

    /**
     * This method returns the static tie state object.
     *
     * @return the static winner state object
     */
    public static TieState getTieState() {
        return tieState;
    }

    /**
     * This method returns the static loser state object.
     *
     * @return the static loser state object
     */
    public static LoserState getLoserState() {
        return loserState;
    }

    // TODO: comment react to methods
    public void reactToClickedConnect(String hostName, String port) {
        setState(state.reactToClickedConnect(this, hostName, port));
    }

    public void reactToClickedDisconnect() {
        setState(state.reactToClickedDisconnect(this));
    }

    public void reactToClickedPlay() {
        setState(state.reactToClickedPlay(this));
    }

    public void reactToClickedBack() {
        setState(state.reactToClickedBack(this));
    }

    public void reactToClickedJoinLobby(UUID lobbyId, String userName) {
        setState(state.reactToClickedJoinLobby(this, lobbyId, userName));
    }

    public void reactToClickedLeave() {
        setState(state.reactToClickedLeave(this));
    }

    public void reactToClickedStart() {
        setState(state.reactToClickedStart(this));
    }

    public void reactToReceivedConnectionError() {
        setState(state.reactToReceivedConnectionError(this));
    }

    public void reactToReceivedWelcome(WelcomeMessage message) {
        setState(state.reactToReceivedWelcome(this, message));
    }

    public void reactToReceivedJoinedLobby(LobbyJoinedMessage message) {
        setState(state.reactToReceivedLobbyJoined(this, message));
    }

    public void reactToReceivedLobbyStatus(LobbyStatusMessage message) {
        setState(state.reactToReceivedLobbyStatus(this, message));
    }

    public void reactToReceivedGameStarted(GameStartedMessage message) {
        setState(state.reactToReceivedGameStarted(this, message));
    }

    public void reactToReceivedGameStatus(GameStatusMessage message) {
        setState(state.reactToReceivedGameStatus(this, message));
    }

    public void reactToReceivedServerDisconnect() {
        setState(state.reactToReceivedServerDisconnect(this));
    }

    /**
     * This methods sets the state to the next state parameter, if it is not null.
     * It is synchronized to achieve an atomic state.
     *
     * @param nextState the next state, if not null
     */
    private synchronized void setState(State nextState) {
        if (nextState != null) {
            state = nextState;
        }
    }
}
