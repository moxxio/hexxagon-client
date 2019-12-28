package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.model.board.Board;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.Date;
import java.util.UUID;

/**
 * This class represents the game status message.
 */
public class GameStatusMessage extends AbstractMessage {

    private UUID gameId;
    private UUID playerOne;
    private UUID playerTwo;
    private String playerOneUserName;
    private String playerTwoUserName;
    private boolean playerOneLeft;
    private boolean playerTwoLeft;
    private int playerOnePoints;
    private int playerTwoPoints;
    private Board board;
    private int turn;
    private TileEnum lastMoveFrom;
    private TileEnum lastMoveTo;
    private Date creationDate;
    private Date actionDate;
    private UUID activePlayer;
    private boolean tie;
    private UUID winner;
    private boolean isClosed;

    /**
     * This is the standard constructor which calls super.
     *
     * @param userId the user uuid
     */
    public GameStatusMessage(UUID userId) {
        super(MessageType.GameStatus, userId);
    }

    /**
     * This method returns the game uuid.
     *
     * @return the game uuid
     */
    public UUID getGameId() {
        return gameId;
    }

    /**
     * This method returns the player one uuid.
     *
     * @return the player one uuid
     */
    public UUID getPlayerOne() {
        return playerOne;
    }

    /**
     * This method returns the player two uuid.
     *
     * @return the player two uuid
     */
    public UUID getPlayerTwo() {
        return playerTwo;
    }

    /**
     * This method returns the player one username.
     *
     * @return the player one username
     */
    public String getPlayerOneUserName() {
        return playerOneUserName;
    }

    /**
     * This method returns the player two username.
     *
     * @return the player two username
     */
    public String getPlayerTwoUserName() {
        return playerTwoUserName;
    }

    /**
     * This method returns whether player one has left.
     *
     * @return true, if player one has left, false otherwise
     */
    public boolean hasPlayerOneLeft() {
        return playerOneLeft;
    }

    /**
     * This method returns whether player two has left.
     *
     * @return true, if player two has left, false otherwise
     */
    public boolean hasPlayerTwoLeft() {
        return playerTwoLeft;
    }

    /**
     * This method returns the player one points.
     *
     * @return the player one points
     */
    public int getPlayerOnePoints() {
        return playerOnePoints;
    }

    /**
     * This method returns the player two points.
     *
     * @return the player two points
     */
    public int getPlayerTwoPoints() {
        return playerTwoPoints;
    }

    /**
     * This method returns the game board state.
     *
     * @return the board game state
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This method returns the turn number.
     *
     * @return the turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * This method returns the last move from tile.
     *
     * @return the last move from tile
     */
    public TileEnum getLastMoveFrom() {
        return lastMoveFrom;
    }

    /**
     * This method returns the last move to tile.
     *
     * @return the last move to tile
     */
    public TileEnum getLastMoveTo() {
        return lastMoveTo;
    }

    /**
     * This method returns the creation date of the game.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method returns the action date of the last move.
     *
     * @return the action date
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * This method returns the active player.
     *
     * @return the active player
     */
    public UUID getActivePlayer() {
        return activePlayer;
    }

    /**
     * This method returns whether the tie field in the message is true or false.
     *
     * @return the tie field of the message
     */
    public boolean isTie() {
        return tie;
    }

    /**
     * This method returns the specified winner uuid.
     *
     * @return the winner uuid
     */
    public UUID getWinner() {
        return winner;
    }

    /**
     * This method returns whether or not the game is closed.
     *
     * @return true, if the game is closed, false otherwise
     */
    public boolean isClosed() {
        return isClosed;
    }
}
