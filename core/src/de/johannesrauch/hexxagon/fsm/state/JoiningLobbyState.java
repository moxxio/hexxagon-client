package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

public class JoiningLobbyState implements State {

    /**
     * When in joining lobby state and the user clicks leave, then leave the lobby und show the select lobby screen.
     *
     * @param context the state context
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        return StateContext.getInLobbyAsPlayerOneState().reactToClickedLeave(context);
    }

    /**
     * When in joining lobby state and a joined lobby message is received, stay in this state if joining was successful,
     * otherwise transition to select lobby state.
     *
     * @param context the context in which this state is used
     * @param message the received joined lobby message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = context.getLobbyHandler();

        if (Boolean.TRUE.equals(message.getSuccessfullyJoined())) {
            lobbyHandler.joinedLobby(message.getUserId(), message.getLobbyId());

            return null;
        } else {
            lobbyHandler.leaveLobby();
            parent.showSelectLobbyScreen(false, -1);

            return StateContext.getSelectLobbyState();
        }
    }

    /**
     * When in joining lobby state and a joined lobby message is received, stay in this state if joining was successful,
     * otherwise transition to select lobby state.
     *
     * @param context the context in which this state is used
     * @param message the received lobby status message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        Hexxagon parent = context.getParent();
        LobbyHandler lobbyHandler = context.getLobbyHandler();

        lobbyHandler.updateLobby(message.getLobby());
        parent.showLobbyScreen();

        if (lobbyHandler.isClientPlayerOne() && lobbyHandler.isLobbyReady()) return StateContext.getInFullLobbyAsPlayerOneState();
        if (lobbyHandler.isClientPlayerOne()) return StateContext.getInLobbyAsPlayerOneState();
        return StateContext.getInLobbyAsPlayerTwoState();
    }
}
