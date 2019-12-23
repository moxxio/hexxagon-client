package de.johannesrauch.hexxagon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.johannesrauch.hexxagon.controller.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.resource.Resources;
import de.johannesrauch.hexxagon.state.context.StateContext;
import de.johannesrauch.hexxagon.view.GameScreen;
import de.johannesrauch.hexxagon.view.LobbyScreen;
import de.johannesrauch.hexxagon.view.MainMenuScreen;
import de.johannesrauch.hexxagon.view.SelectLobbyScreen;

import javax.swing.plaf.nimbus.State;

public class Hexxagon extends Game {

    private StateContext context;

    private Resources resources;

    private ConnectionHandler connectionHandler;
    private LobbyHandler lobbyHandler;
    private GameHandler gameHandler;

    private MainMenuScreen mainMenuScreen;
    private SelectLobbyScreen selectLobbyScreen;
    private LobbyScreen lobbyScreen;
    private GameScreen gameScreen;

    @Override
    public void create() {
        Gdx.graphics.setTitle("Hexxagon");

        context = new StateContext(this);

        resources = new Resources();

        connectionHandler = new ConnectionHandler(this);
        lobbyHandler = new LobbyHandler(this);
        gameHandler = new GameHandler(this);

        mainMenuScreen = new MainMenuScreen(this);
        selectLobbyScreen = new SelectLobbyScreen(this);
        lobbyScreen = new LobbyScreen(this);
        gameScreen = new GameScreen(this);

        showMainMenuScreen();
        // showSelectLobby();
        // showLobby();
        // showGame();
    }

    @Override
    public void dispose() {
        // TODO: free resources
        super.dispose();
    }

    public StateContext getContext() {
        return context;
    }

    public Resources getResources() {
        return resources;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public MessageEmitter getMessageEmitter() {
        return connectionHandler.getMessageEmitter();
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public SelectLobbyScreen getSelectLobbyScreen() {
        return selectLobbyScreen;
    }

    public LobbyScreen getLobbyScreen() {
        return lobbyScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void showMainMenuScreen() {
        setScreen(mainMenuScreen);
    }

    public void showSelectLobbyScreen() {
        selectLobbyScreen.hideProgressBar();
        setScreen(selectLobbyScreen);
    }

    public void showLobbyScreen() {
        setScreen(lobbyScreen);
    }

    public void showGameScreen() {
        setScreen(gameScreen);
    }
}
