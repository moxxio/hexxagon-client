package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.network.messages.Welcome;
import de.johannesrauch.hexxagon.view.MainMenuScreen;

/**
 * This class represents a received welcome message event.
 * The state context and interface uses this class to model the finite-state machine of the client.
 *
 * @author Johannes Rauch
 */
public class WelcomeEvent implements AbstractEvent {

    private Welcome message;

    public WelcomeEvent(Welcome message) {
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
            mainMenuScreen.setConnectionStatusLabelText("Connected");
            mainMenuScreen.setDisconnectButtonTouchable(true);

            return StateEnum.Connected;
        }

        return currentState;
    }
}
