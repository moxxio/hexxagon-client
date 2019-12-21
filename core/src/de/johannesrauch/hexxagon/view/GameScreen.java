package de.johannesrauch.hexxagon.view;

import java.util.HashMap;

import de.johannesrauch.hexxagon.model.GameScreenTile;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.johannesrauch.hexxagon.controller.GameHandler;
import de.johannesrauch.hexxagon.controller.Hexxagon;

public class GameScreen implements Screen {
	
	final Logger logger = LoggerFactory.getLogger(GameScreen.class);

	private Hexxagon parent;
	private GameHandler gameHandler;
	
	private OrthographicCamera camera;
	private StretchViewport viewport;
	private Stage stage;

	private Image loadingImage;
	
	private Label playerOneLabel;
	private Label playerTwoLabel;
	private Label leaveGameLabel;
	
	public HashMap<TileEnum, GameScreenTile> gameScreenTiles;
	
	boolean moveFromSelected = false;
	boolean moveToSelected = false;
	
	TileEnum moveFrom = null;
	TileEnum moveTo = null;
	
	boolean gameHandlerInitializationCompleted = false;
	
	boolean dialogOpened = false;
	
	// TODO: entfernen Sie dieses Attribut, es dient nur dazu, dass Sie initial ein Spielfeld angezeigt bekommen
	//       Diese Funktionalität werden Sie nun selbst schreiben.
	boolean debug = true;
	
