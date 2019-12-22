package de.johannesrauch.hexxagon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.LobbyHandler;
import de.johannesrauch.hexxagon.model.lobby.Lobby;
import de.johannesrauch.hexxagon.state.event.BackEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.Adler32;

public class SelectLobbyScreen extends BaseScreen {

    private Label headingLabel;
    private Label userNameLabel;

    private TextField userNameTextField;

    private TextButton joinButton;
    private TextButton createButton;
    private TextButton refreshButton;
    private TextButton backButton;

    private List<String> lobbyList;
    private HashMap<String, UUID> lobbyIds;
    private ScrollPane scrollPane;

    private Table userNameTable;
    private Table buttonTable;
    private Table mainTable;

    public SelectLobbyScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();
        Adler32 a32 = new Adler32();
        a32.update(UUID.randomUUID().toString().getBytes());

        headingLabel = new Label("CHOOSE LOBBY", skin);
        userNameLabel = new Label("USERNAME: ", skin);

        userNameTextField = new TextField("PLAYER" + a32.getValue(), skin);

        joinButton = new TextButton("JOIN", skin);
        createButton = new TextButton("CREATE", skin);
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label lobbyNameLabel = new Label("TYPE LOBBY NAME: ", skin);

                TextButton createButton = new TextButton("CREATE", skin);
                TextButton cancelButton = new TextButton("CANCEL", skin);

                a32.update(UUID.randomUUID().toString().getBytes());
                TextField lobbyNameTextField = new TextField("LOBBY" + a32.getValue(), skin);

                Dialog dialog = new Dialog("CREATE LOBBY", skin) {
                    @Override
                    protected void result(Object object) {
                        boolean result = (boolean) object;
                        if (result) {
                            parent.getMessageEmitter().sendCreateNewLobbyMessage(lobbyNameTextField.getText());
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
        refreshButton = new TextButton("REFRESH", skin);
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getMessageEmitter().sendGetAvailableLobbiesMessage();
            }
        });
        backButton = new TextButton("BACK", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.getContext().reactOnEvent(new BackEvent());
            }
        });

        lobbyList = new List<>(skin);
        lobbyIds = new HashMap<>();
        scrollPane = new ScrollPane(lobbyList);
        scrollPane.setScrollbarsOnTop(true);

        userNameTable = new Table();
        userNameTable.add(userNameLabel);
        userNameTable.add(userNameTextField).minWidth(300);

        buttonTable = new Table();
        buttonTable.padLeft(15);
        buttonTable.add(joinButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(createButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(refreshButton).padBottom(5).minSize(100, 50);
        buttonTable.row();
        buttonTable.add(backButton).padBottom(5).minSize(100, 50);

        mainTable = new Table();
        mainTable.setWidth(stage.getWidth());
        mainTable.align(Align.top | Align.center);
        mainTable.setPosition(0, Gdx.graphics.getHeight());
        mainTable.padTop(30);
        mainTable.add(headingLabel).padBottom(15).colspan(2);
        mainTable.row();
        mainTable.add(userNameTable).align(Align.left).padBottom(15).colspan(2);
        mainTable.row();
        mainTable.add(scrollPane).padBottom(15).minSize(500, 300);
        mainTable.add(buttonTable).maxSize(200, 50).align(Align.center);

        stage.addActor(mainTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        LobbyHandler lobbyHandler = parent.getLobbyHandler();
        if (lobbyHandler.areLobbiesUpdated()) {
            lobbyIds.clear();
            lobbyList.clearItems();

            java.util.List<String> tmp = new ArrayList<>();
            java.util.List<Lobby> lobbies = lobbyHandler.getAvailableLobbies();
            lobbies.forEach((lobby) -> {
                Adler32 a32 = new Adler32();
                a32.update((lobby.getLobbyName() + lobby.getLobbyId().toString()).getBytes());

                String lobbyId = lobby.getLobbyName() + "[" + a32.getValue() + "]";
                tmp.add(lobbyId);
                lobbyIds.put(lobbyId, lobby.getLobbyId());
            });

            lobbyList.setItems(tmp.toArray(new String[0]));
        }

        stage.act(delta);
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

    }
}
