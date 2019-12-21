package de.johannesrauch.hexxagon.automaton.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.events.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.states.*;
import de.johannesrauch.hexxagon.controller.*;
import de.johannesrauch.hexxagon.network.clients.MessageEmitter;
import de.johannesrauch.hexxagon.network.clients.MessageReceiver;

public class StateContext {

    private Hexxagon parent;

    private State state;

    private ConnectionHandler connectionHandler;
    private MessageEmitter messageEmitter;
    private MessageReceiver messageReceiver;
    private GameHandler gameHandler;

    private DisconnectedState disconnectedState;
    private ConnAttemptState connAttemptState;
    private ConnectedState connectedState;
    private SearchLobbyState searchLobbyState;
    private JoiningLobbyState joiningLobbyState;
    private LobbyPlayerOneState lobbyPlayerOneState;
    private LobbyPlayerTwoState lobbyPlayerTwoState;
    private ReadyLobbyPlayerOneState readyLobbyPlayerOneState;

    public StateContext() {
        disconnectedState = new DisconnectedState();
        connAttemptState = new ConnAttemptState();
        connectedState = new ConnectedState();
        searchLobbyState = new SearchLobbyState();
        joiningLobbyState = new JoiningLobbyState();
        lobbyPlayerOneState = new LobbyPlayerOneState();
        lobbyPlayerTwoState = new LobbyPlayerTwoState();
        readyLobbyPlayerOneState = new ReadyLobbyPlayerOneState();

        state = disconnectedState;
    }

    public synchronized void reactOnEvent(AbstractEvent event) {
        state.reactOnEvent(event, this);
    }

    public Hexxagon getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    public MessageReceiver getMessageReceiver() {
        return messageReceiver;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public DisconnectedState getDisconnectedState() {
        return disconnectedState;
    }

    public ConnAttemptState getConnAttemptState() {
        return connAttemptState;
    }

    public ConnectedState getConnectedState() {
        return connectedState;
    }

    public SearchLobbyState getSearchLobbyState() {
        return searchLobbyState;
    }

    public JoiningLobbyState getJoiningLobbyState() {
        return joiningLobbyState;
    }

    public LobbyPlayerOneState getLobbyPlayerOneState() {
        return lobbyPlayerOneState;
    }

    public LobbyPlayerTwoState getLobbyPlayerTwoState() {
        return lobbyPlayerTwoState;
    }

    public ReadyLobbyPlayerOneState getReadyLobbyPlayerOneState() {
        return readyLobbyPlayerOneState;
    }

    public void setParent(Hexxagon parent) {
        this.parent = parent;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void setMessageEmitter(MessageEmitter messageEmitter) {
        this.messageEmitter = messageEmitter;
    }

    public void setMessageReceiver(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}
