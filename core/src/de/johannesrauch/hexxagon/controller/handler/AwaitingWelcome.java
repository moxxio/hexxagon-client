package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.fsm.context.StateContext;

/**
 * This class implements runnable. It used in a executor service in order to wait a period of time (5s).
 * If no welcome message was received, this runnable then disconnects the client from the server.
 * If the client was welcomed, nothing happens.
 */
public class AwaitingWelcome implements Runnable {

    private final StateContext context;
    private boolean welcomed = false;
    private boolean canceled = false;

    /**
     * This is the standard constructor.
     *
     * @param context the state context
     */
    public AwaitingWelcome(StateContext context) {
        this.context = context;
    }

    /**
     * This method sets the welcomed variable.
     *
     * @param welcomed the value welcomed is set to
     */
    public void setWelcomed(boolean welcomed) {
        this.welcomed = welcomed;
    }

    /**
     * This method sets the canceled variable.
     *
     * @param canceled the value canceled is set to
     */
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * This method waits five seconds. If no welcome message was received in that time,
     * it disconnects the client from the server.
     */
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
