package de.johannesrauch.hexxagon.test.tools;

import de.johannesrauch.hexxagon.Hexxagon;
import de.johannesrauch.hexxagon.resource.Resources;

public class HexxagonDummy extends Hexxagon {
    // Dummy methods
    public HexxagonDummy() {
    }

    @Override
    public void create() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public Resources getResources() {
        return null;
    }

    @Override
    public void showMainMenuScreen(boolean connectionError, boolean serverDisconnect) {
    }

    @Override
    public void showSelectLobbyScreen(boolean showProgressBar, int millis) {
    }

    @Override
    public void showLobbyScreen() {
    }

    @Override
    public void showGameScreen() {
    }
}
