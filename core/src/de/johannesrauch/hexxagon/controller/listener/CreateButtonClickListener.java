package de.johannesrauch.hexxagon.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.johannesrauch.hexxagon.network.client.MessageEmitter;
import de.johannesrauch.hexxagon.view.screen.Lettering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.zip.Adler32;

public class CreateButtonClickListener extends ClickListener {

    private final Logger logger = LoggerFactory.getLogger(CreateButtonClickListener.class);

    private final Stage stage;
    private final Skin skin;
    private final MessageEmitter messageEmitter;
    private final Adler32 a32;

    public CreateButtonClickListener(Stage stage, Skin skin, MessageEmitter messageEmitter) {
        this.stage = stage;
        this.skin = skin;
        this.messageEmitter = messageEmitter;
        a32 = new Adler32();
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Label lobbyNameLabel = new Label(Lettering.LOBBY_NAME, skin);

        TextButton createButton = new TextButton(Lettering.CREATE, skin);
        TextButton cancelButton = new TextButton(Lettering.CANCEL, skin);

        a32.update(UUID.randomUUID().toString().getBytes());
        TextField lobbyNameTextField = new TextField("LOBBY" + a32.getValue(), skin);

        Dialog dialog = new Dialog(Lettering.CREATE_LOBBY, skin) {
            @Override
            protected void result(Object object) {
                boolean result = (boolean) object;
                if (result) {
                    if (messageEmitter != null) {
                        String lobbyName = lobbyNameTextField.getText();
                        if (lobbyName.matches("([a-zA-Z]{4}[a-zA-Z_0-9 -]{0,16})"))
                            messageEmitter.sendCreateNewLobbyMessage(lobbyName);
                        else {
                            Dialog dialog2 = new Dialog(Lettering.CREATE_LOBBY_FAILED, skin);
                            dialog2.getContentTable().add(new Label("The lobby name does not match the required regex.", skin));
                            dialog2.button(Lettering.OK);
                            dialog2.show(stage);
                        }
                    } else logger.warn("Message emitter is null!");
                }
            }
        };
        dialog.getContentTable().pad(15);
        dialog.getContentTable().add(lobbyNameLabel);
        dialog.getContentTable().add(lobbyNameTextField).width(500);
        dialog.button(createButton, true);
        dialog.button(cancelButton, false);
        dialog.show(stage);
    }
}
