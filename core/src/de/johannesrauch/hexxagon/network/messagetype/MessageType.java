package de.johannesrauch.hexxagon.network.messagetype;

public enum MessageType {
	Welcome,
	GetAvailableLobbies,
	AvailableLobbies,
	CreateNewLobby,
	LobbyCreated,
	JoinLobby,
	LobbyJoined,
	LobbyStatus,
	LeaveLobby,
	StartGame,
	GameStarted,
	GameStatus,
	GameMove,
	LeaveGame,
	Strike
}