package de.johannesrauch.hexxagon.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GameScreenTile {
	
	private Image image;
	private GameScreenTileStateEnum tileState;

	public GameScreenTile(Image image, GameScreenTileStateEnum tileState) {
		this.image = image;
		this.tileState = tileState;
	}

	public void setDrawableOfImage(Drawable drawable) {
		image.setDrawable(drawable);
	}

	public void setTileState(GameScreenTileStateEnum tileState) {
		this.tileState = tileState;
	}
}
