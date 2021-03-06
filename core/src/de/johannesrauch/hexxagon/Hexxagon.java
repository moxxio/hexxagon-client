package de.johannesrauch.hexxagon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.resource.Resources;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.view.screen.GameScreen;
import de.johannesrauch.hexxagon.view.screen.LobbyScreen;
import de.johannesrauch.hexxagon.view.screen.MainMenuScreen;
import de.johannesrauch.hexxagon.view.screen.SelectLobbyScreen;
import org.apache.log4j.BasicConfigurator;

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

        context.setConnectionHandler(connectionHandler);
        context.setLobbyHandler(lobbyHandler);
        context.setGameHandler(gameHandler);
        connectionHandler.setContext(context);
        connectionHandler.setLobbyHandler(lobbyHandler);
        connectionHandler.setGameHandler(gameHandler);
        lobbyHandler.setConnectionHandler(connectionHandler);
        gameHandler.setConnectionHandler(connectionHandler);

        mainMenuScreen = new MainMenuScreen(this, context);
        selectLobbyScreen = new SelectLobbyScreen(this, context, connectionHandler, lobbyHandler);
        lobbyScreen = new LobbyScreen(this, context, lobbyHandler);
        gameScreen = new GameScreen(this, context, gameHandler);

        showMainMenuScreen(false, false);
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

    public Resources getResources() {
        return resources;
    }

    public void showMainMenuScreen(boolean connectionError, boolean serverDisconnect) {
        setScreen(mainMenuScreen);
        if (connectionError) mainMenuScreen.showConnectionError();
        if (serverDisconnect) mainMenuScreen.showServerDisconnect();
    }

    public void showSelectLobbyScreen(boolean showProgressBar, int millis) {
        if (showProgressBar) selectLobbyScreen.showProgressBar();
        else {
            if (millis > 0) selectLobbyScreen.hideProgressBar(millis);
            else selectLobbyScreen.hideProgressBar();
        }
        setScreen(selectLobbyScreen);
    }

    public void showLobbyScreen() {
        selectLobbyScreen.hideProgressBar(500);
        setScreen(lobbyScreen);
    }

    public void showGameScreen() {
        setScreen(gameScreen);
    }
}
