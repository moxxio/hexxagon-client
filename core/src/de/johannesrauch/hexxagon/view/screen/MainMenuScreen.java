package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;

/**
 * This class is the main menu screen.
 */
public class MainMenuScreen extends BaseScreen {

    private Label headingLabel;
    private ButtonStyleLabel connStatusLabel;
    private ButtonStyleLabel versionLabel;

    private TextField hostNameTextField;
    private TextField portTextField;

    private TextButton connectButton;
    private TextButton disconnectButton;
    private TextButton playButton;
    private TextButton closeButton;

    private Table mainTable;

    private SpriteBatch spriteBatch;

    private final StateContext context;

    /**
     * This standard constructor sets everything up.
     *
     * @param parent the parent
     */
    public MainMenuScreen(Hexxagon parent, StateContext context) {
        super(parent);
        Skin skin = parent.getResources().getSkin();
        this.context = context;

        headingLabel = new Label(Lettering.HEXXAGON, skin, "title");
        connStatusLabel = new ButtonStyleLabel(Lettering.DISCONNECTED, skin);
        connStatusLabel.setPosition(20, 20);
        connStatusLabel.setSize(300, 50);
        versionLabel = new ButtonStyleLabel(Lettering.VERSION + " " + Hexxagon.getVersion(), skin);
        versionLabel.setPosition(960, 20);
        versionLabel.setSize(300, 50);

        hostNameTextField = new TextField("localhost", skin);
        hostNameTextField.setAlignment(Align.center);
        portTextField = new TextField("4444", skin);
        portTextField.setAlignment(Align.center);

        connectButton = new TextButton(Lettering.CONNECT, skin);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String hostName = hostNameTextField.getText();
                String port = portTextField.getText();
                context.reactToClickedConnect(hostName, port);
            }
        });
        disconnectButton = new TextButton(Lettering.DISCONNECT, skin);
        disconnectButton.setTouchable(Touchable.disabled);
        disconnectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.reactToClickedDisconnect();
            }
        });
        playButton = new TextButton(Lettering.PLAY, skin);
        playButton.setVisible(false);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.reactToClickedPlay();
            }
        });
        closeButton = new TextButton(Lettering.CLOSE, skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable = new Table();
        mainTable.setWidth(stage.getWidth());
        mainTable.align(Align.top | Align.center);
        mainTable.setPosition(0, Gdx.graphics.getHeight());
        mainTable.padTop(100);
        mainTable.add(headingLabel).padBottom(15).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(hostNameTextField).minSize(300, 50).padRight(15);
        mainTable.add(portTextField).minSize(100, 50);
        mainTable.row();
        mainTable.add(playButton).padTop(15).padBottom(5).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(connectButton).padBottom(5).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(disconnectButton).padBottom(5).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(closeButton).padBottom(5).minSize(300, 50).colspan(2);

        stage.addActor(connStatusLabel);
        stage.addActor(versionLabel);
        stage.addActor(mainTable);

        spriteBatch = new SpriteBatch();
    }

    /**
     * This method gets called when the scene is set.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        parent.getResources().getParticleEffect().setPosition((float) stage.getViewport().getScreenWidth() / 2,
                (float) stage.getViewport().getScreenHeight() / 2);
    }

    /**
     * This method gets called in the while loop of the game.
     *
     * @param delta the time since the last render call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set label texts, button touchable and button visible according to the current state
        if (context.hasStateUpdated()) {
            State state = context.getState();
            if (state == StateContext.getDisconnectedState()) {
                connStatusLabel.setText(Lettering.DISCONNECTED);
                connectButton.setTouchable(Touchable.enabled);
                disconnectButton.setTouchable(Touchable.disabled);
                playButton.setVisible(false);
            } else if (state == StateContext.getConnectionAttemptState()) {
                connStatusLabel.setText(Lettering.CONNECTING);
                connectButton.setTouchable(Touchable.disabled);
                disconnectButton.setTouchable(Touchable.disabled);
                playButton.setVisible(false);
            } else if (state == StateContext.getConnectedState()) {
                connStatusLabel.setText(Lettering.CONNECTED);
                connectButton.setTouchable(Touchable.disabled);
                disconnectButton.setTouchable(Touchable.enabled);
                playButton.setVisible(true);
            }
        }

        // Draw background and particle effect
        spriteBatch.begin();
        spriteBatch.draw(parent.getResources().getBackground(), 0, 0, 1280, 720);
        ParticleEffect particleEffect = parent.getResources().getParticleEffect();
        if (particleEffect.isComplete()) particleEffect.reset();
        else particleEffect.draw(spriteBatch, delta);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    /**
     * This method gets called whenever the window gets resized.
     *
     * @param width  the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
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

    /**
     * This method gets called before the object gets destroyed.
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public void showConnectionError() {
        Dialog dialog2 = new Dialog(Lettering.CONNECTION_ERROR, parent.getResources().getSkin());
        dialog2.getContentTable().add(new Label("A connection error occurred.", parent.getResources().getSkin()));
        dialog2.button(Lettering.OK);
        dialog2.show(stage);
    }

    public void showServerDisconnect() {
        Dialog dialog2 = new Dialog(Lettering.SERVER_DISCONNECT, parent.getResources().getSkin());
        dialog2.getContentTable().add(new Label("A server disconnect occurred.", parent.getResources().getSkin()));
        dialog2.button(Lettering.OK);
        dialog2.show(stage);
    }
}
