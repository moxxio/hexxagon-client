package de.johannesrauch.hexxagon.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.view.screen.Lettering;

/**
 * This class represents the click listener for a leave button
 * which asks with a dialog if the user is sure about his leave action.
 */
public class LeaveButtonClickListener extends ClickListener {

    private final StateContext context;
    private final Stage stage;
    private final Skin skin;

    /**
     * This is the standard constructors which sets all necessary values.
     *
     * @param context the state context
     * @param stage   the stage in which the dialog shows
     * @param skin    the skin for the dialog
     */
    public LeaveButtonClickListener(StateContext context, Stage stage, Skin skin) {
        this.context = context;
        this.stage = stage;
        this.skin = skin;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Label reassureLabel = new Label(Lettering.REALLY_LEAVE, skin);
        TextButton yesButton = new TextButton(Lettering.YES, skin);
        TextButton noButton = new TextButton(Lettering.NO, skin);

        Dialog dialog = new Dialog(Lettering.LEAVE_GAME, skin) {
            @Override
            protected void result(Object object) {
                boolean result = (boolean) object;
                if (result) {
                    context.reactToClickedLeave();
                }
            }
        };

        dialog.getContentTable().pad(15);
        dialog.getContentTable().add(reassureLabel);
        dialog.button(yesButton, true);
        dialog.button(noButton, false);
        dialog.show(stage);
    }
}
