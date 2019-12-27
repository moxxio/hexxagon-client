package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;
import de.johannesrauch.hexxagon.view.label.TextFieldStyleLabel;

// TODO: make progress bar for uninitialized game state
public class LobbyScreen extends BaseScreen {

    private Label headingLabel;
    private ButtonStyleLabel playerOneLabel;
    private ButtonStyleLabel playerTwoLabel;
    private TextFieldStyleLabel playerOneUserNameLabel;
    private TextFieldStyleLabel playerTwoUserNameLabel;

    private TextButton startButton;
    private TextButton leaveButton;

    private Table mainTable;

    private SpriteBatch spriteBatch;

    public LobbyScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();

        headingLabel = new Label("LOBBY", skin, "title");
        playerOneLabel = new ButtonStyleLabel("PLAYER ONE: ", skin);
        playerTwoLabel = new ButtonStyleLabel("PLAYER TWO: ", skin);
        playerOneUserNameLabel = new TextFieldStyleLabel("<PLAYER ONE USER NAME>", skin);
        playerTwoUserNameLabel = new TextFieldStyleLabel("<PLAYER TWO USER NAME>", skin);

        startButton = new TextButton("START", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getLobbyHandler().startGame();
            }
        });
        leaveButton = new TextButton("LEAVE", skin);
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getContext().reactToClickedLeave();
            }
        });

        mainTable = new Table();
        mainTable.setWidth(stage.getWidth());
        mainTable.align(Align.top | Align.center);
        mainTable.setPosition(0, Gdx.graphics.getHeight());
        mainTable.padTop(100);
        mainTable.add(headingLabel).padBottom(15).minSize(300, 50).colspan(2);
        mainTable.row();
        mainTable.add(playerOneLabel).padBottom(15).minSize(300, 50);
        mainTable.add(playerOneUserNameLabel).padBottom(15).minSize(300, 50);
        mainTable.row();
        mainTable.add(playerTwoLabel).padBottom(15).minSize(300, 50);
        mainTable.add(playerTwoUserNameLabel).padBottom(15).minSize(300, 50);
        mainTable.row();
        mainTable.add(startButton).padBottom(15).minSize(300, 50).padRight(5);
        mainTable.add(leaveButton).padBottom(15).minSize(300, 50);

        stage.addActor(mainTable);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        reset();

        parent.getResources().getParticleEffect().setPosition((float) stage.getViewport().getScreenWidth() / 2,
                (float) stage.getViewport().getScreenHeight() / 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        LobbyHandler lobbyHandler = parent.getLobbyHandler();
        if (lobbyHandler.isLobbyUpdated()) {
            startButton.setVisible(lobbyHandler.isClientPlayerOne() && lobbyHandler.isLobbyReady());
            playerOneUserNameLabel.setText(lobbyHandler.getPlayerOneUserName());
            playerTwoUserNameLabel.setText(lobbyHandler.getPlayerTwoUserName());
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

    }

    private void reset() {
        startButton.setVisible(false);
        playerOneUserNameLabel.setText("<PLAYER ONE USER NAME>");
        playerTwoUserNameLabel.setText("<PLAYER ONE USER NAME>");
    }
}
