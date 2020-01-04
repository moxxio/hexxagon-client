package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

/**
 * TODO: more default methods to prevent code duplication
 * The state interface implements methods for scenarios, the state have to be able to react to.
 */
public interface State {
    public State reactToClickedConnect(StateContext context, String hostName, String port);

    public State reactToClickedDisconnect(StateContext context);

    public State reactToClickedPlay(StateContext context);

    public State reactToClickedBack(StateContext context);

    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName);

    public State reactToClickedLeave(StateContext context);

    public State reactToClickedStart(StateContext context);

    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message);

    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message);

    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message);

    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message);

    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message);

    /**
     * This is the default implementation for connection errors. It is not necessary to override it.
     *
     * @param context the state context this method gets called in.
     * @return the next state or null, if it does not change
     */
    default State reactToReceivedConnectionError(StateContext context) {
        Hexxagon parent = context.getParent();

        context.getConnectionHandler().disconnect();
        parent.showMainMenuScreen();

        return StateContext.getDisconnectedState();
    }

    /**
     * This is the default implementation for server disconnects. It is not necessary to override it.
     *
     * @param context the state context this method gets called in.
     * @return the next state or null, if it does not change
     */
    default State reactToReceivedServerDisconnect(StateContext context) {
        return reactToReceivedConnectionError(context);
    }
}
