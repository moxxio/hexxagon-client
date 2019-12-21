package de.johannesrauch.hexxagon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.johannesrauch.hexxagon.automaton.states.DisconnectedState;
import de.johannesrauch.hexxagon.automaton.context.StateContext;
import de.johannesrauch.hexxagon.automaton.states.State;
import de.johannesrauch.hexxagon.controller.*;
import de.johannesrauch.hexxagon.network.clients.MessageEmitter;
import de.johannesrauch.hexxagon.network.clients.MessageReceiver;
import de.johannesrauch.hexxagon.view.GameScreen;
import de.johannesrauch.hexxagon.view.LobbyJoinedScreen;
import de.johannesrauch.hexxagon.view.LobbySelectScreen;
import de.johannesrauch.hexxagon.view.MainMenuScreen;

import java.awt.*;

/**
 * Sources of background images:
 * https://wallpaperaccess.com/real-hd-space
 * https://wallpaperaccess.com/deep-space-hd
 * 
 * @version 0.3.1.0
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class Hexxagon extends Game {

	public final static String versionNumber = "0.3.1.0";

	private final StateContext stateContext;
	
	private ConnectionHandler connectionHandler;
	private MessageEmitter messageEmitter;
	private MessageReceiver messageReceiver;
	private LobbyHandler lobbyHandler;
	private GameHandler gameHandler;

	private MainMenuScreen mainMenuScreen;
	private LobbySelectScreen lobbySelectScreen;
	private LobbyJoinedScreen lobbyJoinedScreen;
	private GameScreen gameScreen;

	public SpriteBatch spriteBatch;

	public Texture space;
	public Texture tileFree, tileBlocked, tilePlayerOne, tilePlayerTwo, tileMoveOne, tileMoveTwo, tilePlayerOneSelected, tilePlayerTwoSelected;
	public Texture loading;
	public Skin skin;
	public ParticleEffect particleEffect;

	public Hexxagon() {
		stateContext = new StateContext();
	}

	/**
	 * @author Dennis Jehle
	 * @author Johannes Rauch
	 */
	@Override
	public void create () {
		Gdx.graphics.setTitle("Hexxagon");

		space = new Texture("space.jpg");
		skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
		tileFree = new Texture(Gdx.files.internal("TileFree.png"));
		tileBlocked = new Texture(Gdx.files.internal("TileBlocked.png"));
		tilePlayerOne = new Texture(Gdx.files.internal("TilePlayerOne.png"));
		tilePlayerTwo = new Texture(Gdx.files.internal("TilePlayerTwo.png"));
		tilePlayerOneSelected = new Texture(Gdx.files.internal("TilePlayerOneSelected.png"));
		tilePlayerTwoSelected = new Texture(Gdx.files.internal("TilePlayerTwoSelected.png"));
		tileMoveOne = new Texture(Gdx.files.internal("TileMoveOne.png"));
		tileMoveTwo = new Texture(Gdx.files.internal("TileMoveTwo.png"));
		loading = new Texture(Gdx.files.internal("loading.png"));
		loading.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		spriteBatch = new SpriteBatch();

		particleEffect = new ParticleEffect();
    	particleEffect.load(Gdx.files.internal("slowbuzz.p"), Gdx.files.internal(""));

		connectionHandler = new ConnectionHandler();
		messageReceiver = new MessageReceiver();
		lobbyHandler = new LobbyHandler(this);
		gameHandler = new GameHandler();
		messageEmitter = new MessageEmitter();

		messageReceiver.setParent(this);
		messageReceiver.setConnectionHandler(connectionHandler);
		messageReceiver.setLobbyHandler(lobbyHandler);
		messageReceiver.setGameHandler(gameHandler);
		messageReceiver.setMessageEmitter(messageEmitter);

		gameHandler.setMessageEmitter(messageEmitter);

		messageEmitter.setConnectionHandler(connectionHandler);

		stateContext.setParent(this);
		stateContext.setConnectionHandler(connectionHandler);
		stateContext.setMessageEmitter(messageEmitter);
		stateContext.setMessageReceiver(messageReceiver);
		stateContext.setGameHandler(gameHandler);

		mainMenuScreen = new MainMenuScreen(this);
		lobbySelectScreen = new LobbySelectScreen(this);
		lobbyJoinedScreen = new LobbyJoinedScreen(this, lobbyHandler);
		gameScreen = new GameScreen(this, gameHandler);

		messageReceiver.setLobbySelectScreen(lobbySelectScreen);

		this.showMainMenuScreen();
		// this.showGameScreen();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		// TODO
	}

	public StateContext getStateContext() {
		return stateContext;
	}

	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public MessageEmitter getMessageEmitter() {
		return messageEmitter;
	}

	public MessageReceiver getMessageReceiver() {
		return messageReceiver;
	}

	public LobbyHandler getLobbyHandler() {
		return lobbyHandler;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public LobbySelectScreen getLobbySelectScreen() {
		return lobbySelectScreen;
	}

	public LobbyJoinedScreen getLobbyJoinedScreen() {
		return lobbyJoinedScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

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
}
