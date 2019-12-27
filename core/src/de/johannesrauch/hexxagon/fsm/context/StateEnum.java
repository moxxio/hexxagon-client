package de.johannesrauch.hexxagon.fsm.context;

public enum StateEnum {
    Disconnected,
    ConnectionAttempt,
    Connected,
    SearchLobby,
    JoiningLobby,
    InLobbyAsPlayerOne,
    InLobbyAsPlayerTwo,
    InFullLobbyAsPlayerOne,
    UninitializedGame,
    InGameMyTurn,
    InGameOpponentsTurn,
    Winner,
    Loser,
    Tie
}
