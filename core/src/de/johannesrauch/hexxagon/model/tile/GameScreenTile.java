package de.johannesrauch.hexxagon.model.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * This class represents a game screen tile. It extends an image, so that the texture of the game screen tile can be set.
 */
public class GameScreenTile extends Image {

    /**
     * This is the standard constructor. It calls the image constructor which draws the given texture.
     *
     * @param texture the texture of the game screen tile
     */
    public GameScreenTile(Texture texture) {
        super(new SpriteDrawable(new Sprite(texture)));
    }

    /**
     * This method sets the game screen tile texture.
     *
     * @param texture the texture of the game screen tile
     */
    public void setTexture(Texture texture) {
        setDrawable(new SpriteDrawable(new Sprite(texture)));
    }
}
