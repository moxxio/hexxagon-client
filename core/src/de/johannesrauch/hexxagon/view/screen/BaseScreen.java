package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.Hexxagon;

public abstract class BaseScreen implements Screen {

    protected Hexxagon parent;

    protected OrthographicCamera camera;
    protected StretchViewport viewport;
    protected Stage stage;

    public BaseScreen(Hexxagon parent) {
        this.parent = parent;

        camera = new OrthographicCamera(1280, 720);
        viewport = new StretchViewport(1280, 720, camera);
        stage = new Stage();
    }
}
