package de.johannesrauch.hexxagon.state.event;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.state.context.StateEnum;
import de.johannesrauch.hexxagon.view.MainMenuScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a pressed connect event from the connect button in the main menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class ConnectEvent implements AbstractEvent {

    private final Logger logger = LoggerFactory.getLogger(ConnectEvent.class);

    private String hostName, port;

    public ConnectEvent(String hostName, String port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * This method gets called by the the state context.
     * It executes the reaction on the connection attempt event.
     *
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public StateEnum reactOnEvent(StateContext context) {
        StateEnum currentState = context.getState();
        Hexxagon parent = context.getParent();
        MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();

        if (currentState == StateEnum.Disconnected) {
            mainMenuScreen.setConnStatusLabel("CONNECTING...");
            mainMenuScreen.setConnectButtonTouchable(false);

            context.getExecutorService().submit(() -> {
                parent.getConnectionHandler().connect(hostName, port);
            });

            return StateEnum.ConnectionAttempt;
        }

        return currentState;
    }
}
