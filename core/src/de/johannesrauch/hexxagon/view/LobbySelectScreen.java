package de.johannesrauch.hexxagon.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.johannesrauch.hexxagon.network.lobby.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.Hexxagon;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.controller.MessageEmitter;

public class LobbySelectScreen implements Screen {

    final Logger logger = LoggerFactory.getLogger(LobbySelectScreen.class);

    private Hexxagon parent;
    private LobbyHandler lobbyHandler;
    private MessageEmitter messageEmitter;

    private HashMap<String, UUID> lobbyIds = new HashMap<String, UUID>();

    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Table layoutTable;
    private Table buttonTable;
    private Table userNameTable;
    private Table progressBarTable;

    private Label lobbyLabel;
    private Label userNameLabel;
    private Label progressBarLabel;

    public TextField userNameTextField;

    private Button joinLobbyButton;
    private Button createLobbyButton;
    private Button refreshButton;
    private Button backButton;
    private Button stopJoiningButton;

    private ProgressBar progressBar;

    private ScrollPane scrollPane;

    private List<String> lobbyGdxList;

    public LobbySelectScreen(Hexxagon parent, LobbyHandler lobbyHandler, MessageEmitter messageEmitter) {
        this.parent = parent;
        this.lobbyHandler = lobbyHandler;
        this.messageEmitter = messageEmitter;

        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        layoutTable = new Table();
        layoutTable.setWidth(stage.getWidth());
        layoutTable.align(Align.top | Align.center);
        layoutTable.setPosition(0, Gdx.graphics.getHeight());

        progressBarTable = new Table();
        progressBarTable.setWidth(stage.getWidth());
        progressBarTable.align(Align.top | Align.center);
        progressBarTable.setPosition(0, Gdx.graphics.getHeight());

        buttonTable = new Table();
        userNameTable = new Table();

        lobbyLabel = new Label("CHOOSE LOBBY", parent.skin);
        lobbyLabel.setFontScale(2, 2);
        lobbyLabel.setAlignment(Align.center);

        progressBarLabel = new Label("JOINING LOBBY ...", parent.skin);
        progressBarLabel.setFontScale(2, 2);
        progressBarLabel.setAlignment(Align.center);

        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, parent.skin);

        userNameLabel = new Label("User name: ", parent.skin);

        java.util.zip.Adler32 a32 = new java.util.zip.Adler32();
        a32.update(UUID.randomUUID().toString().getBytes());

        userNameTextField = new TextField("P" + a32.getValue(), parent.skin);

        joinLobbyButton = new TextButton("JOIN", parent.skin, "small");
        //joinLobbyButton.setTouchable(Touchable.disabled);
        joinLobbyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                String selectedLobby = lobbyGdxList.getSelected();
                if (selectedLobby == null) return;

                logger.info(selectedLobby);

                UUID lobbyId = lobbyIds.get(selectedLobby);

                if (lobbyId == null) return;

                logger.info(lobbyId.toString());

                String userName = userNameTextField.getText();

                if (userName == null) return;

                messageEmitter.sendJoinLobbyMessage(lobbyId, userName);

