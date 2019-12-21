package de.johannesrauch.hexxagon.automaton.events;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;
import de.johannesrauch.hexxagon.network.clients.SimpleClient;
import de.johannesrauch.hexxagon.view.MainMenuScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a pressed connect event from the connect button in the main menu.
 * The state context and interface uses this class to model the finite-state machine of the client.
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
     * This method gets called by the current state from the state context.
     * It executes the connection attempt.
     * @param context the state context in which this event object is used
     * @return the next state or null, if the finite-state machine stays in his current state
     */
    @Override
    public State reactOnEvent(StateContext context) { // TODO: concurrency
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
            return context.getConnAttemptState();
        } else {
            mainMenuScreen.setConnectionStatusLabelText("Connection failed");
            mainMenuScreen.setConnectButtonTouchable(true);
            mainMenuScreen.setSearchGameButtonVisible(false);
            return null;
        }
    }
}
