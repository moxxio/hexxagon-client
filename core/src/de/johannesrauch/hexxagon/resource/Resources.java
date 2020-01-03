package de.johannesrauch.hexxagon.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class loads and contains all resources used by the game.
 */
public class Resources {

    private final Skin skin;
    private final Texture tileFree;
    private final Texture tileBlocked;
    private final Texture tilePlayerOne;
    private final Texture tilePlayerTwo;
    private final Texture tilePlayerOneSelected;
    private final Texture tilePlayerTwoSelected;
    private final Texture tileMoveOne;
    private final Texture tileMoveTwo;
    private final Texture background;
    private final ParticleEffect particleEffect;

    /**
     * This standard constructor loads the standard skin of the game.
     */
    public Resources() {
        this("quantum-horizon", "quantum-horizon-ui");
    }

    /**
     * This constructor does all the work. It loads the textures, the skins and the particle effect.
     *
     * @param skinFolder the skin folder in the asset folder
     * @param skinName   the skin name in the skin folder
     */
    private Resources(String skinFolder, String skinName) {
        skin = new Skin(Gdx.files.internal(skinFolder + "/skin/" + skinName + ".json"));
        tileFree = new Texture(Gdx.files.internal("TileFree.png"));
        tileBlocked = new Texture(Gdx.files.internal("TileBlocked.png"));
        tilePlayerOne = new Texture(Gdx.files.internal("TilePlayerOne.png"));
        tilePlayerTwo = new Texture(Gdx.files.internal("TilePlayerTwo.png"));
        tilePlayerOneSelected = new Texture(Gdx.files.internal("TilePlayerOneSelected.png"));
        tilePlayerTwoSelected = new Texture(Gdx.files.internal("TilePlayerTwoSelected.png"));
        tileMoveOne = new Texture(Gdx.files.internal("TileMoveOne.png"));
        tileMoveTwo = new Texture(Gdx.files.internal("TileMoveTwo.png"));
        background = new Texture(Gdx.files.internal("background-hex.png"));
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("slowbuzz-purple.p"), Gdx.files.internal(""));
        particleEffect.start();
    }

    /**
     * This method returns the skin.
     *
     * @return the skin
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * This method returns the tile free texture.
     *
     * @return the tile free texture
     */
    public Texture getTileFree() {
        return tileFree;
    }

    /**
     * This method returns the tile blocked texture.
     *
     * @return the tile blocked texture
     */
    public Texture getTileBlocked() {
        return tileBlocked;
    }

    /**
     * This method returns the tile player one texture.
     *
     * @return the tile player one texture
     */
    public Texture getTilePlayerOne() {
        return tilePlayerOne;
    }

    /**
     * This method returns the tile player two texture.
     *
     * @return the tile player two texture
     */
    public Texture getTilePlayerTwo() {
        return tilePlayerTwo;
    }

    /**
     * This method returns the tile player one selected texture.
     *
     * @return the tile player one selected texture
     */
    public Texture getTilePlayerOneSelected() {
        return tilePlayerOneSelected;
    }

    /**
     * This method returns the tile player two selected texture.
     *
     * @return the tile player two selected texture
     */
    public Texture getTilePlayerTwoSelected() {
        return tilePlayerTwoSelected;
    }

    /**
     * This method returns the tile move one texture.
     *
     * @return the tile move one texture
     */
    public Texture getTileMoveOne() {
        return tileMoveOne;
    }

    /**
     * This method returns the tile move two texture.
     *
     * @return the tile move two texture
     */
    public Texture getTileMoveTwo() {
        return tileMoveTwo;
    }

    /**
     * This method returns the background texture.
     *
     * @return the background texture
     */
    public Texture getBackground() {
        return background;
    }

    /**
     * This method returns the particle effect.
     *
     * @return the particle effect
     */
    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }
}
