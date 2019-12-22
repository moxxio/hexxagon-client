package de.johannesrauch.hexxagon.state.context;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.state.event.AbstractEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StateContext {

    private final Hexxagon parent;
    private StateEnum state;
    private final ExecutorService executorService;

    public StateContext(Hexxagon parent) {
        this.parent = parent;
        state = StateEnum.Disconnected;
        executorService = Executors.newFixedThreadPool(1);
    }

    public void reactOnEvent(AbstractEvent event) {
        executorService.submit(() -> {
            react(event);
        });
    }

    private synchronized void react(AbstractEvent event) {
        StateEnum nextState = event.reactOnEvent(this);
        if (state != nextState) state = nextState;
    }

    public Hexxagon getParent() {
        return parent;
    }

    public StateEnum getState() {
        return state;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
