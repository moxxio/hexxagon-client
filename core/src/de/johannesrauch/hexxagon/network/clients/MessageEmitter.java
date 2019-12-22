package de.johannesrauch.hexxagon.network.clients;

import java.util.UUID;

import de.johannesrauch.hexxagon.Hexxagon;
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
	
	private final Gson gson;
	
	private final Hexxagon parent;

	public MessageEmitter(Hexxagon parent) {
		gson = new Gson();
		this.parent = parent;
	}

	/**
	 * Diese Methode sendet eine bereits als json String formatierte Nachricht an den Spielserver.
	 * 
	 * @param jsonMessage die zu sendende Nachricht im json Format
	 * @author Dennis Jehle
	 */
	private void sendMessage(String jsonMessage) {
		WebSocket connection = parent.getConnectionHandler().getConnection();
		if (connection == null) {
			return;
		}
		connection.send(jsonMessage);
	}
	
	public void sendGetAvailableLobbiesMessage() {
		UUID userId = parent.getConnectionHandler().getUserId();
		GetAvailableLobbies getAvailableLobbies = new GetAvailableLobbies(userId);
		String jsonMessage = gson.toJson(getAvailableLobbies);
		sendMessage(jsonMessage);
	}
	
	public void sendCreateNewLobbyMessage(String lobbyName) {
		UUID userId = parent.getConnectionHandler().getUserId();
		CreateNewLobby createNewLobby = new CreateNewLobby(userId, lobbyName);
		String jsonMessage = gson.toJson(createNewLobby);
		sendMessage(jsonMessage);
	}
	
	public void sendJoinLobbyMessage(UUID lobbyId, String userName) {
		UUID userId = parent.getConnectionHandler().getUserId();
		JoinLobby joinLobby = new JoinLobby(userId, lobbyId, userName);
		String jsonMessage = gson.toJson(joinLobby);
		sendMessage(jsonMessage);
	}
	
	public void sendLeaveLobbyMessage(UUID lobbyId) {
		UUID userId = parent.getConnectionHandler().getUserId();
		LeaveLobby leaveLobby = new LeaveLobby(userId, lobbyId);
		String jsonMessage = gson.toJson(leaveLobby);
		sendMessage(jsonMessage);
	}
	
	public void sendStartGameMessage(UUID lobbyId) {
		UUID userId = parent.getConnectionHandler().getUserId();
		StartGame startGame = new StartGame(userId, lobbyId);
		String jsonMessage = gson.toJson(startGame);
		sendMessage(jsonMessage);
	}
	
	public void sendGameMoveMessage(UUID gameId, TileEnum moveFrom, TileEnum moveTo) {
		// TODO: implement this
	}
	
	public void sendLeaveGameMessage(UUID gameId) {
		// TODO: implement this
	}
	
}
