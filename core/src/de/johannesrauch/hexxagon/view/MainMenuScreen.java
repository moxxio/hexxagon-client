package de.johannesrauch.hexxagon.view;

import de.johannesrauch.hexxagon.automaton.events.ConnectEvent;
import de.johannesrauch.hexxagon.automaton.events.DisconnectEvent;
import de.johannesrauch.hexxagon.automaton.events.SearchGameEvent;
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
import de.johannesrauch.hexxagon.Hexxagon;

/**
 * This class implements the com.badlogic.gdx.Screen interface.
 * Therefore an instance of this class can be rendered as a screen.
 * <p>
 * This class' purpose is to display the main menu.
 * The user may do the following actions here:
 * - Connecting to a server and then switch to the select lobby screen;
 * - Disconnect from a server;
 * - Close the application.
 * <p>
 * More on the lifecycle of a screen object and when the overridden methods get called can be found in the documentation:
 * https://github.com/libgdx/libgdx/wiki/The-life-cycle.
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class MainMenuScreen implements Screen {
    private final Logger logger = LoggerFactory.getLogger(MainMenuScreen.class);

    private final Hexxagon parent;

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
    private Label connectionStatusLabel;
    private Label versionLabel;

    private InputListener connectionListener;
    private InputListener searchGameListener;
    private InputListener closeListener;
    private InputListener disconnectListener;

    public MainMenuScreen(Hexxagon parent) {
        this.parent = parent;

        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        hostnameTextField = new TextField("localhost", parent.skin);
        portTextField = new TextField("4444", parent.skin);

        connectButton = new TextButton("CONNECT", parent.skin, "small");
        connectionListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                String hostname = hostnameTextField.getText();
                String port = portTextField.getText();
                parent.getStateContext().reactOnEvent(new ConnectEvent(hostname, port));
            }
        };
        connectButton.addListener(connectionListener);

        disconnectButton = new TextButton("DISCONNECT", parent.skin, "small");
        disconnectButton.setTouchable(Touchable.disabled);
        disconnectListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                parent.getStateContext().reactOnEvent(new DisconnectEvent());
            }
        };
        disconnectButton.addListener(disconnectListener);

        closeButton = new TextButton("CLOSE", parent.skin, "small");
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
        closeButton.addListener(closeListener);

        searchGameButton = new TextButton("SEARCH GAME", parent.skin, "small");
        searchGameButton.setVisible(false);
        searchGameListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                parent.getStateContext().reactOnEvent(new SearchGameEvent());
            }
        };
        searchGameButton.addListener(searchGameListener);

        hexxagonLabel = new Label("HEXXAGON", parent.skin);
        hexxagonLabel.setFontScale(2, 2);
        hexxagonLabel.setAlignment(Align.center);

        connectionStatusLabel = new Label("Disconnected", parent.skin);
        connectionStatusLabel.setWrap(true);
        connectionStatusLabel.setAlignment(Align.center);

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
        layoutTable.add(connectionStatusLabel).padBottom(5).minSize(200, 50).colspan(2);
        layoutTable.row();
        layoutTable.add(searchGameButton).minSize(200, 50).colspan(2);

        stage.addActor(layoutTable);
        stage.addActor(versionLabel);
    }

    /**
     * This method creates the ui and sets the input processor.
     *
     * @author Dennis Jehle
     */
    @Override
    public void show() {
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

    public void setConnectionStatusLabelText(String text) {
        connectionStatusLabel.setText(text);
    }

    public void setConnectButtonTouchable(boolean touchable) {
        if (touchable) connectButton.setTouchable(Touchable.enabled);
        else connectButton.setTouchable(Touchable.disabled);
    }

    public void setDisconnectButtonTouchable(boolean touchable) {
        if (touchable) disconnectButton.setTouchable(Touchable.enabled);
        else disconnectButton.setTouchable(Touchable.disabled);
    }

    public void setSearchGameButtonVisible(boolean visible) {
        searchGameButton.setVisible(visible);
    }
}
