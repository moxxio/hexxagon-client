package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.controller.listener.TileClickListener;
import de.johannesrauch.hexxagon.model.tile.GameScreenTile;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;
import de.johannesrauch.hexxagon.state.event.LeaveEvent;

import java.util.HashMap;
import java.util.Map;

public class GameScreen extends BaseScreen {

    private Label playerOneLabel;
    private Label playerTwoLabel;
    private Label playerOneUserNameLabel;
    private Label playerTwoUserNameLabel;
    private Label playerOnePointsLabel;
    private Label playerTwoPointsLabel;
    private Label playerOneScoreLabel;
    private Label playerTwoScoreLabel;
    private Label turnLabel;
    private Label winnerLabel;

    private TextButton leaveButton;

    private Map<TileEnum, GameScreenTile> gameScreenTiles;

    private SpriteBatch spriteBatch;

    public GameScreen(Hexxagon parent) {
        super(parent);
        Skin skin = parent.getResources().getSkin();

        playerOneLabel = new Label("PLAYER ONE: ", skin);
        playerOneLabel.setPosition(20, 40);
        playerTwoLabel = new Label("PLAYER TWO: ", skin);
        playerTwoLabel.setPosition(760, 40);
        playerOneUserNameLabel = new Label("<PLAYER ONE USER NAME>", skin);
        playerOneUserNameLabel.setPosition(205, 40);
        playerTwoUserNameLabel = new Label("<PLAYER ONE USER NAME>", skin);
        playerTwoUserNameLabel.setPosition(945, 40);
        playerOnePointsLabel = new Label("POINTS: ", skin);
        playerOnePointsLabel.setPosition(20, 20);
        playerTwoPointsLabel = new Label("POINTS: ", skin);
        playerTwoPointsLabel.setPosition(760, 20);
        playerOneScoreLabel = new Label("<SCORE>", skin);
        playerOneScoreLabel.setPosition(205, 20);
        playerTwoScoreLabel = new Label("<SCORE>", skin);
        playerTwoScoreLabel.setPosition(945, 20);
        turnLabel = new Label("<WHOSE TURN>", skin);
        turnLabel.setPosition(20, 690);
        winnerLabel = new Label("<WHO WON>", skin);
        winnerLabel.setPosition(20, 670);
        winnerLabel.setVisible(false);

        leaveButton = new TextButton("LEAVE", skin);
        leaveButton.setPosition(1115, 660);
        leaveButton.setSize(150, 50);
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label reassureLabel = new Label("ARE YOU SURE YOU WANT TO LEAVE?", skin);
                TextButton yesButton = new TextButton("YES", skin);
                TextButton noButton = new TextButton("NO", skin);

                Dialog dialog = new Dialog("LEAVE GAME", skin) {
                    @Override
                    protected void result(Object object) {
                        boolean result = (boolean) object;
                        if (result) {
                            parent.getContext().reactOnEvent(new LeaveEvent());
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

        gameScreenTiles = new HashMap<>();
        setupTiles();

        stage.addActor(playerOneLabel);
        stage.addActor(playerTwoLabel);
        stage.addActor(playerOneUserNameLabel);
        stage.addActor(playerTwoUserNameLabel);
        stage.addActor(playerOnePointsLabel);
        stage.addActor(playerTwoPointsLabel);
        stage.addActor(playerOneScoreLabel);
        stage.addActor(playerTwoScoreLabel);
        stage.addActor(turnLabel);
        stage.addActor(winnerLabel);
        stage.addActor(leaveButton);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        reset();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Game board
        GameHandler gameHandler = parent.getGameHandler();
        if (gameHandler.isGameUpdated()) {
            playerOneUserNameLabel.setText(gameHandler.getPlayerOneUserName());
            playerTwoUserNameLabel.setText(gameHandler.getPlayerTwoUserName());
            playerOneScoreLabel.setText(gameHandler.getPlayerOnePoints());
            playerTwoScoreLabel.setText(gameHandler.getPlayerTwoPoints());
            turnLabel.setText(gameHandler.getWhoseTurn());

            if (gameHandler.isGameOver()) {
                if (gameHandler.isTie()) winnerLabel.setText("TIE");
                else if (gameHandler.isWinnerMe()) winnerLabel.setText("WINNER");
                else winnerLabel.setText("LOSER");
                winnerLabel.setVisible(true);
            }

            // TODO: check null pointers
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

    public Map<TileEnum, GameScreenTile> getGameScreenTiles() {
        return gameScreenTiles;
    }

    private TileEnum getTileEnumFromInt(int i) {
        return TileEnum.valueOf("TILE_" + i);
    }

    private void reset() {
        for (TileEnum tile : TileEnum.values()) {
            gameScreenTiles.get(tile).setTexture(parent.getResources().getTileFree());
        }
        playerOneUserNameLabel.setText("<PLAYER ONE USER NAME>");
        playerTwoUserNameLabel.setText("<PLAYER TWO USER NAME>");
        playerOneScoreLabel.setText("<SCORE>");
        playerTwoScoreLabel.setText("<SCORE>");
        turnLabel.setText("<WHOSE TURN>");
        winnerLabel.setText("<WHO WON>");
        winnerLabel.setVisible(false);
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
                posY = startPosY - sizeY - sizeY / 2 - (i - 50) * sizeY - 3 * offsetY / 2 - (i - 50) * offsetY;
                ;
            } else {
                posX = startPosX + offsetX * 4;
                posY = startPosY - 2 * sizeY - (i - 56) * sizeY - 2 * offsetY - (i - 56) * offsetY;
            }

            TileEnum tile = getTileEnumFromInt(i + 1);
            GameScreenTile gameScreenTile = new GameScreenTile(parent.getResources().getTileFree());
            gameScreenTile.setPosition(posX, posY);
            gameScreenTile.setSize(sizeX, sizeY);
            gameScreenTile.addListener(new TileClickListener(parent, tile, gameScreenTile));

            gameScreenTiles.put(tile, gameScreenTile);
            stage.addActor(gameScreenTile);
        }
    }
}
