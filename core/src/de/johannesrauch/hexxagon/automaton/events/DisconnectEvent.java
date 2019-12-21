package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;
import de.johannesrauch.hexxagon.view.MainMenuScreen;

/**
 * This class represents the pressed disconnect event from the disconnect button in the main menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
 * @author Johannes Rauch
 */
public class DisconnectEvent implements AbstractEvent {

    /**
     * This method gets called by the current state from the state context.
     * It executes the disconnect event.
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public State reactOnEvent(StateContext context) {
        Hexxagon parent = context.getParent();
        MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

        parent.getConnectionHandler().closeConnection();
        mainMenuScreen.setConnectionStatusLabelText("Disconnected");
        mainMenuScreen.setConnectButtonTouchable(true);
        mainMenuScreen.setSearchGameButtonVisible(false);
        mainMenuScreen.setDisconnectButtonTouchable(false);

        return context.getDisconnectedState();
    }
}
