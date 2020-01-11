package de.johannesrauch.hexxagon.fsm.state;

import de.johannesrauch.hexxagon.controller.handler.LobbyHandler;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.network.message.*;

import java.util.UUID;

public class InLobbyAsPlayerTwoState implements State {

    /**
     * When in lobby as player two state and leave is clicked, leave the lobby, go back to select lobby screen and
     * transition to select lobby state.
     *
     * @param context the context in which this state is used
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedLeave(StateContext context) {
        return StateContext.getInLobbyAsPlayerOneState().reactToClickedLeave(context);
    }

    /**
     * When in lobby as player two state and the user clicks start, then send a start game message,
     * show game screen and transition to uninitialized game state.
     *
     * @param context the context in which this state is used
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToClickedStart(StateContext context) {
        return StateContext.getInFullLobbyAsPlayerOneState().reactToClickedStart(context);
    }

    /**
     * When in full lobby as player one state and the lobby joined message is received after the lobby status message,
     * then update the lobby.
     *
     * @param context the context in which this state is used
     * @param message the received lobby joined message
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyJoined(StateContext context, LobbyJoinedMessage message) {
        return StateContext.getJoiningLobbyState().reactToReceivedLobbyJoined(context, message);
    }

    /**
     * When in lobby as player two state, then check the lobby status message and transition to the particular state.
     *
     * @param context the context in which this state is used
     * @param message the received lobby status message
     * @return the next state or null, if state does not change
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedLobbyStatus(StateContext context, LobbyStatusMessage message) {
        return StateContext.getInFullLobbyAsPlayerOneState().reactToReceivedLobbyStatus(context, message);
    }

    /**
     * When in lobby as player two state and a game started message is received, show game screen and transition to
     * uninitialized game state.
     *
     * @param context the context in which this state is used
     * @param message the received game started message
     * @return the next state
     * @author Johannes Rauch
     */
    @Override
    public State reactToReceivedGameStarted(StateContext context, GameStartedMessage message) {
        return StateContext.getInFullLobbyAsPlayerOneState().reactToReceivedGameStarted(context, message);
    }
}
