package de.johannesrauch.hexxagon.view;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import de.johannesrauch.hexxagon.automaton.events.LeaveEvent;
import de.johannesrauch.hexxagon.model.GameScreenTile;
import de.johannesrauch.hexxagon.network.board.TileEnum;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.Hexxagon;

public class GameScreen implements Screen {

    final Logger logger = LoggerFactory.getLogger(GameScreen.class);

    private final Hexxagon parent;

    private OrthographicCamera camera;
    private StretchViewport viewport;
    private Stage stage;

    private Image loadingImage;

    private Label playerOneLabel;
    private Label playerTwoLabel;
    private Label leaveGameLabel;

    public HashMap<TileEnum, GameScreenTile> gameScreenTiles;

    boolean moveFromSelected = false;
    boolean moveToSelected = false;

    TileEnum moveFrom = null;
    TileEnum moveTo = null;

    boolean gameHandlerInitializationCompleted = false;

    // TODO: entfernen Sie dieses Attribut, es dient nur dazu, dass Sie initial ein Spielfeld angezeigt bekommen
    //       Diese Funktionalität werden Sie nun selbst schreiben.
    boolean debug = true;

    public GameScreen(Hexxagon parent, GameHandler gameHandler) {
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

        playerOneLabel = new Label("", parent.skin);
        playerTwoLabel = new Label("", parent.skin);
        leaveGameLabel = new Label("", parent.skin);

        gameScreenTiles = new HashMap<TileEnum, GameScreenTile>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Keys.F10)) {
            logger.info("F10 was pressed, leaving game now.");
            parent.getStateContext().reactOnEvent(new LeaveEvent());
        }

        parent.spriteBatch.setProjectionMatrix(camera.combined);
        parent.spriteBatch.begin();
        parent.spriteBatch.draw(parent.space, 0, 0, 1024, 576);
        if (parent.particleEffect.isComplete()) parent.particleEffect.reset();
        else parent.particleEffect.draw(parent.spriteBatch, delta);
        parent.spriteBatch.end();

        if (loadingImage != null) loadingImage.rotateBy(delta * -90.0f);

		GameHandler gameHandler = parent.getGameHandler();
        // Falls die Initialisierung des GameHandlers nicht abgeschlossen ist, aber die Initialisierung des GameScreens abgeschlossen ist, so ist etwas falsch gelaufen.
        // In diesem Fall werden Aufräumarbeiten durchgeführt.
        if (!gameHandler.isInitComplete() && gameHandlerInitializationCompleted) {
            logger.error("GameScreen initialization states not matching.");
            gameHandlerInitializationCompleted = false;
        }
        // Falls die Inititalisierugn des GameHandlers abgeschlossen ist, jedoch die des GameScreens nicht, wird der GameScreen initialisiert. 
        else if (gameHandler.isInitComplete() && !gameHandlerInitializationCompleted || debug) {
            // initialize gameScreenTiles
            initializeGameScreenTiles();
            // change gameHandlerInitializationCompleted to true
            gameHandlerInitializationCompleted = true;

            debug = false;
        }
        // Das Spielfeld wird gerendert, falls alle Initialisierungen abgeschlossen sind.
        else if (gameHandler.isInitComplete() && gameHandlerInitializationCompleted || !debug) {

            // show or hide loading animation
            if (gameHandler.isMyTurn()) {
                if (loadingImage.isVisible()) {
                    loadingImage.setVisible(false);
                }
            } else {
                if (!loadingImage.isVisible()) {
                    loadingImage.setVisible(true);
                }
            }

            // TODO: Überprüfen Sie, ob sich am Zustand des Spielfelds etwas geändert hat
            //       Falls eine Änderung vorliegt, aktuallisieren Sie die Kacheln in der Stage
            //       danach soll der aktuelle Zustand des Spielfeldes gezeichnet werden.

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

    private void initializeGameScreenTiles() {
        stage.clear();
        gameScreenTiles.clear();

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

            image.setDrawable(new SpriteDrawable(new Sprite(parent.tileFree)));
            image.addListener(new ClickListener() {
                                  @Override
                                  public void clicked(InputEvent event, float x, float y) {
                                      System.out.println("You clicked an image: " + index);

                                      // TODO: nachdem auf eine Kachel geklickt wurde, sollte hier etwas sinnvolles passieren
                                  }
                              }
            );

            // TODO: hier sollte die Kachel dahingehend angepasst werden, dass der richtige Kachelzustand gesetzt ist
            //       Kachel frei, Kachel blockiert, Kachel belegt durch Spielstein Spieler 1/2

            stage.addActor(image);
        }


        // TODO: zeigen Sie für Spieler 1 den Benutzernamen und die Punktezahl an
        playerOneLabel.setText("USERNAME1" + ": " + "XYZ" + " points");
        playerOneLabel.setPosition(15, 50);

        // TODO: zeigen Sie für Spieler 2 den Benutzernamen und die Punktezahl an
        playerTwoLabel.setText("USERNAME2" + ": " + "XYZ" + " points");
        playerTwoLabel.setPosition(15, 25);

        leaveGameLabel.setText("[F10] LEAVE GAME");
        leaveGameLabel.setPosition(850, 25);

        stage.addActor(playerOneLabel);
        stage.addActor(playerTwoLabel);
        stage.addActor(leaveGameLabel);

        stage.addActor(loadingImage);


    }

}
