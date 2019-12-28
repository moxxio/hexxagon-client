package de.johannesrauch.hexxagon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.resource.Resources;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.view.screen.GameScreen;
import de.johannesrauch.hexxagon.view.screen.LobbyScreen;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;
import de.johannesrauch.hexxagon.view.screen.SelectLobbyScreen;
import org.apache.log4j.BasicConfigurator;

// TODO: Create a class with public static values for the ui
// TODO: dialog window at server disconnect or connection error
public class Hexxagon extends Game {

    private static String version = "1.0";

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
        BasicConfigurator.configure();

        context = new StateContext(this);
        resources = new Resources();
        connectionHandler = new ConnectionHandler();
        lobbyHandler = new LobbyHandler();
        gameHandler = new GameHandler();

        connectionHandler.setContext(context);
        connectionHandler.setLobbyHandler(lobbyHandler);
        connectionHandler.setGameHandler(gameHandler);
        lobbyHandler.setMessageEmitter(connectionHandler.getMessageEmitter());
        gameHandler.setMessageEmitter(connectionHandler.getMessageEmitter());

        mainMenuScreen = new MainMenuScreen(this);
        selectLobbyScreen = new SelectLobbyScreen(this);
        lobbyScreen = new LobbyScreen(this);
        gameScreen = new GameScreen(this);

        showMainMenuScreen();
    }

    @Override
    public void dispose() {
        super.dispose();
        mainMenuScreen.dispose();
        selectLobbyScreen.dispose();
        lobbyScreen.dispose();
        gameScreen.dispose();
    }

    public static String getVersion() {
        return version;
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
