package de.johannesrauch.hexxagon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import de.johannesrauch.hexxagon.Hexxagon;

public class LobbyScreen extends BaseScreen {

    private Label headingLabel;
    private Label playerOneLabel;
    private Label playerTwoLabel;
    private Label playerOneUserNameLabel;
    private Label playerTwoUserNameLabel;

    private TextButton startButton;
    private TextButton leaveButton;

    private Table mainTable;

    public LobbyScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();

        headingLabel = new Label("LOBBY", skin);
        playerOneLabel = new Label("PLAYER ONE: ", skin);
        playerTwoLabel = new Label("PLAYER TWO: ", skin);
        playerOneUserNameLabel = new Label("<PLAYER ONE USER NAME>", skin);
        playerTwoUserNameLabel = new Label("<PLAYER TWO USER NAME>", skin);

        startButton = new TextButton("START", skin);
        leaveButton = new TextButton("LEAVE", skin);

        mainTable = new Table();
        mainTable.setWidth(stage.getWidth());
        mainTable.align(Align.top | Align.center);
        mainTable.setPosition(0, Gdx.graphics.getHeight());
        mainTable.padTop(30);
        mainTable.add(headingLabel).padBottom(15).minSize(200, 50).colspan(2);
        mainTable.row();
        mainTable.add(playerOneLabel).padBottom(15).minSize(200, 50);
        mainTable.add(playerOneUserNameLabel).padBottom(15).minSize(200, 50);
        mainTable.row();
        mainTable.add(playerTwoLabel).padBottom(15).minSize(200, 50);
        mainTable.add(playerTwoUserNameLabel).padBottom(15).minSize(200, 50);
        mainTable.row();
        mainTable.add(startButton).padBottom(15).minSize(200, 50);
        mainTable.add(leaveButton).padBottom(15).minSize(200, 50);

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

        // TODO: retrieve player names

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
