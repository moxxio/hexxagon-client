package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

/**
 * The state interface implements methods for scenarios, the state have to be able to react to.
 */
public interface State {

    /**
     * The default implementation of the react to clicked connect event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context  the state context
     * @param hostName the hostname of the server
     * @param port     the port of the server
     * @return null
     */
    default State reactToClickedConnect(StateContext context, String hostName, String port) {
        return null;
    }

    /**
     * The default implementation of the react to clicked disconnect event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedDisconnect(StateContext context) {
        return null;
    }

    /**
     * The default implementation of the react to clicked play event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedPlay(StateContext context) {
        return null;
    }

    /**
     * The default implementation of the react to clicked back event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedBack(StateContext context) {
        return null;
    }

    /**
     * The default implementation of the react to clicked join lobby event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName) {
        return null;
    }

    /**
     * The default implementation of the react to clicked leave event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedLeave(StateContext context) {
        return null;
    }

    /**
     * The default implementation of the react to clicked start event does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this event.
     *
     * @param context the state context
     * @return null
     */
    default State reactToClickedStart(StateContext context) {
        return null;
    }

    /**
     * The default implementation of the react to received welcome message does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this message.
     *
     * @param context the state context
     * @param message the welcome message
     * @return null
     */
    default State reactToReceivedWelcome(StateContext context, WelcomeMessage message) {
        return null;
    }

    /**
     * The default implementation of the react to received lobby joined message does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this message.
     *
     * @param context the state context
     * @param message the lobby joined message
     * @return null
     */
    default State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        return null;
    }

    /**
     * The default implementation of the react to received lobby status message does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this message.
     *
     * @param context the state context
     * @param message the lobby status message
     * @return null
     */
    default State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        return null;
    }

    /**
     * The default implementation of the react to received game started message does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this message.
     *
     * @param context the state context
     * @param message the game started message
     * @return null
     */
    default State reactToReceivedGameStarted(StateContext context, GameStartedMessage message) {
        return null;
    }

    /**
     * The default implementation of the react to received game status message does nothing and just returns null.
     * If a state does not override this method, this means it does not react to this message.
     *
     * @param context the state context
     * @param message the game status message
     * @return null
     */
    default State reactToReceivedGameStatus(StateContext context, GameStatusMessage message) {
        return null;
    }

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
