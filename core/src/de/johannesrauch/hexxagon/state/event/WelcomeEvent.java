package de.johannesrauch.hexxagon.state.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.message.WelcomeMessage;
import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.state.context.StateEnum;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;

/**
 * This class represents a received welcome message event.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class WelcomeEvent implements AbstractEvent {

    private WelcomeMessage message;

    public WelcomeEvent(WelcomeMessage message) {
        this.message = message;
    }

    /**
     * This method gets called by the state context.
     * It executes the reaction on the received welcome message.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.ConnectionAttempt) {
            Hexxagon parent = context.getParent();
            MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

            parent.getConnectionHandler().setUserId(message.getUserId());
            mainMenuScreen.setConnStatusLabel("CONNECTED");
            mainMenuScreen.setDisconnectButtonTouchable(true);
            mainMenuScreen.setSearchButtonVisible(true);

            return StateEnum.Connected;
        }

        return currentState;
    }
}
