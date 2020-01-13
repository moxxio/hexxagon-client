package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.test.tools.TestKit;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * This class tests reactions of the state context to all click events.
 */
public class ClickEventTest {

    @Before
    public void setUp() {
        BasicConfigurator.configure(); // Otherwise logger complains
    }

    /**
     * This method tests the reaction to clicked connect in disconnected state.
     */
    @Test
    public void testReactToClickedConnect() {
        TestKit kit = new TestKit(StateContext.getDisconnectedState());
        kit.context.reactToClickedConnect("localhost", "4444");
        Assert.assertEquals(StateContext.getConnectionAttemptState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked disconnect in connected state.
     */
    @Test
    public void testReactToClickedDisconnect() {
        TestKit kit = new TestKit(StateContext.getConnectedState());
        kit.context.reactToClickedDisconnect();
        Assert.assertEquals(StateContext.getDisconnectedState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked play in connected state.
     */
    @Test
    public void testReactToClickedPlay() {
        TestKit kit = new TestKit(StateContext.getConnectedState());
        kit.context.reactToClickedPlay();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked back in select lobby state.
     */
    @Test
    public void testReactToClickedBack() {
        TestKit kit = new TestKit(StateContext.getSelectLobbyState());
        kit.context.reactToClickedBack();
        Assert.assertEquals(StateContext.getConnectedState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked join lobby in select lobby state.
     */
    @Test
    public void testReactToClickedJoinLobby() {
        TestKit kit = new TestKit(StateContext.getSelectLobbyState());
        kit.context.reactToClickedJoinLobby(UUID.randomUUID(), "example username");
        Assert.assertEquals(StateContext.getJoiningLobbyState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked leave in in (full) lobby as player x,
     * uninitialized game, in game my turn, in game opponents turn, winner, tie and loser state.
     */
    @Test
    public void testReactToClickedLeave() {
        TestKit kit = new TestKit(StateContext.getInLobbyAsPlayerOneState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getInLobbyAsPlayerTwoState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getInFullLobbyAsPlayerOneState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getUninitializedGameState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getInGameMyTurnState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getInGameOpponentsTurnState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getWinnerState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getTieState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());

        kit = new TestKit(StateContext.getLoserState());
        kit.context.reactToClickedLeave();
        Assert.assertEquals(StateContext.getSelectLobbyState(), kit.context.getState());
    }

    /**
     * This method tests the reaction to clicked start game in in full lobby as player one and
     * in lobby as player two state.
     */
    @Test
    public void testReactToClickedStart() {
        TestKit kit = new TestKit(StateContext.getInFullLobbyAsPlayerOneState());
        kit.context.reactToClickedStart();
        Assert.assertEquals(StateContext.getUninitializedGameState(), kit.context.getState());

        kit = new TestKit(StateContext.getInLobbyAsPlayerTwoState());
        kit.context.reactToClickedStart();
        Assert.assertEquals(StateContext.getUninitializedGameState(), kit.context.getState());
    }
}
