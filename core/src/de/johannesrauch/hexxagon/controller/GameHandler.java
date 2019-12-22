package de.johannesrauch.hexxagon.controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.board.Board;
import de.johannesrauch.hexxagon.network.board.BoardGraph;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameHandler {
	
	private final Logger logger = LoggerFactory.getLogger(GameHandler.class);

	private final Hexxagon parent;
	
	private UUID userId;
	private UUID gameId;
	private Date creationDate;
	private UUID playerOne;
	private UUID playerTwo;
	private String playerOneUserName;
	private String playerTwoUserName;
	private int playerOnePoints;
	private int playerTwoPoints;
	private Board board;
	TileEnum lastMoveFrom;
	TileEnum lastMoveTo;

	private boolean isGameActive;
	private boolean isClientPlayerOne;
	private boolean isClientPlayerTwo;
	private boolean isPlayerOneMove;
	private boolean isPlayerTwoMove;
	private boolean isInitComplete;
	private boolean opponentLeft;
	private AtomicBoolean boardUpdated;

	private BoardGraph boardGraph;

	public GameHandler(Hexxagon parent) {
		this.parent = parent;
		setDefaultValues();
	}

	public void gameOver(UUID winner) {
		isGameActive = false;
		// TODO: process winner (server's job?)
	}

	public void gameStarted(UUID userId, UUID gameId, Date creationDate) {
		setDefaultValues();
		this.userId = userId;
		this.gameId = gameId;
		this.creationDate = creationDate;
		isGameActive = true;
	}

	public void gameStatusInitialize(UUID playerOne,
									 UUID playerTwo,
									 String playerOneUserName,
									 String playerTwoUserName,
									 int playerOnePoints,
									 int playerTwoPoints,
									 Board board,
									 UUID activePlayer) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.playerOneUserName = playerOneUserName != null ? playerOneUserName : "";
		this.playerTwoUserName = playerTwoUserName != null ? playerTwoUserName : "";
		this.playerOnePoints = playerOnePoints;
		this.playerTwoPoints = playerTwoPoints;
		isClientPlayerOne = userId.equals(playerOne);
		isClientPlayerTwo = userId.equals(playerTwo);
		setPlayerMove(activePlayer);
		this.board = board;

		isInitComplete = true;
		boardUpdated.set(true);
	}

	public void gameStatusUpdate(
			TileEnum lastMoveFrom
			, TileEnum lastMoveTo
			, int playerOnePoints
			, int playerTwoPoints
			, UUID activePlayer
			, Board board) {
		// set lastMoveFrom
		this.lastMoveFrom = lastMoveFrom;
		// set lastMoveTo
		this.lastMoveTo = lastMoveTo;
		// set player ones points
		this.playerOnePoints = playerOnePoints;
		// set player twos points
		this.playerTwoPoints = playerTwoPoints;
		// check and set if playerOne or playerTwo is allowed to make the next move
		setPlayerMove(activePlayer);
		// update board
		// TODO: make this more elegant, so that the renderer becomes all information needed for rendering moves
		this.board = board;

		// board updated
		boardUpdated.set(true);
	}

	public boolean isClientPlayerOne() {
		return isClientPlayerOne;
	}

	public boolean isClientPlayerTwo() {
		return isClientPlayerTwo;
	}

	public boolean isPlayerOneMove() {
		return isPlayerOneMove;
	}

	public boolean isPlayerTwoMove() {
		return isPlayerTwoMove;
	}

	public boolean isInitComplete() {
		return isInitComplete;
	}

	public void leaveGame() {
		if (gameId != null && isGameActive) {
			parent.getMessageEmitter().sendLeaveGameMessage(gameId);
		}
		setDefaultValues();
	}

	public void opponentLeftGame() {
		opponentLeft = true;
		isGameActive = false;
	}

	public void sendGameMoveMessage(TileEnum moveFrom, TileEnum moveTo) {
		parent.getMessageEmitter().sendGameMoveMessage(gameId, moveFrom, moveTo);
	}
	
	private void setDefaultValues() {
		userId = null;
		gameId = null;
		isGameActive = false;
		creationDate = null;
		playerOne = null;
		playerTwo = null;
		playerOneUserName = null;
		playerTwoUserName = null;
		isClientPlayerOne = false;
		isClientPlayerTwo = false;
		isPlayerOneMove = false;
		isPlayerTwoMove = false;
		playerOnePoints = 0;
		playerTwoPoints = 0;
		lastMoveFrom = null;
		lastMoveTo = null;
		board = null;
		boardGraph = new BoardGraph();
		isInitComplete = false;
		boardUpdated = new AtomicBoolean();
		opponentLeft = false;
	}

	private void setPlayerMove(UUID activePlayer) {
		isPlayerOneMove = playerOne.equals(activePlayer);
		isPlayerTwoMove = playerTwo.equals(activePlayer);
	}
}