	public GameScreen(Hexxagon parent, GameHandler gameHandler) {
		
		this.parent = parent;
		this.gameHandler = gameHandler;
		
		camera = new OrthographicCamera(1024, 576);    	
    	viewport = new StretchViewport(1024, 576, camera);
    	stage = new Stage(viewport);
    	
    	parent.particleEffect.start();
    	parent.particleEffect.setPosition(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
    	
    	loadingImage = new Image(parent.loading);
		loadingImage.setSize(128, 128);
		loadingImage.setPosition(0, viewport.getScreenHeight() - loadingImage.getHeight());
		loadingImage.setOrigin(64, 64);
		
		playerOneLabel = new Label("", parent.skin);
		playerTwoLabel = new Label("", parent.skin);
		leaveGameLabel = new Label("", parent.skin);
		
		gameScreenTiles = new HashMap<TileEnum, GameScreenTile>();
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // TODO: Dieser Codeblock könnte verbessert werden
        //       Zum Beispiel durch einen Dialog, der sich öffnet und auf dem ein Button "YES, EXIT GAME" geklickt werden muss,
        //       um die Spielpartie zu verlassen.
        if (Gdx.input.isKeyPressed(Keys.F10) && !dialogOpened) {
        	logger.info("F10 was pressed, leaving game now.");
        	// TODO: an dieser Stelle muss eine GameLeave Message gesendet werden
        	parent.showMainMenuScreen();
        }
        
        parent.spriteBatch.setProjectionMatrix(camera.combined);
        parent.spriteBatch.begin();
        parent.spriteBatch.draw(parent.space, 0, 0, 1024, 576);
        if (parent.particleEffect.isComplete()) parent.particleEffect.reset(); else parent.particleEffect.draw(parent.spriteBatch, delta);
        parent.spriteBatch.end();
        
        if (loadingImage != null) loadingImage.rotateBy(delta * -90.0f);

        // Falls die Initialisierung des GameHandlers nicht abgeschlossen ist, aber die Initialisierung des GameScreens abgeschlossen ist, so ist etwas falsch gelaufen.
        // In diesem Fall werden Aufräumarbeiten durchgeführt.
        if (!gameHandler.initializationCompleted && gameHandlerInitializationCompleted) {
        	logger.error("GameScreen initialization states not matching.");
        	gameHandlerInitializationCompleted = false;
        }
        // Falls die Inititalisierugn des GameHandlers abgeschlossen ist, jedoch die des GameScreens nicht, wird der GameScreen initialisiert. 
        else if (gameHandler.initializationCompleted && !gameHandlerInitializationCompleted || debug) {
        	// initialize gameScreenTiles
        	initializeGameScreenTiles();
        	// change gameHandlerInitializationCompleted to true
        	gameHandlerInitializationCompleted = true;
        	
        	debug = false;
        }
        // Das Spielfeld wird gerendert, falls alle Initialisierungen abgeschlossen sind.
        else if (gameHandler.initializationCompleted && gameHandlerInitializationCompleted || !debug) {
        	
        	// show or hide loading animation
        	if ((gameHandler.clientIsPlayerOne && gameHandler.isPlayerOneMove) || (gameHandler.clientIsPlayerTwo && gameHandler.isPlayerTwoMove)) {
        		if (loadingImage.isVisible()) {
        			loadingImage.setVisible(false);
        		}
        	}
        	else {
        		if (!loadingImage.isVisible()) {
        			loadingImage.setVisible(true);
        		}
        	}
        	
        	// TODO: Überprüfen Sie, ob sich am Zustand des Spielfelds etwas geändert hat
        	//       Falls eine Änderung vorliegt, aktuallisieren Sie die Kacheln in der Stage
        	//       danach soll der aktuelle Zustand des Spielfeldes gezeichnet werden.
        	
        	stage.act();
            stage.draw();
        }       
        
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		// TODO: geben Sie alle Ressourcen mit implementiertem Disposable Interface frei
	}
	
	private void initializeGameScreenTiles() {
		
		stage.clear();
		gameScreenTiles.clear();
		
		for (int i = 0; i < 61; i++) {
			
			final int index = i + 1;
			
			int startposx = 512;
			int startposy = 576;
			
			int sizex = 96;
			int sizey = 64;
			
			int offsetx = 80;
			
			startposx -= sizex / 2;
			startposy -= sizey;
			
			int posx, posy;
			
			if (i < 5) {
				posx = startposx - offsetx * 4;
				posy = startposy - 2 * sizey - i * sizey;
			}
			else if (i < 11) {
				posx = startposx - offsetx * 3;
				posy = startposy - sizey - sizey / 2 - (i - 5) * sizey;
			}
			else if (i < 18) {
				posx = startposx - offsetx * 2;
				posy = startposy - sizey - (i - 11) * sizey;
			}
			else if (i < 26) {
				posx = startposx - offsetx;
				posy = startposy - sizey / 2 - (i - 18) * sizey;
			}
			else if (i < 35) {
				posx = startposx;
				posy = startposy - (i - 26) * sizey;
			}
			else if (i < 43) {
				posx = startposx + offsetx;
				posy = startposy - sizey / 2 - (i - 35) * sizey;
			}
			else if (i < 50) {
				posx = startposx + offsetx * 2;
				posy = startposy - sizey - (i - 43) * sizey;
			}
			else if (i < 56) {
				posx = startposx + offsetx * 3;
				posy = startposy - sizey - sizey / 2 - (i - 50) * sizey;
			}
			else if (i < 61) {
				posx = startposx + offsetx * 4;
				posy = startposy - 2 * sizey - (i - 56) * sizey;
			}
			else {
				posx = posy = 0;
			}
			
			Image image = new Image();
			image.setPosition(posx, posy);
			image.setSize(sizex, sizey);
			
			image.setDrawable(new SpriteDrawable(new Sprite(parent.tileFree)));
			image.addListener(new ClickListener() {
				    @Override
				    public void clicked(InputEvent event, float x, float y) {
				        System.out.println("You clicked an image: " + index);
				        
				        // TODO: nachdem auf eine Kachel geklickt wurde, sollte hier etwas sinnvolles passieren
				    }
				}
			);
			
			// TODO: hier sollte die Kachel dahingehend angepasst werden, dass der richtige Kachelzustand gesetzt ist
			//       Kachel frei, Kachel blockiert, Kachel belegt durch Spielstein Spieler 1/2
			
			stage.addActor(image);
		}
		
		
		// TODO: zeigen Sie für Spieler 1 den Benutzernamen und die Punktezahl an
		playerOneLabel.setText("USERNAME1" + ": " + "XYZ" + " points");
		playerOneLabel.setPosition(15, 50);

		// TODO: zeigen Sie für Spieler 2 den Benutzernamen und die Punktezahl an
		playerTwoLabel.setText("USERNAME2" + ": " + "XYZ" + " points");
		playerTwoLabel.setPosition(15, 25);
		
		leaveGameLabel.setText("[F10] LEAVE GAME");
		leaveGameLabel.setPosition(850, 25);
		
		stage.addActor(playerOneLabel);
		stage.addActor(playerTwoLabel);
		stage.addActor(leaveGameLabel);
		
		stage.addActor(loadingImage);
		
				
	}

}
