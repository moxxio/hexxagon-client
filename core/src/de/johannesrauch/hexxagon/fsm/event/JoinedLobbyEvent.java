package de.johannesrauch.hexxagon.fsm.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.message.LobbyJoinedMessage;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.context.StateEnum;

/**
 * This class represents a received joined lobby event from the join button in the lobby select menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class JoinedLobbyEvent implements AbstractEvent {

    private LobbyJoinedMessage message;

    public JoinedLobbyEvent(LobbyJoinedMessage message) {
        this.message = message;
    }

    /**
     * This method gets called by the state context.
     * It executes the reaction on the received joined lobby event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();
        Hexxagon parent = context.getParent();

        if (currentState == StateEnum.JoiningLobby) {
            if (message.getSuccessfullyJoined()) {
                parent.getLobbyHandler().joinedLobby(message.getUserId(), message.getLobbyId());
                return currentState;
            } else {
                parent.showSelectLobbyScreen();
                return StateEnum.SearchLobby;
            }
        }

        if (currentState == StateEnum.SearchLobby) {
            parent.getMessageEmitter().sendLeaveLobbyMessage(message.getLobbyId());
        }

        return currentState;
    }
}
