package de.johannesrauch.hexxagon.view.label;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

public class TextFieldStyleLabel extends TextField {
    public TextFieldStyleLabel(String text, Skin skin) {
        super(text, skin);
        setDisabled(true);
        setAlignment(Align.center);
    }

    public TextFieldStyleLabel(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        setDisabled(true);
        setAlignment(Align.center);
    }

    public TextFieldStyleLabel(String text, TextFieldStyle style) {
        super(text, style);
        setDisabled(true);
        setAlignment(Align.center);
    }
}
