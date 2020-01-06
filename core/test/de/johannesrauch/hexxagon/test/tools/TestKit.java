package de.johannesrauch.hexxagon.test.tools;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;

public class TestKit {

    public HexxagonDummy hexxagon;
    public StateContext context;
    public ConnectionHandler connectionHandler;
    public LobbyHandler lobbyHandler;
    public GameHandler gameHandler;
    public Gson gson;

    public TestKit(State initialState) {
        hexxagon = new HexxagonDummy();
        context = new StateContext(hexxagon);
        connectionHandler = new ConnectionHandler();
        lobbyHandler = new LobbyHandler();
        gameHandler = new GameHandler();
        gson = new Gson();

        context.setState(initialState);
        context.setConcurrent(false);
        context.setConnectionHandler(connectionHandler);
        context.setLobbyHandler(lobbyHandler);
        context.setGameHandler(gameHandler);
        connectionHandler.setContext(context);
        connectionHandler.setLobbyHandler(lobbyHandler);
        connectionHandler.setGameHandler(gameHandler);
        lobbyHandler.setConnectionHandler(connectionHandler);
        gameHandler.setConnectionHandler(connectionHandler);
    }
}
