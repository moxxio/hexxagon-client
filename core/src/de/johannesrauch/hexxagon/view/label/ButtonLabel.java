package de.johannesrauch.hexxagon.view.label;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonLabel extends TextButton {
    public ButtonLabel(String text, Skin skin) {
        super(text, skin);
        setTouchable(Touchable.disabled);
    }

    public ButtonLabel(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        setTouchable(Touchable.disabled);
    }

    public ButtonLabel(String text, TextButtonStyle style) {
        super(text, style);
        setTouchable(Touchable.disabled);
    }
}
