package de.johannesrauch.hexxagon.fsm.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.state.*;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public StateContext(Hexxagon parent) {
        this.parent = parent;
        executorService = Executors.newFixedThreadPool(1);
        state = disconnectedState;
    }

    public Hexxagon getParent() {
        return parent;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public State getState() {
        return state;
    }

    public static DisconnectedState getDisconnectedState() {
        return disconnectedState;
    }

    public static ConnectionAttemptState getConnectionAttemptState() {
        return connectionAttemptState;
    }

    public static ConnectedState getConnectedState() {
        return connectedState;
    }

    public static SelectLobbyState getSelectLobbyState() {
        return selectLobbyState;
    }

    public static JoiningLobbyState getJoiningLobbyState() {
        return joiningLobbyState;
    }

    public static InLobbyAsPlayerOneState getInLobbyAsPlayerOneState() {
        return inLobbyAsPlayerOneState;
    }

    public static InLobbyAsPlayerTwoState getInLobbyAsPlayerTwoState() {
        return inLobbyAsPlayerTwoState;
    }

    public static InFullLobbyAsPlayerOneState getInFullLobbyAsPlayerOneState() {
        return inFullLobbyAsPlayerOneState;
    }

    public static UninitializedGameState getUninitializedGameState() {
        return uninitializedGameState;
    }

    public static InGameMyTurnState getInGameMyTurnState() {
        return inGameMyTurnState;
    }

    public static InGameOpponentsTurnState getInGameOpponentsTurnState() {
        return inGameOpponentsTurnState;
    }

    public static WinnerState getWinnerState() {
        return winnerState;
    }

    public static TieState getTieState() {
        return tieState;
    }

    public static LoserState getLoserState() {
        return loserState;
    }

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
