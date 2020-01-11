package de.johannesrauch.hexxagon.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.fsm.context.StateContext;
import de.johannesrauch.hexxagon.view.screen.Lettering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

/**
 * This class implements the click listener interface and should be added to a join button.
 */
public class JoinButtonClickListener extends ClickListener {

    private final Logger logger = LoggerFactory.getLogger(JoinButtonClickListener.class);

    private final StateContext context;
    private final Stage stage;
    private final Skin skin;
    private final List<String> lobbyList;
    private final Map<String, UUID> lobbyIds;
    private final TextField userNameTextField;

    /**
     * This constructor provides all necessary objects to the class.
     *
     * @param context           the state context
     * @param lobbyList         the lobby list
     * @param lobbyIds          the lobby uuids
     * @param userNameTextField the username textfield
     */
    public JoinButtonClickListener(StateContext context, Stage stage, Skin skin, List<String> lobbyList, Map<String, UUID> lobbyIds, TextField userNameTextField) {
        this.context = context;
        this.stage = stage;
        this.skin = skin;
        this.lobbyList = lobbyList;
        this.lobbyIds = lobbyIds;
        this.userNameTextField = userNameTextField;
    }

    /**
     * This method triggers the action on click join.
     * First it looks up the selected lobby. If it is null, it returns.
     * Afterwards, it looks up the lobby uuid associated to the lobby name. If it is null, it returns.
     * Then it gets the username. If it is null, it returns.
     * If it has not returned so far, it informs the context about the clicked join lobby event.
     *
     * @param event ignored
     * @param x     ignored
     * @param y     ignored
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {
        String selected = lobbyList.getSelected();
        if (selected == null) return;
        logger.info("Selected lobby " + selected);

        UUID lobbyId = lobbyIds.get(selected);
        if (lobbyId == null) return;
        logger.info("Selected lobby UUID " + lobbyId.toString());

        String userName = userNameTextField.getText();
        if (userName == null) return;
        else if (!userName.matches("([a-zA-Z][a-zA-Z_0-9]{3,13})")) {
            Dialog dialog2 = new Dialog(Lettering.USERNAME_INVALID, skin);
            dialog2.getContentTable().add(new Label("The username does not match the required regex.", skin));
            dialog2.button(Lettering.OK);
            dialog2.show(stage);
            return;
        }
        context.reactToClickedJoinLobby(lobbyId, userName);
    }
}
