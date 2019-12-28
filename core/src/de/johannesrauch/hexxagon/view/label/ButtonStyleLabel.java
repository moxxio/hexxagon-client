package de.johannesrauch.hexxagon.view.label;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * This class represents a label. It extends a text button, to make it a button style label.
 */
public class ButtonStyleLabel extends TextButton {
    public ButtonStyleLabel(String text, Skin skin) {
        super(text, skin);
        setTouchable(Touchable.disabled);
    }

    public ButtonStyleLabel(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        setTouchable(Touchable.disabled);
    }

    public ButtonStyleLabel(String text, TextButtonStyle style) {
        super(text, style);
        setTouchable(Touchable.disabled);
    }
}
