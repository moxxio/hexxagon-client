package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.model.board.Board;
import de.johannesrauch.hexxagon.model.board.BoardGraph;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;
import de.johannesrauch.hexxagon.network.message.GameStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * This class handles the game state and provides helpful methods to determine the game end,
 * the winner, valid moves and other things.
 */
public class GameHandler {

    private final Logger logger = LoggerFactory.getLogger(GameHandler.class);

    private GameStatusMessage gameStatus;
    private boolean gameUpdated;
    private BoardGraph boardGraph;

    private UUID userId;
    private UUID gameId;

    private TileEnum selectedTile;
    private boolean moved;

    private ConnectionHandler connectionHandler;

    /**
     * This is the standard constructor. It constructs the board graph and resets the game handler.
     */
    public GameHandler() {
        boardGraph = new BoardGraph();
        reset();
    }

    /**
     * This method sends a game move message, if the move is valid.
     *
     * @param moveFrom the tile that gets moved
     * @param moveTo   the tile where move from gets moved
     * @return true, if the game move was valid and sent, false otherwise
     */
    public boolean gameMove(TileEnum moveFrom, TileEnum moveTo) {
        if (connectionHandler != null && validMove(moveFrom, moveTo)) {
            connectionHandler.getMessageEmitter().sendGameMoveMessage(gameId, moveFrom, moveTo);
            return true;
        } else {
            logger.warn("MessageEmitter is null in gameMove(...)!");
            return false;
        }
    }

    /**
     * This method returns the current board state.
     *
     * @return the board
     */
    private Board getBoard() {
        return gameStatus.getBoard();
    }

    /**
     * This method returns the player number of the user.
     * If the game status or the user id is null it returns -1.
     * If the user is neither player one or two it returns -2.
     *
     * @return 1 or 2, if the user is player one or two respectively, -1 if game status or user uuid is null, -2 if the user is neither player one or two
     */
    public int getMyPlayerNumber() {
        if (gameStatus == null || userId == null) return -1;
        if (userId.equals(gameStatus.getPlayerOne())) return 1;
        if (userId.equals(gameStatus.getPlayerTwo())) return 2;
        return -2;
    }

    /**
     * This method returns the neighbors of a specific tile.
     *
     * @param tile the tile you want the neighbors of
     * @return the neighbors of the given tile
     */
    public List<TileEnum> getNeighborsOf(TileEnum tile) {
        return boardGraph.getNeighborsOf(tile);
    }

    /**
     * This methods returns the points of player one.
     *
     * @return the points of player one or -1, if the game status is null
     */
    public int getPlayerOnePoints() {
        if (gameStatus == null) return -1;
        return gameStatus.getPlayerOnePoints();
    }

    /**
     * The method returns the points of player two.
     *
     * @return the points of player two or -1, if the game status is null
     */
    public int getPlayerTwoPoints() {
        if (gameStatus == null) return -1;
        return gameStatus.getPlayerTwoPoints();
    }

    /**
     * This method returns the player one username. If the lobby or the username is null, it returns an empty string.
     *
     * @return the player one username or "", if there is no player one username
     */
    public String getPlayerOneUserName() {
        if (gameStatus == null) return "";
        if (gameStatus.getPlayerOneUserName() == null) return "";
        return gameStatus.getPlayerOneUserName();
    }

    /**
     * This method returns the player two username. If the lobby or the username is null, it returns an empty string.
     *
     * @return the player two username or "", if there is no player two username
     */
    public String getPlayerTwoUserName() {
        if (gameStatus == null) return "";
        if (gameStatus.getPlayerTwoUserName() == null) return "";
        return gameStatus.getPlayerTwoUserName();
    }

    /**
     * This method returns the tile the user selected or null, if none was selected.
     *
     * @return the selected tile
     */
    public TileEnum getSelectedTile() {
        return selectedTile;
    }

    /**
     * This method returns the tile state of a specific tile.
     *
     * @param tile the tile you want the tile state of
     * @return the tile state of the given tile
     */
    public TileStateEnum getTileState(TileEnum tile) {
        return getBoard().getTileState(tile);
    }

    /**
     * This method returns a string specifying, which turn it is. If the turn is not determinable,
     * it returns the empty string.
     *
     * @return MY TURN, if it is the user's turn, OPPONENT'S TURN, if it is the opponent's turn, "", if game status or user uuid is null
     */
    public String getWhoseTurnDesc() {
        if (gameStatus == null || userId == null) return "";
        return userId.equals(gameStatus.getActivePlayer()) ? "MY TURN" : "OPPONENT'S TURN";
    }

    /**
     * This method returns whose turn it is, player one's or player two's.
     *
     * @return 1, if it is player one's turn, 2 if it is player two's, -1, if game status or active player is null, -2 if the active player is neither one or two
     */
    public int getWhoseTurn() {
        if (gameStatus == null || gameStatus.getActivePlayer() == null) return -1;
        if (gameStatus.getActivePlayer().equals(gameStatus.getPlayerOne())) return 1;
        if (gameStatus.getActivePlayer().equals(gameStatus.getPlayerTwo())) return 2;
        return -2;
    }

    /**
     * This method returns whether the user has already moved in his turn.
     *
     * @return true, if the user has moved, false otherwise
     */
    public boolean hasMoved() {
        return moved;
    }

