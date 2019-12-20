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
 *
 * Die Klasse dient dazu, das Hauptmenü darzustellen.
 * Über das Hauptmenü kann der Benutzer folgende Aktionen ausführen:
 *
 * 1. Eine Verbindung zu einem Spielserver aufbauen
 * 2. Eine bestehende Verbindung zu einem Spielserver trennen
 * 3. Die Anwendung schließen
 * 4. Sobald eine Verbindung zu einem Spielserver hergestellt wurde auf den LobbySelectScreen wechseln
 *
 * Aus dem Interface com.badlogic.gdx.Screen werden folgende Methoden implementiert:
 *
 * show(), render(float delta), resize(int width, int height), pause(), resume(), hide(), dispose()
 *
 * show(): wird aufgerufen nachdem dieser Screen aktiv wird
 * render(float delta): wird jeden Frame aufgerufen, delta gibt die Zeit in Millisekunden seit dem letzten Frame an
 *
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

        // Dieser InputListener soll verwendet werden, wenn der 'CONNECT' Button gedrückt wird.
        // Innerhalb des InputListeners wird eine Verbindung zum angegebenen Server aufgebaut.
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

        // Dieser InputListener soll verwendet werden, wenn der 'DISCONNECT' Button gedrückt wird.
        // Innerhalb des InputListeners wird die Verbindung zum Server getrennt.
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

        // Dieser InputListener soll verwendet werden, wenn der 'SEARCH GAME' Button gedrückt wird.
        // Innerhalb des InputListeners wird eine Nachricht zum Abrufen der verfügbaren Lobbies an den Server gesendet.
        // Außerdem wird der aktive Screen gewechselt und es wird der LobbySelectScreen zum aktiven Screen.
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

        // Dieser InputListener soll verwendet werden, wenn der 'CLOSE' Button gedrückt wird.
        // Innerhalb des InputListeners wird die Applikation beendet.
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
        // Camera, Viewport und Stage erzeugen
        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        // TextFields zur Eingabe von Hostname und Port erzeugen
        hostnameTextField = new TextField("localhost", Hexxagon.skin);
        portTextField = new TextField("4444", Hexxagon.skin);

        // Buttons für Hauptmenü erzeugen und an InputListener binden
        connectButton = new TextButton("CONNECT", Hexxagon.skin, "small");
        connectButton.addListener(connectionListener);

        disconnectButton = new TextButton("DISCONNECT", Hexxagon.skin, "small");
        disconnectButton.addListener(disconnectListener);

        closeButton = new TextButton("CLOSE", Hexxagon.skin, "small");
        closeButton.addListener(closeListener);

        searchGameButton = new TextButton("SEARCH GAME", Hexxagon.skin, "small");
        searchGameButton.setVisible(false);
        searchGameButton.addListener(searchGameListener);

        // Labels erzeugen
        hexxagonLabel = new Label("HEXXAGON", Hexxagon.skin);
        hexxagonLabel.setFontScale(2, 2);
        hexxagonLabel.setAlignment(Align.center);

        connectionStatusLable = new Label("disconnected", Hexxagon.skin);
        connectionStatusLable.setWrap(true);
        connectionStatusLable.setAlignment(Align.center);

        versionLabel = new Label("Version " + Hexxagon.versionNumber, Hexxagon.skin);
        versionLabel.setPosition(15, 15);

        // Alle erzeugten UI Komponenten in Layout Tabelle einfügen
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

        // alle UI Komponenten zur Stage hinzufügen
        stage.addActor(layoutTable);
        stage.addActor(versionLabel);

        // Partikeleffekt starten und positionieren
        Hexxagon.particleEffect.start();
        Hexxagon.particleEffect.setPosition((float) viewport.getScreenWidth() / 2, (float) viewport.getScreenHeight() / 2);

        // Durch diesen Befehl wird die in diesem Screen erzeugte Stage als InputProcessor gesetzt 
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Hexxagon.spriteBatch.begin();
        Hexxagon.spriteBatch.draw(Hexxagon.space, 0, 0, 1024, 576);
        if (Hexxagon.particleEffect.isComplete()) Hexxagon.particleEffect.reset();
        else Hexxagon.particleEffect.draw(Hexxagon.spriteBatch, delta);
        Hexxagon.spriteBatch.end();

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
