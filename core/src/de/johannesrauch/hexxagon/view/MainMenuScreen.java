package de.johannesrauch.hexxagon.view;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.controller.Hexxagon;
import de.johannesrauch.hexxagon.controller.MessageEmitter;
import de.johannesrauch.hexxagon.controller.MessageReceiver;
import de.johannesrauch.hexxagon.controller.SimpleClient;

/**
 * Die Klasse MainMenuScreen implementiert das com.badlogic.gdx.Screen Interface.
 * Somit kann eine Instanz der Klasse als Screen gerendert werden.
 * <p>
 * Die Klasse dient dazu, das Hauptmenü darzustellen.
 * Über das Hauptmenü kann der Benutzer folgende Aktionen ausführen:
 * <p>
 * 1. Eine Verbindung zu einem Spielserver aufbauen
 * 2. Eine bestehende Verbindung zu einem Spielserver trennen
 * 3. Die Anwendung schließen
 * 4. Sobald eine Verbindung zu einem Spielserver hergestellt wurde auf den LobbySelectScreen wechseln
 * <p>
 * Aus dem Interface com.badlogic.gdx.Screen werden folgende Methoden implementiert:
 * <p>
 * show(), render(float delta), resize(int width, int height), pause(), resume(), hide(), dispose()
 * <p>
 * show(): wird aufgerufen nachdem dieser Screen aktiv wird
 * render(float delta): wird jeden Frame aufgerufen, delta gibt die Zeit in Millisekunden seit dem letzten Frame an
 * <p>
 * Mehr zum Life Cycle finden Sie unter: https://github.com/libgdx/libgdx/wiki/The-life-cycle
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class MainMenuScreen implements Screen {

    private final Logger logger = LoggerFactory.getLogger(MainMenuScreen.class);

    private Hexxagon parent;

    private ConnectionHandler connectionHandler;
    private MessageReceiver messageReceiver;
    private GameHandler gameHandler;
    private MessageEmitter messageEmitter;

    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Table layoutTable;

    private Button connectButton;
    private Button disconnectButton;
    private Button closeButton;
    private Button searchGameButton;

    private TextField hostnameTextField;
    private TextField portTextField;

    private Label hexxagonLabel;
    private Label connectionStatusLable;
    private Label versionLabel;

    private InputListener connectionListener;
    private InputListener searchGameListener;
    private InputListener closeListener;
    private InputListener disconnectListener;

    public MainMenuScreen(Hexxagon parent,
                          ConnectionHandler connectionHandler,
                          MessageReceiver messageReceiver,
                          GameHandler gameHandler,
                          MessageEmitter messageEmitter) {
        this.parent = parent;
        this.connectionHandler = connectionHandler;
        this.messageReceiver = messageReceiver;
        this.gameHandler = gameHandler;
        this.messageEmitter = messageEmitter;

        connectionListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                boolean successfullyConnected = false;
                connectClient:
                try {
                    String hostname = hostnameTextField.getText();
                    String port = portTextField.getText();

                    SimpleClient simpleClient = new SimpleClient(new URI("ws://" + hostname + ":" + port),
                            connectionHandler,
                            messageReceiver);
                    successfullyConnected = simpleClient.connectBlocking(10, TimeUnit.SECONDS);

                    if (!successfullyConnected) {
                        break connectClient; // TODO: throw exception
                    }

                    connectionHandler.setSimpleClient(simpleClient);
                    connectionStatusLable.setText("connected");
                    connectButton.setTouchable(Touchable.disabled);
                    searchGameButton.setVisible(true);
                    successfullyConnected = true;
                } catch (Exception e) {
                    logger.error("Exception: " + e.getMessage());
                } finally {
                    if (!successfullyConnected) {
                        connectButton.setTouchable(Touchable.enabled);
                        connectionStatusLable.setText("connection failed");
                        searchGameButton.setVisible(false);
                    }
                }
            }
        };

        disconnectListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                connectionHandler.closeConnection();
                connectButton.setTouchable(Touchable.enabled);
                connectionStatusLable.setText("disconnected");
                searchGameButton.setVisible(false);
            }
        };

        searchGameListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    messageEmitter.sendGetAvailableLobbiesMessage();
                    parent.showLobbySelectScreen();
                } catch (NullPointerException npe) {
                    logger.error("NullPointerException: " + npe.getMessage());
                }
            }
        };

        closeListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        };
    }

    /**
     * Diese Methode erzeugt die UI und setzt den InputProcessor.
     *
     * @author Dennis Jehle
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        hostnameTextField = new TextField("localhost", parent.skin);
        portTextField = new TextField("4444", parent.skin);

        connectButton = new TextButton("CONNECT", parent.skin, "small");
        connectButton.addListener(connectionListener);

        disconnectButton = new TextButton("DISCONNECT", parent.skin, "small");
        disconnectButton.addListener(disconnectListener);

        closeButton = new TextButton("CLOSE", parent.skin, "small");
        closeButton.addListener(closeListener);

        searchGameButton = new TextButton("SEARCH GAME", parent.skin, "small");
        searchGameButton.setVisible(false);
        searchGameButton.addListener(searchGameListener);

        hexxagonLabel = new Label("HEXXAGON", parent.skin);
        hexxagonLabel.setFontScale(2, 2);
        hexxagonLabel.setAlignment(Align.center);

        connectionStatusLable = new Label("disconnected", parent.skin);
        connectionStatusLable.setWrap(true);
        connectionStatusLable.setAlignment(Align.center);

        versionLabel = new Label("Version " + Hexxagon.versionNumber, parent.skin);
        versionLabel.setPosition(15, 15);

        layoutTable = new Table();
        layoutTable.setWidth(stage.getWidth());
        layoutTable.align(Align.top | Align.center);
        layoutTable.setPosition(0, Gdx.graphics.getHeight());
        layoutTable.padTop(30);
        layoutTable.add(hexxagonLabel).padBottom(15).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(hostnameTextField).minSize(200, 50);
        layoutTable.add(portTextField).minSize(100, 50);
        layoutTable.row();
        layoutTable.add(connectButton).padTop(15).padBottom(5).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(disconnectButton).padBottom(5).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(closeButton).padBottom(5).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(connectionStatusLable).padBottom(5).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(searchGameButton).minSize(200, 50).colspan(2);

        stage.addActor(layoutTable);
        stage.addActor(versionLabel);

        parent.particleEffect.start();
        parent.particleEffect.setPosition((float) viewport.getScreenWidth() / 2, (float) viewport.getScreenHeight() / 2);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.spriteBatch.begin();
        parent.spriteBatch.draw(parent.space, 0, 0, 1024, 576);
        if (parent.particleEffect.isComplete()) parent.particleEffect.reset();
        else parent.particleEffect.draw(parent.spriteBatch, delta);
        parent.spriteBatch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // TODO: geben Sie alle Ressourcen mit implementiertem Disposable Interface frei
    }

}
