package de.johannesrauch.hexxagon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.johannesrauch.hexxagon.Hexxagon;

/**
 * TODO: comment
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class DesktopLauncher {
	
	/**
	 * TODO: comment
	 * @param args Kommandozeilenparameter
	 */
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 576;

		new LwjglApplication(new Hexxagon(), config);
	}
}
