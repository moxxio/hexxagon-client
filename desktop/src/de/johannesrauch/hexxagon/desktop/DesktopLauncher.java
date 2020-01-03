package de.johannesrauch.hexxagon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.johannesrauch.hexxagon.Hexxagon;

/**
 * This class launches the hexxagon game application.
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class DesktopLauncher {

	/**
	 * This method is the entry point for the game app.
	 *
	 * @param args the command line arguments
	 */
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;

		new LwjglApplication(new Hexxagon(), config);
	}
}
