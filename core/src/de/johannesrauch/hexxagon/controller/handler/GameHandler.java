package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.model.board.Board;
import de.johannesrauch.hexxagon.model.board.BoardGraph;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;
import de.johannesrauch.hexxagon.network.message.GameStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

// TODO: check for win, tie or lose (since the fucking server does not do it although it should)
public class GameHandler {

    private final Hexxagon parent;

    private final Logger logger;

    private GameStatusMessage gameStatus;
    private boolean gameUpdated;
    private BoardGraph boardGraph;

    private UUID userId;
    private UUID gameId;

    private TileEnum selectedTile;
    private boolean moved;

    public GameHandler(Hexxagon parent) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(GameHandler.class);
        boardGraph = new BoardGraph();
        reset();
    }

    public void gameMove(TileEnum moveFrom, TileEnum moveTo) {
        parent.getMessageEmitter().sendGameMoveMessage(gameId, moveFrom, moveTo);
    }

    public Board getBoard() {
        return gameStatus.getBoard();
    }

    public int getMyPlayerNumber() {
        if (gameStatus == null || userId == null) return -1;
        if (userId.equals(gameStatus.getPlayerOne())) return 1;
        if (userId.equals(gameStatus.getPlayerTwo())) return 2;
        return -2;
    }

    public int getPlayerOnePoints() {
        if (gameStatus == null) return -1;
        return gameStatus.getPlayerOnePoints();
    }

    public int getPlayerTwoPoints() {
        if (gameStatus == null) return -1;
        return gameStatus.getPlayerTwoPoints();
    }

    public String getPlayerOneUserName() {
        if (gameStatus == null) return "";
        if (gameStatus.getPlayerOneUserName() == null) return "";
        return gameStatus.getPlayerOneUserName();
    }

    public String getPlayerTwoUserName() {
        if (gameStatus == null) return "";
        if (gameStatus.getPlayerTwoUserName() == null) return "";
        return gameStatus.getPlayerTwoUserName();
    }

    public TileEnum getSelectedTile() {
        return selectedTile;
    }

    public TileStateEnum getTileState(TileEnum tile) {
        return getBoard().getTileState(tile);
    }

    public String getWhoseTurn() {
        if (gameStatus == null || userId == null) return "";
        return userId.equals(gameStatus.getActivePlayer()) ? "MY TURN" : "OPPONENT'S TURN";
    }

    public boolean hasMoved() {
        return moved;
    }

    public boolean isGameOver() {
        if (gameStatus == null || gameStatus.getPlayerOne() == null || gameStatus.getPlayerTwo() == null) return false;
        if (gameStatus.isTie()) return true;
        return gameStatus.getPlayerOne().equals(gameStatus.getWinner())
                || gameStatus.getPlayerTwo().equals(gameStatus.getWinner());
    }

    public boolean isGameUpdated() {
        if (gameUpdated) {
            gameUpdated = false;
            return true;
        }
        return false;
    }

    public boolean isMyTurn() {
        if (gameStatus == null || userId == null) return false;
        return userId.equals(gameStatus.getActivePlayer());
    }

    public boolean isTie() {
        if (gameStatus == null) return false;
        return gameStatus.isTie();
    }

    public boolean isTileSelected() {
        return selectedTile != null;
    }

    public boolean isWinnerMe() {
        if (gameStatus == null || userId == null) return false;
        return userId.equals(gameStatus.getWinner());
    }

    public void leaveGame() {
        if (gameId != null) {
            logger.info("Left game: " + gameId.toString());
            parent.getMessageEmitter().sendLeaveGameMessage(gameId);
        } else logger.warn("GameId is null in leaveGame()!");
        reset();
    }

    public List<TileEnum> getNeighborsOf(TileEnum tile) {
        return boardGraph.getNeighborsOf(tile);
    }

    public void reset() {
        gameStatus = null;
        userId = null;
        gameId = null;
        gameUpdated = false;
        selectedTile = null;
        moved = false;
    }

    public void setSelectedTile(TileEnum tile) {
        selectedTile = tile;
    }

    public void startedGame(UUID userId, UUID gameId) {
        logger.info("Started game: " + gameId.toString());
        this.userId = userId;
        this.gameId = gameId;
    }

    public void updateGame(GameStatusMessage gameStatus) {
        logger.info("Game update: " + gameStatus.toString());
        this.gameStatus = gameStatus;
        gameUpdated = true;
        selectedTile = null;
        moved = false;
    }
}
