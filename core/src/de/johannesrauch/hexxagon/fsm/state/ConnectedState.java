package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;

import java.util.UUID;

public class ConnectedState implements State {

    /**
     * When in the connected state and disconnect is clicked, then disconnect from the server and switch to the
     * disconnected state.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedDisconnect(StateContext context) {
        context.getConnectionHandler().disconnect();

        return StateContext.getDisconnectedState();
    }

    /**
     * When in the connected state and play is clicked, then switch to the select lobby screen and automatically send
     * a get available lobbies message.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedPlay(StateContext context) {
        Hexxagon parent = context.getParent();

        context.getMessageEmitter().sendGetAvailableLobbiesMessage();
        parent.showSelectLobbyScreen(false);

        return StateContext.getSelectLobbyState();
    }
}
