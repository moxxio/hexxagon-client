package de.johannesrauch.hexxagon.fsm.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.state.*;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
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

    private ConnectionHandler connectionHandler;
    private LobbyHandler lobbyHandler;
    private GameHandler gameHandler;

    private State state;
    private State previousState;

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

    private boolean stateUpdated;
    private boolean concurrent;

    /**
     * This is the standard constructor. It sets the parent, creates the executor service and sets the initial state.
     *
     * @param parent the parent
     */
    public StateContext(Hexxagon parent) {
        this.parent = parent;
        executorService = Executors.newFixedThreadPool(1);
        setState(disconnectedState); // Call two times to init previous state
        setState(disconnectedState);
        concurrent = true;
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
     * This method returns the current state.
     *
     * @return the current state
     */
    public synchronized State getState() {
        return state;
    }

    /**
     * This method returns the previous state.
     *
     * @return the previous state
     */
    public synchronized State getPreviousState() {
        return previousState;
    }

    /**
     * This method returns the connection handler.
     *
     * @return the connection handler
     */
    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    /**
     * This method returns the lobby handler.
     *
     * @return the lobby handler
     */
    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    /**
     * This method returns the game handler.
     *
     * @return the game handler
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    /**
     * This method returns the message emitter. If the connection handler is null, return null.
     *
     * @return the message emitter or null, if the connection handler is null
     */
    public MessageEmitter getMessageEmitter() {
        if (connectionHandler != null) return connectionHandler.getMessageEmitter();
        return null;
    }

    /**
     * This method returns whether the state has been updated since the last check. It resets the state updated flag.
     *
     * @return true, if the state has been updated, false otherwise
     */
    public boolean hasStateUpdated() {
        if (stateUpdated) {
            stateUpdated = false;
            return true;
        }
        return false;
    }

    /**
     * This method sets the connection handler of the context.
     *
     * @param connectionHandler the connection handler
     */
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    /**
     * This method sets the lobby handler of the context.
     *
     * @param lobbyHandler the lobby handler
     */
    public void setLobbyHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    /**
     * This method sets the game handler of the context.
     *
     * @param gameHandler the game handler
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
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

    /**
     * This method reacts to a clicked connect user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param hostName the host to connect to
     * @param port     the port to connect to
     */
    public void reactToClickedConnect(String hostName, String port) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedConnect(this, hostName, port)));
            else setState(state.reactToClickedConnect(this, hostName, port));
        }
    }

    /**
     * This method reacts to a clicked disconnect user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToClickedDisconnect() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedDisconnect(this)));
            else setState(state.reactToClickedDisconnect(this));
        }
    }

    /**
     * This method reacts to a clicked play user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToClickedPlay() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedPlay(this)));
            else setState(state.reactToClickedPlay(this));
        }
    }

    /**
     * This method reacts to a clicked back user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToClickedBack() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedBack(this)));
            else setState(state.reactToClickedBack(this));
        }
    }

    /**
     * This method reacts to a clicked join lobby user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param lobbyId  the lobby uuid to join
     * @param userName the username of the user
     */
    public void reactToClickedJoinLobby(UUID lobbyId, String userName) {
        if (isInitialized()) {
            if (concurrent)
                executorService.submit(() -> setState(state.reactToClickedJoinLobby(this, lobbyId, userName)));
            else setState(state.reactToClickedJoinLobby(this, lobbyId, userName));
        }
    }

    /**
     * This method reacts to a clicked leave user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToClickedLeave() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedLeave(this)));
            else setState(state.reactToClickedLeave(this));
        }
    }

    /**
     * This method reacts to a clicked start user input.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToClickedStart() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToClickedStart(this)));
            else setState(state.reactToClickedStart(this));
        }
    }

    /**
     * This method reacts to a received connection error.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToReceivedConnectionError() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedConnectionError(this)));
            else setState(state.reactToReceivedConnectionError(this));
        }
    }

    /**
     * This method reacts to a received welcome message.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param message the received welcome message
     */
    public void reactToReceivedWelcome(WelcomeMessage message) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedWelcome(this, message)));
            else setState(state.reactToReceivedWelcome(this, message));
        }
    }

    /**
     * This method reacts to a received lobby joined message.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param message the received lobby joined message
     */
    public void reactToReceivedLobbyJoined(LobbyJoinedMessage message) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedLobbyJoined(this, message)));
            else setState(state.reactToReceivedLobbyJoined(this, message));
        }
    }

    /**
     * This method reacts to a received lobby status message.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param message the received lobby status message
     */
    public void reactToReceivedLobbyStatus(LobbyStatusMessage message) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedLobbyStatus(this, message)));
            else setState(state.reactToReceivedLobbyStatus(this, message));
        }
    }

    /**
     * This method reacts to a received game started message.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param message the received game started message
     */
    public void reactToReceivedGameStarted(GameStartedMessage message) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedGameStarted(this, message)));
            else setState(state.reactToReceivedGameStarted(this, message));
        }
    }

    /**
     * This method reacts to a received game status message.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     *
     * @param message the received game status message
     */
    public void reactToReceivedGameStatus(GameStatusMessage message) {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedGameStatus(this, message)));
            else setState(state.reactToReceivedGameStatus(this, message));
        }
    }

    /**
     * This method reacts to a received server disconnect.
     * It submits this task to a executor service such that the io does not get blocked.
     * (You can disable concurrency by setting the concurrent flag to false. But this is originally for testing.)
     */
    public void reactToReceivedServerDisconnect() {
        if (isInitialized()) {
            if (concurrent) executorService.submit(() -> setState(state.reactToReceivedServerDisconnect(this)));
            else setState(state.reactToReceivedServerDisconnect(this));
        }
    }

    /**
     * This methods sets the state to the next state parameter, if it is not null.
     * It is synchronized to achieve an atomic state.
     *
     * @param nextState the next state, if not null
     */
    public synchronized void setState(State nextState) {
        if (nextState != null) {
            previousState = state;
            state = nextState;
            stateUpdated = true;
        }
    }

    /**
     * This method returns whether the state context is initialized. If the parent, the connection handler,
     * the lobby handler and the game handler are not null, then the context is initialized.
     * If the context is not initialized, it may throws null-pointer exceptions.
     *
     * @return true, if the context is initialized, false otherwise
     */
    private boolean isInitialized() {
        return parent != null && connectionHandler != null && lobbyHandler != null && gameHandler != null;
    }
}
