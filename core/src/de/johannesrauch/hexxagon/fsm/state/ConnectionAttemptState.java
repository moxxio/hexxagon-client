package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

public class ConnectionAttemptState implements State {

    /**
     * When you receive a welcome message in the connection attempt state, then the client is connected and
     * authenticated by the server.
     *
     * @param context the context in which this state is used
     * @param message the received welcome message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message) {
        Hexxagon parent = context.getParent();

        ConnectionHandler connectionHandler = context.getConnectionHandler();
        connectionHandler.setUserId(message.getUserId());
        parent.showMainMenuScreen(false, false);

        return StateContext.getConnectedState();
    }
}
