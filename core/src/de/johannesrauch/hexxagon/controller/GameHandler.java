package de.johannesrauch.hexxagon.controller;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.network.message.GameStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class GameHandler {

    private final Hexxagon parent;

    private final Logger logger;

    private GameStatusMessage gameStatus;

    private UUID userId;
    private UUID gameId;

    public GameHandler(Hexxagon parent) {
        this.parent = parent;
        logger = LoggerFactory.getLogger(GameHandler.class);
        reset();
    }

    public boolean isInitComplete() {
        return gameStatus != null;
    }

    public boolean isMyTurn() {
        if (gameStatus == null || userId == null) return false;
        return gameStatus.getActivePlayer() == userId;
    }

    public void leaveGame() {
        if (gameId != null) {
            logger.info("Left game: " + gameId.toString());
            parent.getMessageEmitter().sendLeaveGameMessage(gameId);
        } else logger.warn("GameId is null in leaveGame()!");
        reset();
    }

    public void startedGame(UUID userId, UUID gameId) {
        logger.info("Started game: " + gameId.toString());
        this.userId = userId;
        this.gameId = gameId;
    }

    public void updateGame(GameStatusMessage gameStatus) {
        logger.info("Game update: " + gameStatus.toString());
        this.gameStatus = gameStatus;
    }

    private void reset() {
        gameStatus = null;
        userId = null;
        gameId = null;
    }
}
