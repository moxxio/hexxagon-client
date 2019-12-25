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
// TODO: move valid game move check here
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

    private Board getBoard() {
        return gameStatus.getBoard();
    }

    public int getMyPlayerNumber() {
        if (gameStatus == null || userId == null) return -1;
        if (userId.equals(gameStatus.getPlayerOne())) return 1;
        if (userId.equals(gameStatus.getPlayerTwo())) return 2;
        return -2;
    }

    public List<TileEnum> getNeighborsOf(TileEnum tile) {
        return boardGraph.getNeighborsOf(tile);
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
        return gameStatus.isTie()
                || gameStatus.getPlayerOne().equals(gameStatus.getWinner())
                || gameStatus.getPlayerTwo().equals(gameStatus.getWinner())
                || gameStatus.getPlayerOnePoints() == 0
                || gameStatus.getPlayerTwoPoints() == 0
                || isPlayerMoveImpossible(1)
                || isPlayerMoveImpossible(2);
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

    private boolean isPlayerMoveImpossible(int playerNumber) {
        for (TileEnum tile : TileEnum.values()) {
            TileStateEnum tileState = getTileState(tile);
            if (!((playerNumber == 1 && tileState == TileStateEnum.PLAYERONE)
                    || (playerNumber == 2 && tileState == TileStateEnum.PLAYERTWO))) continue;

            List<TileEnum> neighbors = getNeighborsOf(tile);
            for (TileEnum otherTile : neighbors) {
                TileStateEnum otherTileState = getTileState(otherTile);
                if (otherTileState == TileStateEnum.FREE) return false;

                List<TileEnum> otherNeighbors = getNeighborsOf(otherTile);
                for (TileEnum anotherTile : otherNeighbors) {
                    TileStateEnum anotherTileState = getTileState(anotherTile);
                    if (anotherTileState == TileStateEnum.FREE) return false;
                }
            }
        }
        return true;
    }

    public boolean isTie() {
        if (gameStatus == null) return false;
        return gameStatus.isTie()
                || (gameStatus.getPlayerOnePoints() != -1
                && gameStatus.getPlayerOnePoints() == gameStatus.getPlayerTwoPoints()
                && isGameOver());
    }

    public boolean isTileSelected() {
        return selectedTile != null;
    }

    public boolean isWinnerMe() {
        if (gameStatus == null || userId == null) return false;
        return userId.equals(gameStatus.getWinner())
                || (getMyPlayerNumber() == 1 && getPlayerTwoPoints() == 0)
                || (getMyPlayerNumber() == 2 && getPlayerOnePoints() == 0)
                || getMyPlayerNumber() == 1 && isPlayerMoveImpossible(2)
                || getMyPlayerNumber() == 2 && isPlayerMoveImpossible(1);
    }

    public void leaveGame() {
        if (gameId != null) {
            logger.info("Left game: " + gameId.toString());
            parent.getMessageEmitter().sendLeaveGameMessage(gameId);
        } else logger.warn("GameId is null in leaveGame()!");
        reset();
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
