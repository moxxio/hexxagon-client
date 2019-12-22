package de.johannesrauch.hexxagon.view;

import java.util.HashMap;
import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.johannesrauch.hexxagon.automaton.events.BackEvent;
import de.johannesrauch.hexxagon.automaton.events.LeaveEvent;
import de.johannesrauch.hexxagon.model.GameScreenTile;
import de.johannesrauch.hexxagon.model.GameScreenTileStateEnum;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import de.johannesrauch.hexxagon.network.board.TileStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.Hexxagon;

// TODO: rescaling does fuck up hit boxes
public class GameScreen implements Screen {

    final Logger logger = LoggerFactory.getLogger(GameScreen.class);

    private final Hexxagon parent;

    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Image loadingImage;

    private Label playerOneLabel;
    private Label playerTwoLabel;
    private TextButton leaveGameButton;

    public HashMap<TileEnum, GameScreenTile> gameScreenTiles;

    boolean moveFromSelected = false;
    boolean moveToSelected = false;

    TileEnum moveFrom = null;
    TileEnum moveTo = null;

    public GameScreen(Hexxagon parent) {
        this.parent = parent;

        camera = new OrthographicCamera(1024, 576);
        viewport = new StretchViewport(1024, 576, camera);
        stage = new Stage(viewport);

        parent.particleEffect.start();
        parent.particleEffect.setPosition((float) viewport.getScreenWidth() / 2,
                (float) viewport.getScreenHeight() / 2);

        loadingImage = new Image(parent.loading);
        loadingImage.setSize(128, 128);
        loadingImage.setPosition(0, viewport.getScreenHeight() - loadingImage.getHeight());
        loadingImage.setOrigin(64, 64);

        playerOneLabel = new Label("Label1", parent.skin);
        playerOneLabel.setPosition(15, 50);
        playerTwoLabel = new Label("Label2", parent.skin);
        playerTwoLabel.setPosition(15, 25);

        leaveGameButton = new TextButton("LEAVE GAME", parent.skin, "small");
        leaveGameButton.setSize(150, 50);
        leaveGameButton.setPosition(850, 25);
        leaveGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                TextButton yesButton = new TextButton("YES", parent.skin, "small");
                TextButton noButton = new TextButton("NO", parent.skin, "small");

                Label reassureLabel = new Label("Are you sure you want to leave?", parent.skin);
                reassureLabel.setColor(Color.BLACK);

                Dialog dialog = new Dialog("", parent.skin) {
                    @Override
                    protected void result(Object object) {
                        boolean result = (boolean) object;
                        if (result) {
                            parent.getStateContext().reactOnEvent(new LeaveEvent());
                        }
                    }
                };

                dialog.getContentTable().pad(15);
                dialog.getContentTable().add(reassureLabel);
                dialog.button(yesButton, true);
                dialog.button(noButton, false);
                dialog.show(stage);
            }
        });

        gameScreenTiles = new HashMap<TileEnum, GameScreenTile>();
        initGameScreenTiles();

        stage.addActor(loadingImage);
        stage.addActor(playerOneLabel);
        stage.addActor(playerTwoLabel);
        stage.addActor(leaveGameButton);
    }

    @Override
    public void show() {
        resetGameScreenTiles();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.spriteBatch.setProjectionMatrix(camera.combined);
        parent.spriteBatch.begin();
        parent.spriteBatch.draw(parent.space, 0, 0, 1024, 576);
        if (parent.particleEffect.isComplete()) parent.particleEffect.reset();
        else parent.particleEffect.draw(parent.spriteBatch, delta);
        parent.spriteBatch.end();

        if (loadingImage != null) loadingImage.rotateBy(delta * -90.0f);

        GameHandler gameHandler = parent.getGameHandler();
        if (gameHandler.isInitComplete()) {


            stage.act();
            stage.draw();
        }
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
        // TODO: geben Sie alle Ressourcen mit implementiertem Disposable Interface frei
    }

    /**
     * This methods gets called by the constructor and initializes the game screen tiles.
     * It mainly sets the right positions of the game screen tiles and constructs the gameScreenTiles hash map.
     *
     * @author Dennis Jehle
     * @author Johannes Rauch
     */
    private void initGameScreenTiles() {
        for (int i = 0; i < 61; i++) {
            final int index = i + 1;

            int startPosX = 512;
            int startPosY = 576;

            int sizeX = 96;
            int sizeY = 64;

            int offsetX = 80;

            startPosX -= sizeX / 2;
            startPosY -= sizeY;

            int posX, posY;

            if (i < 5) {
                posX = startPosX - offsetX * 4;
                posY = startPosY - 2 * sizeY - i * sizeY;
            } else if (i < 11) {
                posX = startPosX - offsetX * 3;
                posY = startPosY - sizeY - sizeY / 2 - (i - 5) * sizeY;
            } else if (i < 18) {
                posX = startPosX - offsetX * 2;
                posY = startPosY - sizeY - (i - 11) * sizeY;
            } else if (i < 26) {
                posX = startPosX - offsetX;
                posY = startPosY - sizeY / 2 - (i - 18) * sizeY;
            } else if (i < 35) {
                posX = startPosX;
                posY = startPosY - (i - 26) * sizeY;
            } else if (i < 43) {
                posX = startPosX + offsetX;
                posY = startPosY - sizeY / 2 - (i - 35) * sizeY;
            } else if (i < 50) {
                posX = startPosX + offsetX * 2;
                posY = startPosY - sizeY - (i - 43) * sizeY;
            } else if (i < 56) {
                posX = startPosX + offsetX * 3;
                posY = startPosY - sizeY - sizeY / 2 - (i - 50) * sizeY;
            } else {
                posX = startPosX + offsetX * 4;
                posY = startPosY - 2 * sizeY - (i - 56) * sizeY;
            }

            Image image = new Image();
            image.setPosition(posX, posY);
            image.setSize(sizeX, sizeY);
            image.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("Touched tile " + index);
                }
            });
            stage.addActor(image);

            GameScreenTile gameScreenTile = new GameScreenTile(image, null);
            gameScreenTiles.put(TileEnum.valueOf("Tile_" + index), gameScreenTile);
        }
    }

    /**
     * This methods gets called by show. It resets the tiles to the free tile image and state.
     *
     * @author Johannes Rauch
     */
    private void resetGameScreenTiles() {
        for (int i = 1; i <= 61; i++) {
            TileEnum tile = TileEnum.valueOf("Tile_" + i);
            GameScreenTile gameScreenTile = gameScreenTiles.get(tile);

            if (gameScreenTile != null) {
                gameScreenTile.setDrawableOfImage(new SpriteDrawable(new Sprite(parent.tileFree)));
                gameScreenTile.setTileState(GameScreenTileStateEnum.Free);
            }
        }
    }
}
