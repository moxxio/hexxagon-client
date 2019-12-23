package de.johannesrauch.hexxagon.state.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.network.message.LobbyStatusMessage;
import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.state.context.StateEnum;

/**
 * This class represents a received lobby status event from the join button in the lobby select menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class LobbyStatusEvent implements AbstractEvent {

    private LobbyStatusMessage message;

    public LobbyStatusEvent(LobbyStatusMessage message) {
        this.message = message;
    }

    /**
     * This method gets called by the current state from the state context.
     * It executes the reaction on the received lobby status event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = parent.getLobbyHandler();

        if (currentState == StateEnum.JoiningLobby) {
            lobbyHandler.updateLobby(message.getLobby());
            parent.showLobbyScreen();

            if (lobbyHandler.isClientPlayerOne() && lobbyHandler.isLobbyReady())
                return StateEnum.InFullLobbyAsPlayerOne;
            if (lobbyHandler.isClientPlayerOne()) return StateEnum.InLobbyAsPlayerOne;
            return StateEnum.InLobbyAsPlayerTwo;
        }

        if (currentState == StateEnum.InLobbyAsPlayerOne) {
            lobbyHandler.updateLobby(message.getLobby());

            if (lobbyHandler.isLobbyReady()) return StateEnum.InFullLobbyAsPlayerOne;
            return currentState;
        }

        if (currentState == StateEnum.InLobbyAsPlayerTwo) {
            lobbyHandler.updateLobby(message.getLobby());

            if (!lobbyHandler.isLobbyReady()) return StateEnum.InLobbyAsPlayerOne;
            return currentState;
        }

        if (currentState == StateEnum.InFullLobbyAsPlayerOne) {
            lobbyHandler.updateLobby(message.getLobby());

            if (!lobbyHandler.isLobbyReady()) return StateEnum.InLobbyAsPlayerOne;
            return currentState;
        }

        return currentState;
    }
}
