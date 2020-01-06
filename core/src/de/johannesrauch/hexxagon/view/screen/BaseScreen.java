package de.johannesrauch.hexxagon.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.Hexxagon;

/**
 * This is the base screen. It sets up the camera, the viewport and the stage.
 */
public abstract class BaseScreen implements Screen {

    protected Hexxagon parent;

    protected OrthographicCamera camera;
    protected ScreenViewport viewport;
    protected Stage stage;

    /**
     * This constructor sets everything up.
     *
     * @param parent the parent
     */
    public BaseScreen(Hexxagon parent) {
        this.parent = parent;

        camera = new OrthographicCamera(1280, 720);
        viewport = new ScreenViewport(camera);
        stage = new Stage();
    }
}
