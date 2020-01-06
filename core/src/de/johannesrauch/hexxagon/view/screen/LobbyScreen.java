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
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;
import de.johannesrauch.hexxagon.view.label.TextFieldStyleLabel;

/**
 * This class is the lobby screen.
 */
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

    private final StateContext context;
    private final LobbyHandler lobbyHandler;

    /**
     * This constructor sets everything up.
     *
     * @param parent the parent
     */
    public LobbyScreen(Hexxagon parent, StateContext context, LobbyHandler lobbyHandler) {
        super(parent);
        Skin skin = parent.getResources().getSkin();
        this.context = context;
        this.lobbyHandler = lobbyHandler;

        headingLabel = new Label(Lettering.LOBBY, skin, "title");
        playerOneLabel = new ButtonStyleLabel(Lettering.PLAYER_ONE, skin);
        playerTwoLabel = new ButtonStyleLabel(Lettering.PLAYER_TWO, skin);
        playerOneUserNameLabel = new TextFieldStyleLabel(Lettering.PLAYER_ONE_USERNAME, skin);
        playerTwoUserNameLabel = new TextFieldStyleLabel(Lettering.PLAYER_TWO_USERNAME, skin);

        startButton = new TextButton(Lettering.START, skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lobbyHandler.startGame();
            }
        });
        leaveButton = new TextButton(Lettering.LEAVE, skin);
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.reactToClickedLeave();
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

    /**
     * This method gets called when the scene is set.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        reset();

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

    /**
     * This method resets the labels.
     */
    private void reset() {
        startButton.setVisible(false);
        playerOneUserNameLabel.setText(Lettering.PLAYER_ONE_USERNAME);
        playerTwoUserNameLabel.setText(Lettering.PLAYER_TWO_USERNAME);
    }
}
