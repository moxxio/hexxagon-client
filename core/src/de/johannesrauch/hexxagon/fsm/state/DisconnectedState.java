package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.fsm.context.StateContext;

public class DisconnectedState implements State {

    /**
     * When the clicked button is pressed in the disconnected state, try to open a websocket connection with the
     * specified server. After that, switch to the connection attempt state.
     *
     * @param context  the context in which this state is used
     * @param hostName the hostname of the server
     * @param port     the port of the server
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedConnect(StateContext context, String hostName, String port) {
        context.getConnectionHandler().connect(hostName, port);

        return StateContext.getConnectionAttemptState();
    }
}
