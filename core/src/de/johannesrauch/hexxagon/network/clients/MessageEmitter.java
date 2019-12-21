package de.johannesrauch.hexxagon.network.clients;

import java.util.UUID;

import de.johannesrauch.hexxagon.controller.ConnectionHandler;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import org.java_websocket.WebSocket;

import com.google.gson.Gson;
import de.johannesrauch.hexxagon.network.messages.CreateNewLobby;
import de.johannesrauch.hexxagon.network.messages.GetAvailableLobbies;
import de.johannesrauch.hexxagon.network.messages.JoinLobby;
import de.johannesrauch.hexxagon.network.messages.LeaveLobby;
import de.johannesrauch.hexxagon.network.messages.StartGame;

/**
 * Diese Klasse wird verwendet um Nachrichten an den Spielserver zu senden.
 * Innerhalb der Klasse sind bereits alle Nachrichtenarten implementiert, die an den Server geschickt werden können.
 * Sie können diese Klasse also für Ihre Implementierung ohne weitere Anpassungen nutzen.
 *
 * @author Dennis Jehle
 */
public class MessageEmitter {
	
	Gson gson = new Gson();
	
	private ConnectionHandler connectionHandler;
	
	public void setConnectionHandler(ConnectionHandler connectionHandler) {
		this.connectionHandler = connectionHandler;
	}
	
	public MessageEmitter() {
		connectionHandler = null;
	}
	
	/**
	 * Diese Methode sendet eine bereits als json String formatierte Nachricht an den Spielserver.
	 * 
	 * @param jsonMessage die zu sendende Nachricht im json Format
	 * @author Dennis Jehle
	 */
	private void sendMessage(String jsonMessage) {
		WebSocket connection = connectionHandler.getConnection();
		if (connection == null) {
			return;
		}
		connection.send(jsonMessage);
	}
	
	// GetAvailableLobbies
	public void sendGetAvailableLobbiesMessage() {
		UUID userId = connectionHandler.getUserId();
		GetAvailableLobbies getAvailableLobbies = new GetAvailableLobbies(userId);
		String jsonMessage = gson.toJson(getAvailableLobbies);
		sendMessage(jsonMessage);
	}
	
	// CreateNewLobby
	public void sendCreateNewLobbyMessage(String lobbyName) {
		UUID userId = connectionHandler.getUserId();
		CreateNewLobby createNewLobby = new CreateNewLobby(userId, lobbyName);
		String jsonMessage = gson.toJson(createNewLobby);
		sendMessage(jsonMessage);
	}
	
	// JoinLobby
	public void sendJoinLobbyMessage(UUID lobbyId, String userName) {
		UUID userId = connectionHandler.getUserId();
		JoinLobby joinLobby = new JoinLobby(userId, lobbyId, userName);
		String jsonMessage = gson.toJson(joinLobby);
		sendMessage(jsonMessage);
	}
	
	// LeaveLobby
	public void sendLeaveLobbyMessage(UUID lobbyId) {
		UUID userId = connectionHandler.getUserId();
		LeaveLobby leaveLobby = new LeaveLobby(userId, lobbyId);
		String jsonMessage = gson.toJson(leaveLobby);
		sendMessage(jsonMessage);
	}
	
	// StartGame
	public void sendStartGameMessage(UUID lobbyId) {
		UUID userId = connectionHandler.getUserId();
		StartGame startGame = new StartGame(userId, lobbyId);
		String jsonMessage = gson.toJson(startGame);
		sendMessage(jsonMessage);
	}
	
	// GameMove
	public void sendGameMoveMessage(UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
		// TODO: implementieren
	}
	
	// LeaveGame
	public void sendLeaveGameMessage(UUID gameId) {
		// TODO: implementieren
	}
	
}
