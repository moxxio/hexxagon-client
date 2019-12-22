package de.johannesrauch.hexxagon.automaton.states;

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
    Loser
}
