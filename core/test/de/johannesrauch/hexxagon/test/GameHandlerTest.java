package de.johannesrauch.hexxagon.test;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.controller.handler.ConnectionHandler;
import de.johannesrauch.hexxagon.controller.handler.GameHandler;
import de.johannesrauch.hexxagon.model.board.Board;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.message.GameStatusMessage;
import de.johannesrauch.hexxagon.test.tools.TilesExample;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * This class tests the game handler class.
 */
public class GameHandlerTest {

    @Before
    public void setUp() {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This method tests the valid move method of the game handler.
     */
    @Test
    public void testValidMove() {
        Gson gson = new Gson();
        GameHandler gameHandler = new GameHandler();
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        gameHandler.startedGame(userId, gameId);
        Board board = gson.fromJson(TilesExample.initTiles, Board.class);
        GameStatusMessage message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                6, 6, board,
                userId, false, null);
        gameHandler.updateGame(message);

        Assert.assertTrue(gameHandler.validMove(TileEnum.TILE_22, TileEnum.TILE_21));
        Assert.assertTrue(gameHandler.validMove(TileEnum.TILE_22, TileEnum.TILE_20));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_22, TileEnum.TILE_19));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_22, TileEnum.TILE_23));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_1, TileEnum.TILE_2));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_22, TileEnum.TILE_22));

        board = gson.fromJson(TilesExample.tieTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                26, 26, board,
                userId, false, null);
        gameHandler.updateGame(message);

        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_30, TileEnum.TILE_32));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_30, TileEnum.TILE_31));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_30, TileEnum.TILE_38));
        Assert.assertFalse(gameHandler.validMove(TileEnum.TILE_30, TileEnum.TILE_40));
    }

    /**
     * This method tests the game move method of the game handler.
     */
    @Test
    public void testGameMove() {
        Gson gson = new Gson();
        GameHandler gameHandler = new GameHandler();
        gameHandler.setConnectionHandler(new ConnectionHandler());
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        gameHandler.startedGame(userId, gameId);
        Board board = gson.fromJson(TilesExample.initTiles, Board.class);
        GameStatusMessage message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                6, 6, board,
                userId, false, null);
        gameHandler.updateGame(message);

        Assert.assertTrue(gameHandler.gameMove(TileEnum.TILE_32, TileEnum.TILE_34));
        Assert.assertFalse(gameHandler.gameMove(TileEnum.TILE_32, TileEnum.TILE_33));
    }

    /**
     * This method tests the is winner me, is game over and is tie method of the game handler
     * with the board configurations from tiles example.
     */
    @Test
    public void testIsWinnerMeIsGameOverAndIsTie() {
        Gson gson = new Gson();
        GameHandler gameHandler = new GameHandler();
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        gameHandler.startedGame(userId, gameId);
        Board board = gson.fromJson(TilesExample.initTiles, Board.class);
        GameStatusMessage message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                6, 6, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertFalse(gameHandler.isWinnerMe());
        Assert.assertFalse(gameHandler.isGameOver());
        Assert.assertFalse(gameHandler.isTie());

        board = gson.fromJson(TilesExample.tieTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                26, 26, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertFalse(gameHandler.isWinnerMe());
        Assert.assertTrue(gameHandler.isGameOver());
        Assert.assertTrue(gameHandler.isTie());

        board = gson.fromJson(TilesExample.playerOneWonTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                12, 0, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertTrue(gameHandler.isWinnerMe());
        Assert.assertTrue(gameHandler.isGameOver());
        Assert.assertFalse(gameHandler.isTie());

        board = gson.fromJson(TilesExample.playerOneWonPlayerMoveImpossibleTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                27, 25, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertTrue(gameHandler.isWinnerMe());
        Assert.assertTrue(gameHandler.isGameOver());
        Assert.assertFalse(gameHandler.isTie());

        board = gson.fromJson(TilesExample.playerTwoWonTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                0, 18, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertFalse(gameHandler.isWinnerMe());
        Assert.assertTrue(gameHandler.isGameOver());
        Assert.assertFalse(gameHandler.isTie());

        board = gson.fromJson(TilesExample.playerTwoWonPlayerMoveImpossibleTiles, Board.class);
        message = new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                25, 27, board,
                userId, false, null);
        gameHandler.updateGame(message);
        Assert.assertFalse(gameHandler.isWinnerMe());
        Assert.assertTrue(gameHandler.isGameOver());
        Assert.assertFalse(gameHandler.isTie());
    }

    /**
     * This method tests the selected tile mechanic.
     */
    @Test
    public void testSelectedTile() {
        GameHandler gameHandler = new GameHandler();
        UUID userId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();
        gameHandler.startedGame(userId, gameId);
        Assert.assertNull(gameHandler.getSelectedTile());
        gameHandler.setSelectedTile(TileEnum.TILE_1);
        Assert.assertEquals(TileEnum.TILE_1, gameHandler.getSelectedTile());
        gameHandler.updateGame(new GameStatusMessage(userId, gameId,
                userId, UUID.randomUUID(),
                6, 6, null,
                userId, false, null));
        Assert.assertNull(gameHandler.getSelectedTile());
    }
}
