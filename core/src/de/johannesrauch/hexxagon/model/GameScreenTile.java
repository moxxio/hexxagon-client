package de.johannesrauch.hexxagon.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameScreenTile {
	
	public Image image;
	private GameScreenTileStateEnum tileState;
	public boolean tileStateChanged;
	
	public GameScreenTileStateEnum getTileState() {
		return tileState;
	}
	
	public void setTileState(GameScreenTileStateEnum tileState) {
		this.tileState = tileState;
		tileStateChanged = true;
	}
	
	public GameScreenTile() {
		this(null);
	}
	
	public GameScreenTile(Image image) {
		this(image, GameScreenTileStateEnum.BLOCKED);
	}
	
	public GameScreenTile(Image image, GameScreenTileStateEnum tileState) {
		this.image = image;
		setTileState(tileState);		
	}
	
}
