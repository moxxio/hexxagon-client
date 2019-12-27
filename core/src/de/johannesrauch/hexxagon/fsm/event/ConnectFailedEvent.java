package de.johannesrauch.hexxagon.fsm.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.context.StateEnum;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;

/**
 * This class represents a connect failed event from the simple client.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class ConnectFailedEvent implements AbstractEvent {

    /**
     * This method gets called by the the state context.
     * It executes the reaction on the connection failed event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();
        Hexxagon parent = context.getParent();
        MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

        if (currentState == StateEnum.ConnectionAttempt) {
            mainMenuScreen.setConnStatusLabel("DISCONNECTED");
            mainMenuScreen.setConnectButtonTouchable(true);
            mainMenuScreen.setSearchButtonVisible(false);

            context.getExecutorService().submit(() -> {
                parent.getConnectionHandler().disconnect();
            });

            return StateEnum.Disconnected;
        }

        return currentState;
    }
}
