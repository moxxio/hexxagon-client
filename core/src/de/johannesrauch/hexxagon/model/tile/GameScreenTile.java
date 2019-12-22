package de.johannesrauch.hexxagon.model.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class GameScreenTile extends Image {
    public GameScreenTile(Texture texture) {
        super(new SpriteDrawable(new Sprite(texture)));
    }

    public void setTexture(Texture texture) {
        setDrawable(new SpriteDrawable(new Sprite(texture)));
    }
}
