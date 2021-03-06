package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.controller.listener.CreateButtonClickListener;
import de.johannesrauch.hexxagon.controller.listener.JoinButtonClickListener;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.fsm.state.State;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.Adler32;

/**
 * This class represents the select lobby screen.
 */
public class SelectLobbyScreen extends BaseScreen {

    private Label headingLabel;
    private ButtonStyleLabel userNameLabel;
    private Label joiningLabel;

    private TextField userNameTextField;

    private TextButton joinButton;
    private TextButton createButton;
    private TextButton refreshButton;
    private TextButton backButton;
    private TextButton cancelButton;

    private ProgressBar progressBar;

    private List<String> lobbyList;
    private HashMap<String, UUID> lobbyIds;
    private ScrollPane scrollPane;

    private Table userNameTable;
    private Table buttonTable;
    private Table mainTable;
    private Table joiningTable;

    private SpriteBatch spriteBatch;

    private final StateContext context;
    private final LobbyHandler lobbyHandler;

    /**
     * This constructor sets everything up.
     *
     * @param parent the parent
     */
    public SelectLobbyScreen(Hexxagon parent, StateContext context, ConnectionHandler connectionHandler, LobbyHandler lobbyHandler) {
        super(parent);
        Skin skin = parent.getResources().getSkin();
        Adler32 a32 = new Adler32();
        MessageEmitter messageEmitter = connectionHandler.getMessageEmitter();
        this.context = context;
        this.lobbyHandler = lobbyHandler;
        lobbyList = new List<>(skin);
        lobbyIds = new HashMap<>();

        headingLabel = new Label(Lettering.CHOOSE_LOBBY, skin, "title");
        userNameLabel = new ButtonStyleLabel(Lettering.USERNAME, skin);
        joiningLabel = new Label(Lettering.JOINING_LOBBY, skin, "title");

        a32.update(UUID.randomUUID().toString().getBytes());
        userNameTextField = new TextField("P" + a32.getValue(), skin);
        userNameTextField.setAlignment(Align.center);

        joinButton = new TextButton(Lettering.JOIN, skin);
        joinButton.addListener(new JoinButtonClickListener(context, stage, skin, lobbyList, lobbyIds, userNameTextField));
        createButton = new TextButton(Lettering.CREATE, skin);
        createButton.addListener(new CreateButtonClickListener(stage, skin, messageEmitter));
        refreshButton = new TextButton(Lettering.REFRESH, skin);
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageEmitter.sendGetAvailableLobbiesMessage();
            }
        });
        backButton = new TextButton(Lettering.BACK, skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.reactToClickedBack();
            }
        });
        cancelButton = new TextButton(Lettering.CANCEL, skin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.reactToClickedLeave();
            }
        });

        scrollPane = new ScrollPane(lobbyList);
        scrollPane.setScrollbarsOnTop(true);

        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);

        userNameTable = new Table();
        userNameTable.align(Align.center);
        userNameTable.add(userNameLabel).minSize(300, 50).padRight(15);
        userNameTable.add(userNameTextField).minSize(300, 50);

        buttonTable = new Table();
        buttonTable.padLeft(15);
        buttonTable.add(joinButton).padBottom(5).minSize(300, 50);
        buttonTable.row();
        buttonTable.add(createButton).padBottom(5).minSize(300, 50);
        buttonTable.row();
        buttonTable.add(refreshButton).padBottom(5).minSize(300, 50);
        buttonTable.row();
        buttonTable.add(backButton).padBottom(5).minSize(300, 50);

        mainTable = new Table();
        mainTable.setWidth(stage.getWidth());
        mainTable.align(Align.top | Align.center);
        mainTable.setPosition(0, Gdx.graphics.getHeight());
        mainTable.padTop(100);
        mainTable.add(headingLabel).padBottom(15).colspan(2);
        mainTable.row();
        mainTable.add(userNameTable).align(Align.center).padBottom(15).colspan(2);
        mainTable.row();
        mainTable.add(scrollPane).padBottom(15).minSize(600, 400);
        mainTable.add(buttonTable).maxSize(300, 50).align(Align.center);

        joiningTable = new Table();
        joiningTable.setWidth(stage.getWidth());
        joiningTable.align(Align.top | Align.center);
        joiningTable.setPosition(0, Gdx.graphics.getHeight());
        joiningTable.padTop(100);
        joiningTable.add(joiningLabel).padBottom(15).colspan(2);
        joiningTable.row();
        joiningTable.add(progressBar).minSize(600, 50);
        joiningTable.row();
        joiningTable.add(cancelButton).minSize(300, 50).padBottom(5);
        joiningTable.setVisible(false);

        stage.addActor(mainTable);
        stage.addActor(joiningTable);

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

        // Update lobby list if lobbies have been updated
        if (lobbyHandler.areLobbiesUpdated()) {
            lobbyIds.clear();
            lobbyList.clearItems();

            java.util.List<String> tmp = new ArrayList<>();
            java.util.List<Lobby> lobbies = lobbyHandler.getAvailableLobbies();
            lobbies.forEach(lobby -> {
                Adler32 a32 = new Adler32();
                a32.update((lobby.getLobbyName() + lobby.getLobbyId().toString()).getBytes());

                String lobbyId = lobby.getLobbyName() + "[" + a32.getValue() + "]";
                tmp.add(lobbyId);
                lobbyIds.put(lobbyId, lobby.getLobbyId());
            });

            lobbyList.setItems(tmp.toArray(new String[0]));
        }

        if (context.hasStateUpdated()) {
            State state = context.getState();
            if (state == StateContext.getJoiningLobbyState()) {
                showProgressBar();
            } else if (state == StateContext.getSelectLobbyState()) {
                hideProgressBar();
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
        // The game is never paused.
    }

    @Override
    public void resume() {
        // Since the game is never paused it never gets resumed.
    }

    @Override
    public void hide() {
        // No action on hide.
    }

    /**
     * This method gets called before the object gets destroyed.
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    /**
     * This method shows the progress bar and hides the main table.
     */
    public void showProgressBar() {
        mainTable.setVisible(false);
        joiningTable.setVisible(true);
        progressBar.setAnimateDuration(10.0f);
        progressBar.setValue(1.0f);
    }

    /**
     * This method waits the in millis specified time and then calls hide progress bar.
     *
     * @param millis the time this methods waits in millis
     */
    public void hideProgressBar(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        } finally {
            hideProgressBar();
        }
    }

    /**
     * This method hides the progress bar.
     */
    public void hideProgressBar() {
        joiningTable.setVisible(false);
        mainTable.setVisible(true);
        progressBar.setAnimateDuration(0.0f);
        progressBar.setValue(0.0f);
    }
}
