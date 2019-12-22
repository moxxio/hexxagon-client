package de.johannesrauch.hexxagon.automaton.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.automaton.events.AbstractEvent;
import de.johannesrauch.hexxagon.automaton.states.*;
import de.johannesrauch.hexxagon.controller.*;
import de.johannesrauch.hexxagon.network.clients.MessageEmitter;
import de.johannesrauch.hexxagon.network.clients.MessageReceiver;

public class StateContext {

    private final Hexxagon parent;

    private StateEnum state;

    public StateContext(Hexxagon parent) {
        this.parent = parent;
        state = StateEnum.Disconnected;
    }

    public synchronized void reactOnEvent(AbstractEvent event) {
        StateEnum nextState = event.reactOnEvent(this);
        setState(nextState);
    }

    public Hexxagon getParent() {
        return parent;
    }

    public StateEnum getState() {
        return state;
    }

    private void setState(StateEnum state) {
        this.state = state;
    }
}
