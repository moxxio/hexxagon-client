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
import de.johannesrauch.hexxagon.state.event.ConnectEvent;
import de.johannesrauch.hexxagon.state.event.DisconnectEvent;
import de.johannesrauch.hexxagon.state.event.SearchGameEvent;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;

public class MainMenuScreen extends BaseScreen {

    private Label headingLabel;
    private ButtonStyleLabel connStatusLabel;

    private TextField hostNameTextField;
    private TextField portTextField;

    private TextButton connectButton;
    private TextButton disconnectButton;
    private TextButton playButton;
    private TextButton closeButton;

    private Table mainTable;

    private SpriteBatch spriteBatch;

    public MainMenuScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();

        headingLabel = new Label("HEXXAGON", skin, "title");
        connStatusLabel = new ButtonStyleLabel("DISCONNECTED", skin);

        hostNameTextField = new TextField("localhost", skin);
        hostNameTextField.setAlignment(Align.center);
        portTextField = new TextField("4444", skin);
        portTextField.setAlignment(Align.center);

        connectButton = new TextButton("CONNECT", skin);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String hostName = hostNameTextField.getText();
                String port = portTextField.getText();
                parent.getContext().reactOnEvent(new ConnectEvent(hostName, port));
            }
        });
        disconnectButton = new TextButton("DISCONNECT", skin);
        disconnectButton.setTouchable(Touchable.disabled);
        disconnectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getContext().reactOnEvent(new DisconnectEvent());
            }
        });
        playButton = new TextButton("PLAY", skin);
        playButton.setVisible(false);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getContext().reactOnEvent(new SearchGameEvent());
            }
        });
        closeButton = new TextButton("CLOSE", skin);
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
        mainTable.add(connStatusLabel).padBottom(5).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(closeButton).padBottom(5).minSize(300, 50).colspan(2);

        stage.addActor(mainTable);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        parent.getResources().getParticleEffect().setPosition((float) stage.getViewport().getScreenWidth() / 2,
                (float) stage.getViewport().getScreenHeight() / 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    @Override
    public void dispose() {
        // TODO: free resources
    }

    public void setConnStatusLabel(String text) {
        connStatusLabel.setText(text);
    }

    public void setConnectButtonTouchable(boolean touchable) {
        if (touchable) connectButton.setTouchable(Touchable.enabled);
        else connectButton.setTouchable(Touchable.disabled);
    }

    public void setDisconnectButtonTouchable(boolean touchable) {
        if (touchable) disconnectButton.setTouchable(Touchable.enabled);
        else disconnectButton.setTouchable(Touchable.disabled);
    }

    public void setSearchButtonVisible(boolean visible) {
        playButton.setVisible(visible);
    }
}