    /**
     * This method determines, if the game is over. It checks the game status message for a tie or
     * a specified winner. Furthermore, it checks if the score of one player is equal to zero or if
     * one player cannot move anymore.
     *
     * @return true, if the game is over, false otherwise
     */
    public boolean isGameOver() {
        if (gameStatus == null || gameStatus.getPlayerOne() == null || gameStatus.getPlayerTwo() == null) return false;
        return gameStatus.isTie()
                || gameStatus.getPlayerOne().equals(gameStatus.getWinner())
                || gameStatus.getPlayerTwo().equals(gameStatus.getWinner())
                || gameStatus.getPlayerOnePoints() == 0
                || gameStatus.getPlayerTwoPoints() == 0
                || (getWhoseTurn() == 1 && isPlayerMoveImpossible(1))
                || (getWhoseTurn() == 2 && isPlayerMoveImpossible(2));
    }

    /**
     * This method returns whether the game has been updated since the last check.
     *
     * @return true, if the game has been updated, false otherwise
     */
    public boolean isGameUpdated() {
        if (gameUpdated) {
            gameUpdated = false;
            return true;
        }
        return false;
    }

    /**
     * This method returns whether it is the turn of this user. If the game status or user uuid is null,
     * it returns false.
     *
     * @return true, if it is the user's turn, false otherwise.
     */
    public boolean isMyTurn() {
        if (gameStatus == null || userId == null) return false;
        return userId.equals(gameStatus.getActivePlayer());
    }

    /**
     * This method returns whether the player with the specified number can move or not.
     * If a number different from one or two is given, this method returns false.
     *
     * @param playerNumber 1 for player one, 2 for player two
     * @return whether the given player can move
     */
    private boolean isPlayerMoveImpossible(int playerNumber) {
        if (playerNumber != 1 && playerNumber != 2) return false;
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

    /**
     * This method checks if the game ended with a tie.
     *
     * @return true, if the game is a tie, false otherwise
     */
    public boolean isTie() {
        if (gameStatus == null) return false;
        return gameStatus.isTie()
                || (gameStatus.getPlayerOnePoints() != -1
                && gameStatus.getPlayerOnePoints() == gameStatus.getPlayerTwoPoints()
                && isGameOver()
                && gameStatus.getWinner() == null);
    }

    /**
     * This method returns whether a tile was already selected by the user.
     *
     * @return true, if a tile was selected, false otherwise
     */
    public boolean isTileSelected() {
        return selectedTile != null;
    }

    /**
     * This method checks, if the user is the winner. If the game status or the user id is null,
     * it returns false.
     *
     * @return true, if the user is the winner, false otherwise.
     */
    public boolean isWinnerMe() {
        if (gameStatus == null || userId == null) return false;
        return userId.equals(gameStatus.getWinner())
                || (getMyPlayerNumber() == 1 && getPlayerTwoPoints() == 0)
                || (getMyPlayerNumber() == 2 && getPlayerOnePoints() == 0)
                || getMyPlayerNumber() == 1 && getPlayerOnePoints() > getPlayerTwoPoints()
                || getMyPlayerNumber() == 2 && getPlayerOnePoints() < getPlayerTwoPoints();
    }

    /**
     * This method leaves the game. It sends a game leave message and resets the game handler.
     */
    public void leaveGame() {
        if (gameId != null) {
            logger.info("Left game: " + gameId.toString());
            if (connectionHandler != null) connectionHandler.getMessageEmitter().sendLeaveGameMessage(gameId);
            else logger.warn("MessageEmitter is null in leaveGame()!");
        } else logger.warn("GameId is null in leaveGame()!");
        reset();
    }

    /**
     * This method resets the game handler. It sets the game status, user uuid, game uuid and selected tile
     * to null and the game updated and moved flag to false.
     */
    public void reset() {
        gameStatus = null;
        userId = null;
        gameId = null;
        gameUpdated = false;
        selectedTile = null;
        moved = false;
    }

    /**
     * This method sets the connection handler.
     *
     * @param connectionHandler the connection handler
     */
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    /**
     * This method sets the selected tile.
     *
     * @param tile the selected tile
     */
    public void setSelectedTile(TileEnum tile) {
        selectedTile = tile;
    }

    /**
     * This method informs the game handler about a game started message.
     * It sets the user uuid and the game uuid.
     *
     * @param userId the user uuid of the game started message
     * @param gameId the game uuid of the game started message
     */
    public void startedGame(UUID userId, UUID gameId) {
        logger.info("Started game: " + gameId.toString());
        this.userId = userId;
        this.gameId = gameId;
    }

    /**
     * This method updates the game status and sets the game updated flag.
     *
     * @param gameStatus the game status message
     */
    public void updateGame(GameStatusMessage gameStatus) {
        logger.info("Game update: " + gameStatus.toString());
        this.gameStatus = gameStatus;
        gameUpdated = true;
        selectedTile = null;
        moved = false;
    }

    /**
     * This method checks if the move the player wants to do is valid. It should be called before the
     * game move method.
     *
     * @param from the tile that gets moved
     * @param to   the tile where the from tile should get moved
     * @return true, if it is a valid move, false otherwise
     */
    public boolean validMove(TileEnum from, TileEnum to) {
        if (!isMyTurn() || hasMoved()) return false;

        List<TileEnum> fromNeighbors = getNeighborsOf(from);
        for (TileEnum otherTile : fromNeighbors) {
            if (to == otherTile) return true;

            List<TileEnum> otherNeighbors = getNeighborsOf(otherTile);
            for (TileEnum anotherTile : otherNeighbors) {
                if (to == anotherTile) return true;
            }
        }
        return false;
    }
}
