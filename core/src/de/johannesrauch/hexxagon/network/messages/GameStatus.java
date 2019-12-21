package de.johannesrauch.hexxagon.network.messages;

import de.johannesrauch.hexxagon.network.board.Board;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.Date;
import java.util.UUID;

public class GameStatus extends AbstractMessage {

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

    public GameStatus(UUID userId) {
        super(MessageType.GameStatus, userId);
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getPlayerOne() {
        return playerOne;
    }

    public UUID getPlayerTwo() {
        return playerTwo;
    }

    public String getPlayerOneUserName() {
        return playerOneUserName;
    }

    public String getPlayerTwoUserName() {
        return playerTwoUserName;
    }

    public boolean isPlayerOneLeft() {
        return playerOneLeft;
    }

    public boolean isPlayerTwoLeft() {
        return playerTwoLeft;
    }

    public int getPlayerOnePoints() {
        return playerOnePoints;
    }

    public int getPlayerTwoPoints() {
        return playerTwoPoints;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public TileEnum getLastMoveFrom() {
        return lastMoveFrom;
    }

    public TileEnum getLastMoveTo() {
        return lastMoveTo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public UUID getActivePlayer() {
        return activePlayer;
    }

    public boolean isTie() {
        return tie;
    }

    public UUID getWinner() {
        return winner;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
