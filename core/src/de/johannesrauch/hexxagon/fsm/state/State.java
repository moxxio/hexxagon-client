package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public interface State {
    public State reactToClickedConnect(StateContext context, String hostName, String port);

    public State reactToClickedDisconnect(StateContext context);

    public State reactToClickedPlay(StateContext context);

    public State reactToClickedBack(StateContext context);

    public State reactToClickedJoinLobby(StateContext context, UUID lobbyId, String userName);

    public State reactToClickedLeave(StateContext context);

    public State reactToClickedStart(StateContext context);

    public State reactToReceivedConnectionError(StateContext context);

    public State reactToReceivedWelcome(StateContext context, WelcomeMessage message);

    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message);

    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message);

    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message);

    public State reactToReceivedGameStatus(StateContext context, GameStatusMessage message);

    public State reactToReceivedServerDisconnect(StateContext context);
}
