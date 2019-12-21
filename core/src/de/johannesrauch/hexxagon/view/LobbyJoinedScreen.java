package de.johannesrauch.hexxagon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.Hexxagon;
import de.johannesrauch.hexxagon.controller.LobbyHandler;

public class LobbyJoinedScreen implements Screen {

    private Hexxagon parent;
    private LobbyHandler lobbyHandler;

    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Table layoutTable;
    private Label lobbyLabel;

    private Label playerOneLabel;
    private Label playerTwoLabel;
    private Label playerOneUserNameLabel;
    private Label playerTwoUserNameLabel;

    private TextButton startGameButton;
    private TextButton leaveLobbyButton;

    private InputListener startGameListener;
    private InputListener leaveLobbyListener;

    public LobbyJoinedScreen(Hexxagon parent, LobbyHandler lobbyHandler) {
        this.parent = parent;
        this.lobbyHandler = lobbyHandler;

        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        startGameListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                lobbyHandler.sendStartGameMessage();
            }
        };

        leaveLobbyListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                lobbyHandler.sendLeaveLobbyMessage();
                parent.showLobbySelectScreen();
            }
        };

    }

    @Override
    public void show() {
        lobbyLabel = new Label("LOBBY", parent.skin);
        lobbyLabel.setFontScale(2, 2);
        lobbyLabel.setAlignment(Align.center);

        playerOneLabel = new Label("Player1: ", parent.skin);
        playerTwoLabel = new Label("Player2: ", parent.skin);

        playerOneUserNameLabel = new Label("", parent.skin);
        playerTwoUserNameLabel = new Label("", parent.skin);

        startGameButton = new TextButton("START GAME", parent.skin, "small");
        startGameButton.setVisible(false);
        startGameButton.addListener(startGameListener);

        leaveLobbyButton = new TextButton("LEAVE LOBBY", parent.skin, "small");
        leaveLobbyButton.addListener(leaveLobbyListener);

        layoutTable = new Table();
        layoutTable.setWidth(stage.getWidth());
        layoutTable.align(Align.top | Align.center);
        layoutTable.setPosition(0, Gdx.graphics.getHeight());
        layoutTable.padTop(30);
        layoutTable.add(lobbyLabel);
        layoutTable.row();
        layoutTable.add(playerOneLabel);
        layoutTable.row();
        layoutTable.add(playerOneUserNameLabel);
        layoutTable.row();
        layoutTable.add(playerTwoLabel);
        layoutTable.row();
        layoutTable.add(playerTwoUserNameLabel);
        layoutTable.row();
        layoutTable.add(startGameButton);
        layoutTable.row();
        layoutTable.add(leaveLobbyButton);

        stage.addActor(layoutTable);

        parent.particleEffect.start();
        parent.particleEffect.setPosition((float) viewport.getScreenWidth() / 2, (float) viewport.getScreenHeight() / 2);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // TODO: search code duplicate
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.spriteBatch.begin();
        parent.spriteBatch.draw(parent.space, 0, 0, 1024, 576);
        if (parent.particleEffect.isComplete()) parent.particleEffect.reset();
        else parent.particleEffect.draw(parent.spriteBatch, delta);
        parent.spriteBatch.end();

        if (lobbyHandler.initializationCompleted && lobbyHandler.joinedLobbyUpdated) {
            lobbyLabel.setText("LOBBY connected");

            playerOneUserNameLabel.setText(lobbyHandler.playerOneUserName);
            playerTwoUserNameLabel.setText(lobbyHandler.playerTwoUserName);

            if (lobbyHandler.clientIsPlayerOne && lobbyHandler.lobbyReady) {
                startGameButton.setVisible(true);
            } else {
                startGameButton.setVisible(false);
            }
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

}
