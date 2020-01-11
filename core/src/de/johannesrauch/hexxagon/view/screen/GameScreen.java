package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.listener.LeaveButtonClickListener;
import de.johannesrauch.hexxagon.controller.listener.TileClickListener;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.model.tile.GameScreenTile;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;
import de.johannesrauch.hexxagon.view.label.ButtonStyleLabel;
import de.johannesrauch.hexxagon.view.label.TextFieldStyleLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the game screen.
 */
public class GameScreen extends BaseScreen {

    private ButtonStyleLabel playerOneLabel;
    private ButtonStyleLabel playerTwoLabel;
    private TextFieldStyleLabel playerOneUserNameLabel;
    private TextFieldStyleLabel playerTwoUserNameLabel;
    private TextFieldStyleLabel playerOneScoreLabel;
    private TextFieldStyleLabel playerTwoScoreLabel;
    private ButtonStyleLabel turnLabel;
    private TextFieldStyleLabel winnerLabel;

    private TextButton leaveButton;

    private Map<TileEnum, GameScreenTile> gameScreenTiles;

    private SpriteBatch spriteBatch;

    private final GameHandler gameHandler;

    /**
     * This constructor sets everything up.
     *
     * @param parent the parent
     */
    public GameScreen(Hexxagon parent, StateContext context, GameHandler gameHandler) {
        super(parent);
        Skin skin = parent.getResources().getSkin();
        this.gameHandler = gameHandler;

        playerOneLabel = new ButtonStyleLabel(Lettering.PLAYER_ONE, skin);
        playerOneLabel.setPosition(20, 80);
        playerOneLabel.setSize(300, 50);
        playerTwoLabel = new ButtonStyleLabel(Lettering.PLAYER_TWO, skin);
        playerTwoLabel.setPosition(960, 80);
        playerTwoLabel.setSize(300, 50);
        playerOneUserNameLabel = new TextFieldStyleLabel(Lettering.PLAYER_ONE_USERNAME, skin);
        playerOneUserNameLabel.setPosition(20, 20);
        playerOneUserNameLabel.setSize(300, 50);
        playerTwoUserNameLabel = new TextFieldStyleLabel(Lettering.PLAYER_TWO_USERNAME, skin);
        playerTwoUserNameLabel.setPosition(960, 20);
        playerTwoUserNameLabel.setSize(300, 50);
        playerOneScoreLabel = new TextFieldStyleLabel(Lettering.SCORE, skin);
        playerOneScoreLabel.setPosition(340, 20);
        playerOneScoreLabel.setSize(50, 50);
        playerTwoScoreLabel = new TextFieldStyleLabel(Lettering.SCORE, skin);
        playerTwoScoreLabel.setPosition(890, 20);
        playerTwoScoreLabel.setSize(50, 50);
        turnLabel = new ButtonStyleLabel(Lettering.TURN, skin);
        turnLabel.setPosition(20, 660);
        turnLabel.setSize(300, 50);
        winnerLabel = new TextFieldStyleLabel(Lettering.WHO_WON, skin);
        winnerLabel.setPosition(20, 600);
        winnerLabel.setSize(300, 50);
        winnerLabel.setVisible(false);

        leaveButton = new TextButton(Lettering.LEAVE, skin);
        leaveButton.setPosition(960, 660);
        leaveButton.setSize(300, 50);
        leaveButton.addListener(new LeaveButtonClickListener(context, stage, skin));

        gameScreenTiles = new HashMap<>();
        setupTiles();

        stage.addActor(playerOneLabel);
        stage.addActor(playerTwoLabel);
        stage.addActor(playerOneUserNameLabel);
        stage.addActor(playerTwoUserNameLabel);
        stage.addActor(playerOneScoreLabel);
        stage.addActor(playerTwoScoreLabel);
        stage.addActor(turnLabel);
        stage.addActor(winnerLabel);
        stage.addActor(leaveButton);

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

        // Draw game board and get player information
        if (gameHandler != null && gameHandler.isGameUpdated()) {
            playerOneUserNameLabel.setText(gameHandler.getPlayerOneUserName());
            playerTwoUserNameLabel.setText(gameHandler.getPlayerTwoUserName());
            playerOneScoreLabel.setText("" + gameHandler.getPlayerOnePoints());
            playerTwoScoreLabel.setText("" + gameHandler.getPlayerTwoPoints());
            turnLabel.setText(gameHandler.getWhoseTurnDesc());

            if (gameHandler.isGameOver()) {
                if (gameHandler.isTie()) winnerLabel.setText(Lettering.TIE);
                else if (gameHandler.isWinnerMe()) winnerLabel.setText(Lettering.WINNER);
                else winnerLabel.setText(Lettering.LOSER);
                winnerLabel.setVisible(true);
            }

            for (TileEnum tile : TileEnum.values()) {
                TileStateEnum tileState = gameHandler.getTileState(tile);
                if (tileState == TileStateEnum.PLAYERONE)
                    gameScreenTiles.get(tile).setTexture(parent.getResources().getTilePlayerOne());
                else if (tileState == TileStateEnum.PLAYERTWO)
                    gameScreenTiles.get(tile).setTexture(parent.getResources().getTilePlayerTwo());
                else if (tileState == TileStateEnum.BLOCKED)
                    gameScreenTiles.get(tile).setTexture(parent.getResources().getTileBlocked());
                else
                    gameScreenTiles.get(tile).setTexture(parent.getResources().getTileFree());
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
     * This method returns the game screen tile map.
     *
     * @return the game screen tile map
     */
    public Map<TileEnum, GameScreenTile> getGameScreenTiles() {
        return gameScreenTiles;
    }

    /**
     * This method constructs a tile enum from integer.
     *
     * @param i the integer from which the tile gets constructed
     * @return the tile enum
     */
    private TileEnum getTileEnumFromInt(int i) {
        return TileEnum.valueOf("TILE_" + i);
    }

    /**
     * This methods resets the tile textures, the label names and the winner label.
     */
    private void reset() {
        for (TileEnum tile : TileEnum.values()) {
            gameScreenTiles.get(tile).setTexture(parent.getResources().getTileFree());
        }
        playerOneUserNameLabel.setText(Lettering.PLAYER_ONE_USERNAME);
        playerTwoUserNameLabel.setText(Lettering.PLAYER_TWO_USERNAME);
        playerOneScoreLabel.setText(Lettering.SCORE);
        playerTwoScoreLabel.setText(Lettering.SCORE);
        turnLabel.setText(Lettering.TURN);
        winnerLabel.setText(Lettering.WHO_WON);
        winnerLabel.setVisible(false);
    }

    /**
     * This method sets up the positions for all tiles.
     */
    private void setupTiles() {
        for (int i = 0; i < 61; i++) {
            final int index = i + 1;
            int startPosX = 640;
            int startPosY = 690;
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
                posY = startPosY - sizeY - sizeY / 2 - (i - 50) * sizeY - 3 * offsetY / 2 - (i - 50) * offsetY;
            } else {
                posX = startPosX + offsetX * 4;
                posY = startPosY - 2 * sizeY - (i - 56) * sizeY - 2 * offsetY - (i - 56) * offsetY;
            }

            TileEnum tile = getTileEnumFromInt(index);
            GameScreenTile gameScreenTile = new GameScreenTile(parent.getResources().getTileFree());
            gameScreenTile.setPosition(posX, posY);
            gameScreenTile.setSize(sizeX, sizeY);
            gameScreenTile.addListener(new TileClickListener(parent, this, gameHandler, tile, gameScreenTile));

            gameScreenTiles.put(tile, gameScreenTile);
            stage.addActor(gameScreenTile);
        }
    }
}
