package de.johannesrauch.hexxagon.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
    private final Texture loading;

    public Resources() {
        this("quantum-horizon");
    }

    public Resources(String skinName) {
        skin = new Skin(Gdx.files.internal(skinName + "/skin/" + skinName + "-ui.json"));
        tileFree = new Texture(Gdx.files.internal("TileFree.png"));
        tileBlocked = new Texture(Gdx.files.internal("TileBlocked.png"));
        tilePlayerOne = new Texture(Gdx.files.internal("TilePlayerOne.png"));
        tilePlayerTwo = new Texture(Gdx.files.internal("TilePlayerTwo.png"));
        tilePlayerOneSelected = new Texture(Gdx.files.internal("TilePlayerOneSelected.png"));
        tilePlayerTwoSelected = new Texture(Gdx.files.internal("TilePlayerTwoSelected.png"));
        tileMoveOne = new Texture(Gdx.files.internal("TileMoveOne.png"));
        tileMoveTwo = new Texture(Gdx.files.internal("TileMoveTwo.png"));
        loading = new Texture(Gdx.files.internal("loading.png"));
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getTileFree() {
        return tileFree;
    }

    public Texture getTileBlocked() {
        return tileBlocked;
    }

    public Texture getTilePlayerOne() {
        return tilePlayerOne;
    }

    public Texture getTilePlayerTwo() {
        return tilePlayerTwo;
    }

    public Texture getTilePlayerOneSelected() {
        return tilePlayerOneSelected;
    }

    public Texture getTilePlayerTwoSelected() {
        return tilePlayerTwoSelected;
    }

    public Texture getTileMoveOne() {
        return tileMoveOne;
    }

    public Texture getTileMoveTwo() {
        return tileMoveTwo;
    }

    public Texture getLoading() {
        return loading;
    }
}
