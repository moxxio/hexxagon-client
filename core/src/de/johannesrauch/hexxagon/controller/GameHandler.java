package de.johannesrauch.hexxagon.controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import de.johannesrauch.hexxagon.network.board.Board;
import de.johannesrauch.hexxagon.network.board.BoardGraph;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import de.johannesrauch.hexxagon.network.clients.MessageEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameHandler {
	
	private final Logger logger = LoggerFactory.getLogger(GameHandler.class);
	
	private MessageEmitter messageEmitter;
	
	/**
	 * The clients unique identifier. Duplicate of userId in ConnectionHandler. 
	 */
	private UUID userId;
	
	/**
	 * The unique identifier representing the games id. 
	 */
	public UUID gameId;
	
	/**
	 * This attribute indicates whether the game is active or not.
	 * true = active
	 * false = closed (for whatever reason)
	 */
	public boolean isGameActive;
	
	/**
	 * Indicates when the game was started.
	 */
	public Date creationDate;
	
	/**
	 * The unique identifier representing the player one.
	 */
	public UUID playerOne;
	
	/**
	 * The unique identifier representing the player two.
	 */
	public UUID playerTwo;
	
	/**
	 * Well, if player one should have a name, here is the place to store it.
	 */
	public String playerOneUserName;
	
	/**
	 * This attribute stores the user name of player two.
	 */
	public String playerTwoUserName;
	
	/**
	 * This attribute indicates whether the client is player one or not.
	 * true = client is player one
	 * false = not
	 */
	public boolean clientIsPlayerOne;
	
	/**
	 * This attribute indicates whether the client is player two or not.
	 * true = client is player two
	 * false = not
	 */
	public boolean clientIsPlayerTwo;
	
	/**
	 * If this attribute is true, player one is allowed to make the next move.
	 * true = yes
	 * false = no
	 */
	public boolean isPlayerOneMove;
	
	/**
	 * if this attribute is true, player two is allowed to make the next move.
	 * true = yes
	 * false = no
	 */
	public boolean isPlayerTwoMove;
	
	/**
	 * This attribute stores the points of player one.
	 */
	public int playerOnePoints;
	
	/**
	 * This attribute stores the points of player two.
	 */
	public int playerTwoPoints;
	
	TileEnum lastMoveFrom;
	
	TileEnum lastMoveTo;
	
	public Board board;
	
	/**
	 * Have a look at the documentation of BoardGraph.
	 */
	public BoardGraph boardGraph;
	
	/**
	 * This attribute signals the message receiver and the renderer whether everything is ready or not.
	 * true = ready
	 * false = not
	 */
	public boolean initializationCompleted; 
	
	/**
	 * This attribute signals the renderer whether the board has changed or not.
	 * true = changed
	 * false = not
	 */
	public AtomicBoolean boardUpdated;
	
	public boolean opponentLeft; 
	
	/**
	 * Setter for the private attribute messageEmitter.
	 * @param messageEmitter TODO
	 */
	public void setMessageEmitter(MessageEmitter messageEmitter) {
		this.messageEmitter = messageEmitter;
	}
	
	/**
	 * Default constructor of class GameHandler.
	 * Constructor sets default values for all class attributes. 
	 */
	public GameHandler() {
		setDefaultValues();
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
									 UUID activePlayer,
									 Board board) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.playerOneUserName = playerOneUserName;
		this.playerTwoUserName = playerTwoUserName;
		this.playerOnePoints = playerOnePoints;
		this.playerTwoPoints = playerTwoPoints;
		clientIsPlayerOne = userId.equals(playerOne);
		clientIsPlayerTwo = userId.equals(playerTwo);
		whoseTurnIsIt(activePlayer);
		this.board = board;
		
		initializationCompleted = true;
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
		whoseTurnIsIt(activePlayer);
		// update board
		// TODO: make this more elegant, so that the renderer becomes all information needed for rendering moves
		this.board = board;
		
		// board updated
		boardUpdated.set(true);
		
	}
	
	public void opponentLeftGame() {
		opponentLeft = true;
		isGameActive = false;
	}
	
	public void gameOver(UUID winner) {
		isGameActive = false;
		// TODO: process winner (server's job?)
	}
	
	public void leaveGame() {
		if (gameId != null && isGameActive) {
			// send leave game message
			messageEmitter.sendLeaveGameMessage(gameId);
		}
		// set everything to the default values
		setDefaultValues();
	}
	
	public void sendGameMoveMessage(TileEnum moveFrom, TileEnum moveTo) {
		messageEmitter.sendGameMoveMessage(gameId, moveFrom, moveTo);
	}
	
	private void whoseTurnIsIt(UUID activePlayer) {
		isPlayerOneMove = playerOne.equals(activePlayer);
		isPlayerTwoMove = playerTwo.equals(activePlayer);
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
		
		clientIsPlayerOne = false;
		clientIsPlayerTwo = false;
		
		isPlayerOneMove = false;
		isPlayerTwoMove = false;
		
		playerOnePoints = 0;
		playerTwoPoints = 0;
		
		lastMoveFrom = null;
		lastMoveTo = null;
		
		board = null;
		boardGraph = new BoardGraph();
		initializationCompleted = false;
		boardUpdated = new AtomicBoolean();
		
		opponentLeft = false;
	}
}
