package de.johannesrauch.hexxagon.controller.handler;

import de.johannesrauch.hexxagon.fsm.context.StateContext;

/**
 * This class implements runnable. It used in a executor service in order to wait a period of time (5s).
 * If no welcome message was received, this runnable then disconnects the client from the server.
 * If the client was welcomed, nothing happens.
 */
public class AwaitingWelcome implements Runnable {

    private final StateContext context;
    private final int sleepTime;
    private boolean welcomed = false;
    private boolean canceled = false;

    /**
     * This is the standard constructor. It sets the context and a sleep time of 3s.
     *
     * @param context the state context
     */
    public AwaitingWelcome(StateContext context) {
        this(context, 3000);
    }

    /**
     * This is the constructor where you can specify the sleep time yourself.
     * If sleep time is <= 0, the run method will not sleep.
     *
     * @param context   the state context
     * @param sleepTime the sleep time in millis
     */
    public AwaitingWelcome(StateContext context, int sleepTime) {
        this.context = context;
        this.sleepTime = sleepTime;
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
     * This method sleeps sleep time millis and then checks if the client was welcomed.
     * If no welcome message was received in that time, it disconnects the client from the server.
     */
    @Override
    public void run() {
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {
            }
        }

        if (canceled) return;
        if (!welcomed) context.reactToReceivedConnectionError();
    }
}
