package de.johannesrauch.hexxagon.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.johannesrauch.hexxagon.view.GameScreen;
import de.johannesrauch.hexxagon.view.LobbyJoinedScreen;
import de.johannesrauch.hexxagon.view.LobbySelectScreen;
import de.johannesrauch.hexxagon.view.MainMenuScreen;

/**
 * Sopra1920 Hexxagon Client
 * 
 * Die Hexxagon Klasse implementiert das Interface com.badlogic.gdx.Game,
 * dieses Interface ermöglicht es dem Entwickler zwischen mehreren Screens umzuschalten.
 * 
 * Eine Klasse muss das com.badlogic.gdx.Screen Interface implementieren um als Screen verwendet werden zu können.
 * In dieser Vorlage finden Sie unter anderem folgende vier Klassen:
 *      MainMenuScreen, LobbySelectScreen, LobbyJoinedScreen und GameScreen
 * jede dieser Klassen implementiert das com.badlogic.gdx.Screen Interface und kann somit als Screen verwendet werden.
 * 
 * Folgende Methoden sind aus dem com.badlogic.gdx.Game Interace:
 *
 *      create(): wird aufgerufen wenn die Applikation gestartet wird, dies passiert in der Klasse DesktopLauncher.java
 *      render(): wird aufgerufen wenn die Applikation sich neu zeichnen soll, der Aufruf super.render() ist notwendig, es wird zusätzlich die render Methode im aktiven Screen aufgerufen
 *      dispose(): wird aufgerufen wenn die Applikation beendet wird. Innerhalb der dispose Methode müssen alle Resourcen welche das Disposable Interface implementieren freigegeben werden.
 *                 Sie können eine Ressouce mit dem Befehl xyz.dispose() freigeben.
 *                 Hier finden Sie alle Klassen, welche das Disposable Interface implementieren: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/utils/Disposable.html
 * 
 * Innerhalb dieser Klasse sind einige Attribute als 'public static' deklariert.
 * Hierbei handelt es sich um:
 *      SpriteBatch
 *      Texture
 *      Skin
 * dies hat folgenden Grund, alle diese Ressourcen werden auf anderen Screens verwendet, um nicht auf jedem Screen erneut die Ressource von der Platte
 * zu laden, passiert es hier zentral. Aus den anderen Klassen kann dann über Hexxagon.xyz auf die entsprechende Ressource zugegriffen werden.
 * 
 * Sources of background images:
 * https://wallpaperaccess.com/real-hd-space
 * https://wallpaperaccess.com/deep-space-hd
 * 
 * @version 0.3.1.0
 * @author Dennis Jehle
 * @author Vorname Nachname
 */
public class Hexxagon extends Game {

	// Versionsnummer des Spiels Hexxagon
	public final static String versionNumber = "0.3.1.0";
	
	private MessageReceiver messageReceiver;
	private ConnectionHandler connectionHandler;
	private LobbyHandler lobbyHandler;
	private GameHandler gameHandler;
	private MessageEmitter messageEmitter;
	
	private MainMenuScreen mainMenuScreen;
	private LobbySelectScreen lobbySelectScreen;
	private LobbyJoinedScreen lobbyJoinedScreen;
	private GameScreen gameScreen;
	
	// Ressourcen mit Disposable Interface
	public static SpriteBatch spriteBatch;
	public static Texture space;
	public static Texture tileFree, tileBlocked, tilePlayerOne, tilePlayerTwo, tileMoveOne, tileMoveTwo, tilePlayerOneSelected, tilePlayerTwoSelected;
	public static Texture loading;
	public static Skin skin;
	public static ParticleEffect particleEffect;
	
	public void showMainMenuScreen() {
		this.setScreen(mainMenuScreen);
	}
	
	public void showLobbySelectScreen() {
		this.setScreen(lobbySelectScreen);
	}
	
	public void showLobbyJoinedScreen() {
		this.setScreen(lobbyJoinedScreen);
	}
	
	public void showGameScreen() {
		this.setScreen(gameScreen);
	}
	
	/**
	 * Diese Methode wird direkt nach dem Start der Applikation aufgerufen.
	 * Innerhalb dieser Methode werden alle Schritte unternommen, um das Spiel zu initialisieren.
	 * 
	 * Ablauf der Methode:
	 *      1. Titelleiste des Anwendungsfensters anpassen
	 *      2. Laden der Hintergrundgrafik
	 *      3. Laden des Skins zur Gestaltung von UI Komponenten
	 *      4. Laden der Texturen zur Darstellung des Spielfeldes
	 *      5. Laden der Textur zur Darstellung der Lade/Warteanimation
	 *
	 * 
	 * @author Dennis Jehle
	 */
	@Override
	public void create () {
		// Fenstertitel ändern
		Gdx.graphics.setTitle("Hexxagon - Sopra 2019 / 2020 - Client");
		
		// Hintergrundgrafik laden
		space = new Texture("space.jpg");
		// Skin laden
		skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
		// Texturen für Spielfeld laden
		tileFree = new Texture(Gdx.files.internal("TileFree.png"));
		tileBlocked = new Texture(Gdx.files.internal("TileBlocked.png"));
		tilePlayerOne = new Texture(Gdx.files.internal("TilePlayerOne.png"));
		tilePlayerTwo = new Texture(Gdx.files.internal("TilePlayerTwo.png"));
		tilePlayerOneSelected = new Texture(Gdx.files.internal("TilePlayerOneSelected.png"));
		tilePlayerTwoSelected = new Texture(Gdx.files.internal("TilePlayerTwoSelected.png"));
		tileMoveOne = new Texture(Gdx.files.internal("TileMoveOne.png"));
		tileMoveTwo = new Texture(Gdx.files.internal("TileMoveTwo.png"));
		// Textur für Ladeanimation laden
		loading = new Texture(Gdx.files.internal("loading.png"));
		loading.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// SpiteBatch erzeugen
		spriteBatch = new SpriteBatch();
		
		// Partikeleffekt erzeugen
		particleEffect = new ParticleEffect();
    	particleEffect.load(Gdx.files.internal("slowbuzz.p"), Gdx.files.internal(""));
		
		connectionHandler = new ConnectionHandler();
		messageReceiver = new MessageReceiver();
		lobbyHandler = new LobbyHandler();
		gameHandler = new GameHandler();
		messageEmitter = new MessageEmitter();

		messageReceiver.setParent(this);
		messageReceiver.setConnectionHandler(connectionHandler);
		messageReceiver.setLobbyHandler(lobbyHandler);
		messageReceiver.setGameHandler(gameHandler);
		
		lobbyHandler.setMessageEmitter(messageEmitter);
		
		gameHandler.setMessageEmitter(messageEmitter);
		
		messageEmitter.setConnectionHandler(connectionHandler);
		
		mainMenuScreen = new MainMenuScreen(this, connectionHandler, messageReceiver, gameHandler, messageEmitter); 
		lobbySelectScreen = new LobbySelectScreen(this, lobbyHandler, messageEmitter);
		lobbyJoinedScreen = new LobbyJoinedScreen(this, lobbyHandler);
		gameScreen = new GameScreen(this, gameHandler);
		
		messageReceiver.setLobbySelectScreen(lobbySelectScreen);
		messageReceiver.setGameScreen(gameScreen);
		
		// Den Hauptmenü Screen als aktiven Screen setzen
		this.showMainMenuScreen();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		// TODO: geben Sie alle Ressourcen mit implementiertem Disposable Interface frei
	}
}
