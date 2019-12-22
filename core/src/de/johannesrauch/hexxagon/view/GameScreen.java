package de.johannesrauch.hexxagon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.model.tile.GameScreenTile;

public class GameScreen extends BaseScreen {

    private Label playerOneLabel;
    private Label playerTwoLabel;

    private TextButton leaveButton;

    private GameScreenTile[] gameScreenTiles;

    public GameScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();

        // TODO: get the right values
        playerOneLabel = new Label("USERNAME 1: POINTS", skin);
        playerOneLabel.setSize(150, 50);
        playerOneLabel.setPosition(15, 660);
        playerTwoLabel = new Label("USERNAME 2: Points", skin);
        playerTwoLabel.setSize(150, 50);
        playerTwoLabel.setPosition(15, 630);

        leaveButton = new TextButton("LEAVE", skin);
        leaveButton.setPosition(1115, 660);
        leaveButton.setSize(150, 50);

        gameScreenTiles = new GameScreenTile[61];
        setupTiles();

        stage.addActor(playerOneLabel);
        stage.addActor(playerTwoLabel);
        stage.addActor(leaveButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    private void setupTiles() {
        for (int i = 0; i < 61; i++) {
            final int index = i + 1;
            int startPosX = 640;
            int startPosY = 700;
            int sizeX = 96;
            int sizeY = 64;
            int offsetX = 90;
            int offsetY = 10;
            startPosX -= sizeX / 2;
            startPosY -= sizeY;
            int posX, posY;

            if (i < 5) {
                posX = startPosX - offsetX * 4;
                posY = startPosY - 2 * sizeY - i * sizeY - 2 * offsetY - i * offsetY;
            } else if (i < 11) {
                posX = startPosX - offsetX * 3;
                posY = startPosY - sizeY - sizeY / 2 - (i - 5) * sizeY - 3 * offsetY / 2 - (i - 5) * offsetY;
            } else if (i < 18) {
                posX = startPosX - offsetX * 2;
                posY = startPosY - sizeY - (i - 11) * sizeY - offsetY - (i - 11) * offsetY;
            } else if (i < 26) {
                posX = startPosX - offsetX;
                posY = startPosY - sizeY / 2 - (i - 18) * sizeY - offsetY / 2 - (i - 18) * offsetY;
            } else if (i < 35) {
                posX = startPosX;
                posY = startPosY - (i - 26) * sizeY - (i - 26) * offsetY;
            } else if (i < 43) {
                posX = startPosX + offsetX;
                posY = startPosY - sizeY / 2 - (i - 35) * sizeY - offsetY / 2 - (i - 35) * offsetY;
            } else if (i < 50) {
                posX = startPosX + offsetX * 2;
                posY = startPosY - sizeY - (i - 43) * sizeY - offsetY - (i - 43) * offsetY;
            } else if (i < 56) {
                posX = startPosX + offsetX * 3;
                posY = startPosY - sizeY - sizeY / 2 - (i - 50) * sizeY - 3 * offsetY / 2 - (i - 50) * offsetY;;
            } else {
                posX = startPosX + offsetX * 4;
                posY = startPosY - 2 * sizeY - (i - 56) * sizeY - 2 * offsetY - (i - 56) * offsetY;
            }

            GameScreenTile tile = new GameScreenTile(parent.getResources().getTileFree());
            tile.setPosition(posX, posY);
            tile.setSize(sizeX, sizeY);
            tile.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: actually handle event
                    System.out.println("CLICKED " + index);
                }
            });

            gameScreenTiles[i] = tile;
            stage.addActor(tile);
        }
    }
}
