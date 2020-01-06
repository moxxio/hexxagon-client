package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class SelectLobbyState implements State {

    /**
     * When in the selected lobby state and the back button is clicked, switch to the connected state and show the main
     * menu.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedBack(StateContext context) {
        Hexxagon parent = context.getParent();

        parent.showMainMenuScreen();

        return StateContext.getConnectedState();
    }

    /**
     * When in the selected lobby state and the join button is clicked, switch to the joining lobby state and send a
     * join lobby message.
     *
     * @param context the context in which this state is used
     * @param lobbyId the lobby uuid
     * @param userName the username string
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName) {
        context.getMessageEmitter().sendJoinLobbyMessage(lobbyId, userName);

        return StateContext.getJoiningLobbyState();
    }
}
