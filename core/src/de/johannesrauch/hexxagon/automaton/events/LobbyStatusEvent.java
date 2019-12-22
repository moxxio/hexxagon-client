package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.network.messages.LobbyStatus;

/**
 * This class represents a received lobby status event from the join button in the lobby select menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
 *
 * @author Johannes Rauch
 */
public class LobbyStatusEvent implements AbstractEvent {

    private LobbyStatus message;

    public LobbyStatusEvent(LobbyStatus message) {
        this.message = message;
    }

    /**
     * This method gets called by the current state from the state context.
     * It executes the received lobby status event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = parent.getLobbyHandler();

        lobbyHandler.lobbyJoined(message.getUserId(), message.getLobbyId());
        lobbyHandler.lobbyStatusUpdate(message.getLobby());

        if (currentState == StateEnum.JoiningLobby) {
            parent.getLobbySelectScreen().hideProgressBar(0);
            parent.showLobbyJoinedScreen();

            if (lobbyHandler.isClientPlayerOne() && lobbyHandler.isLobbyReady())
                return StateEnum.InFullLobbyAsPlayerOne;
            if (lobbyHandler.isClientPlayerOne()) return StateEnum.InLobbyAsPlayerOne;
            return StateEnum.InLobbyAsPlayerTwo;
        }

        if (currentState == StateEnum.InLobbyAsPlayerOne) {
            if (lobbyHandler.isLobbyReady()) return StateEnum.InFullLobbyAsPlayerOne;
            return currentState;
        }

        if (currentState == StateEnum.InLobbyAsPlayerTwo) {
            if (!lobbyHandler.isLobbyReady()) return StateEnum.InLobbyAsPlayerOne;
            return currentState;
        }

        if (currentState == StateEnum.InFullLobbyAsPlayerOne) {
            if (!lobbyHandler.isLobbyReady()) return StateEnum.InLobbyAsPlayerOne;
            return currentState;
        }

        return currentState;
    }
}
