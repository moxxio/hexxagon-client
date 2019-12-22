package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.StateEnum;
import de.johannesrauch.hexxagon.network.clients.SimpleClient;
import de.johannesrauch.hexxagon.view.MainMenuScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a pressed connect event from the connect button in the main menu.
 * The state context uses this class as a transition event. A new state may occur.
 *
 * @author Johannes Rauch
 */
public class ConnectEvent implements AbstractEvent {

    private final Logger logger = LoggerFactory.getLogger(ConnectEvent.class);

    private String hostname, port;

    public ConnectEvent(String hostname, String port) {
        this.hostname = hostname;
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
    public StateEnum reactOnEvent(StateContext context) { // TODO: concurrency
        StateEnum currentState = context.getState();

        if (currentState == StateEnum.Disconnected) {
            Hexxagon parent = context.getParent();
            MainMenuScreen mainMenuScreen = parent.getMainMenuScreen();
            boolean successfullyConnected = false;

            mainMenuScreen.setConnectionStatusLabelText("Connecting");
            mainMenuScreen.setConnectButtonTouchable(false);
            mainMenuScreen.setSearchGameButtonVisible(true);

            SimpleClient simpleClient = null;
            try {
                simpleClient = new SimpleClient(new URI("ws://" + hostname + ":" + port),
                        parent.getConnectionHandler(),
                        parent.getMessageReceiver());
                successfullyConnected = simpleClient.connectBlocking(10, TimeUnit.SECONDS);
            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }

            if (successfullyConnected) {
                parent.getConnectionHandler().setSimpleClient(simpleClient);
                return StateEnum.ConnectionAttempt;
            } else {
                mainMenuScreen.setConnectionStatusLabelText("Connection failed");
                mainMenuScreen.setConnectButtonTouchable(true);
                mainMenuScreen.setSearchGameButtonVisible(false);
                return StateEnum.Disconnected;
            }
        }

        return currentState;
    }
}
