package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.fsm.context.StateContext;

public class AwaitingWelcome implements Runnable {

    private final StateContext context;
    private boolean welcomed = false;
    private boolean canceled = false;

    public AwaitingWelcome(StateContext context) {
        this.context = context;
    }

    public void setWelcomed(boolean welcomed) {
        this.welcomed = welcomed;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }

        if (canceled) return;
        if (!welcomed) context.reactToReceivedConnectionError();
    }
}