                showProgressBar();
            }
        });

        stopJoiningButton = new TextButton("CANCEL", parent.skin, "small");
        stopJoiningButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hideProgressBar();
            }
        });

        createLobbyButton = new TextButton("CREATE", parent.skin, "small");
        createLobbyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                TextButton createButton = new TextButton("CREATE", parent.skin, "small");
                TextButton cancelButton = new TextButton("CANCEL", parent.skin, "small");

                Label lobbyNameLabel = new Label("Lobby name:", parent.skin);
                lobbyNameLabel.setColor(Color.BLACK);

                java.util.zip.Adler32 a32 = new java.util.zip.Adler32();
                a32.update(UUID.randomUUID().toString().getBytes());

                TextField lobbyNameTextField = new TextField("LOBBY" + a32.getValue(), parent.skin);

                Dialog dialog = new Dialog("CREATE LOBBY", parent.skin) {
                    @Override
                    protected void result(Object object) {
                        boolean result = (boolean) object;
                        if (result) {
                            messageEmitter.sendCreateNewLobbyMessage(lobbyNameTextField.getText());
                        }
                    }
                };
                dialog.getContentTable().pad(15);
                dialog.getContentTable().add(lobbyNameLabel);
                dialog.getContentTable().add(lobbyNameTextField).width(500);
                dialog.button(createButton, true);
                dialog.button(cancelButton, false);
                dialog.show(stage);
            }
        });

        refreshButton = new TextButton("REFRESH", parent.skin, "small");
        refreshButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                messageEmitter.sendGetAvailableLobbiesMessage();
            }
        });

        backButton = new TextButton("BACK", parent.skin, "small");
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    parent.showMainMenuScreen();
                } catch (NullPointerException npe) {
                    logger.error("NullPointerException: " + npe.getMessage());
                }
            }
        });

        lobbyGdxList = new List<>(parent.skin);

        scrollPane = new ScrollPane(lobbyGdxList);
        scrollPane.setScrollbarsOnTop(true);
        scrollPane.setScrollbarsVisible(true);

        buttonTable.padLeft(15);
        buttonTable.add(joinLobbyButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(createLobbyButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(refreshButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(backButton).padBottom(5).minSize(100, 50);

        userNameTable.add(userNameLabel);
        userNameTable.add(userNameTextField).minWidth(300);

        layoutTable.padTop(30);
        layoutTable.add(lobbyLabel).padBottom(15).colspan(2);
        layoutTable.row();
        layoutTable.add(userNameTable).align(Align.left).padBottom(15).colspan(2);
        layoutTable.row();
        layoutTable.add(scrollPane).padBottom(15).minSize(500, 300);
        layoutTable.add(buttonTable).maxSize(200, 50).align(Align.center);

        stage.addActor(layoutTable);

        progressBarTable.padTop(30);
        progressBarTable.add(progressBarLabel).padBottom(15);
        progressBarTable.row();
        progressBarTable.add(progressBar).minSize(600, 50);
        progressBarTable.row();
        progressBarTable.add(stopJoiningButton).minSize(100, 50).padBottom(5);
        progressBarTable.setVisible(false);

        stage.addActor(progressBarTable);
    }

    public void showProgressBar() {
        layoutTable.setVisible(false);
        progressBarTable.setVisible(true);
        progressBar.setAnimateDuration(10.0f);
        progressBar.setValue(1.0f);
    }

    public void hideProgressBar(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        } finally {
            hideProgressBar();
        }
    }

    public void hideProgressBar() {
        layoutTable.setVisible(true);
        progressBarTable.setVisible(false);
        progressBar.setAnimateDuration(0.0f);
        progressBar.setValue(0.0f);
    }

    public void updateAvailableLobbies(ArrayList<Lobby> availableLobbies) {
        lobbyIds.clear();
        lobbyGdxList.clearItems();
        ArrayList<String> stringList = new ArrayList<>();
        availableLobbies.forEach((lobby) -> {
            String hashString = lobby.lobbyName + lobby.lobbyId.toString();

            java.util.zip.Adler32 a32 = new java.util.zip.Adler32();
            a32.update(hashString.getBytes());

            String lobbyString = lobby.lobbyName + " [" + a32.getValue() + "]";

            stringList.add(lobbyString);
            lobbyIds.put(lobbyString, lobby.lobbyId);
        });
        String[] stringArray = stringList.toArray(new String[0]);
        lobbyGdxList.setItems(stringArray);
    }

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

        if (lobbyHandler.availableLobbiesUpdated) {
            updateAvailableLobbies(lobbyHandler.availableLobbies);
            lobbyHandler.availableLobbiesUpdated = false;
        }

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
