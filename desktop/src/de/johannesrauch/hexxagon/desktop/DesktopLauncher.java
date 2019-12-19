package de.johannesrauch.hexxagon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.johannesrauch.hexxagon.controller.Hexxagon;

/**
 * Die Klasse DesktopLauncher wird benötigt um das Spiel auf einem Desktop System zu starten.
 *
 * @author Dennis Jehle
 * @author Vorname Nachname
 */
public class DesktopLauncher {
	
	/**
	 * Die main Methode dient als Programmeinstieg.
	 * Innerhalb der Methode wird eine LwjglApplication erzeugt, diese erstellt ein Fenster
	 * und startet das eigentliche Spiel Hexxagon, welches sich im core Projekt befindet.
	 * 
	 * @param arg Kommandozeilenparameter
	 */
	public static void main (String[] arg) {
		// Neue Konfigurationsdatei erzeugen
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Breite des Fensters festlegen
		config.width = 1024;
		// Hoehe des Fensters festlegen
		config.height = 576;
		// Fenster erzeugen und Hexxagon starten
		new LwjglApplication(new Hexxagon(), config);
	}
}