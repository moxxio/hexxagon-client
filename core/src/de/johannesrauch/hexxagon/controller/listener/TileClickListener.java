package de.johannesrauch.hexxagon.controller.listener;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.fsm.state.InGameMyTurnState;
import de.johannesrauch.hexxagon.fsm.state.State;
import de.johannesrauch.hexxagon.model.tile.GameScreenTile;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * This class implements the reaction to a click of the user on a game board tile.
 */
public class TileClickListener extends ClickListener {

    private final Hexxagon parent;

    private final Logger logger;

    private TileEnum tile;
    private GameScreenTile gameScreenTile;

    /**
     * TODO: remove parent and set the actual stuff this class needs
     * This is the standard constructor. It sets the parent and the tile and the game screen tile this listener listens to.
     *
     * @param parent         the parent
     * @param tile           the tile which is listened to
     * @param gameScreenTile the game screen tile which is listened to
     */
    public TileClickListener(Hexxagon parent, TileEnum tile, GameScreenTile gameScreenTile) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(TileClickListener.class);
        this.tile = tile;
        this.gameScreenTile = gameScreenTile;
    }

    /**
     * This method determines the reaction to the user clicking a tile of the playing board.
     *
     * @param event the input event
     * @param x     the x coordinate of the click
     * @param y     the y coordinate of the click
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {
        GameHandler gameHandler = parent.getGameHandler();

        // If it is not the player's turn or the player has moved in this round, ignore the click event
        if (!gameHandler.isMyTurn() || gameHandler.hasMoved()) return;

        // If something is wrong with the player number, then ignore the click event
        int playerNumber = gameHandler.getMyPlayerNumber();
        if (playerNumber != 1 && playerNumber != 2) {
            logger.warn("Player number is not one or two!");
            return;
        }

        // If the player clicked on a blocked or a opponent's tile, then ignore the click event
        TileStateEnum tileState = gameHandler.getTileState(tile);
        if (tileState == TileStateEnum.BLOCKED
                || (playerNumber == 1 && tileState == TileStateEnum.PLAYERTWO)
                || (playerNumber == 2 && tileState == TileStateEnum.PLAYERONE)) {
            return;
        }

        // If no tile is selected or the clicked tile is my tile, then choose a new selected tile
        TileEnum selectedTile = gameHandler.getSelectedTile();
        if (!gameHandler.isTileSelected() || gameHandler.getTileState(selectedTile) == tileState) {
            // If the clicked tile is a free tile, then ignore the click event
            if (tileState == TileStateEnum.FREE) return;

            Map<TileEnum, GameScreenTile> gameScreenTiles = parent.getGameScreen().getGameScreenTiles();
            Texture tileTexture = playerNumber == 1 ?
                    parent.getResources().getTilePlayerOne() : parent.getResources().getTilePlayerTwo();

            // Reset textures of all game screen tiles
            for (TileEnum otherTile : TileEnum.values()) {
                TileStateEnum otherTileState = gameHandler.getTileState(otherTile);
                if (otherTileState == tileState)
                    gameScreenTiles.get(otherTile).setTexture(tileTexture);
                else if (otherTileState == TileStateEnum.FREE)
                    gameScreenTiles.get(otherTile).setTexture(parent.getResources().getTileFree());
            }

            // Set texture of the selected and adjacent tiles
            gameScreenTile.setTexture(playerNumber == 1 ?
                    parent.getResources().getTilePlayerOneSelected() : parent.getResources().getTilePlayerTwoSelected());
            List<TileEnum> neighbors = gameHandler.getNeighborsOf(tile);
            for (TileEnum otherTile : neighbors) {
                TileStateEnum otherTileState = gameHandler.getTileState(otherTile);
                if (otherTileState == TileStateEnum.FREE)
                    gameScreenTiles.get(otherTile).setTexture(parent.getResources().getTileMoveOne());

                List<TileEnum> otherNeighbors = gameHandler.getNeighborsOf(otherTile);
                for (TileEnum anotherTile : otherNeighbors) {
                    TileStateEnum anotherTileState = gameHandler.getTileState(anotherTile);
                    if (anotherTileState == TileStateEnum.FREE && !neighbors.contains(anotherTile))
                        gameScreenTiles.get(anotherTile).setTexture(parent.getResources().getTileMoveTwo());
                }
            }

            gameHandler.setSelectedTile(tile);
        } else { // Otherwise check if it is a valid move
            if (gameHandler.validMove(selectedTile, tile)) {
                gameHandler.gameMove(selectedTile, tile);
            }
        }
    }
}
