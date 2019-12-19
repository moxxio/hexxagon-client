package de.johannesrauch.hexxagon.network.board;

import java.util.HashMap;

public class Board {
	
	private HashMap<TileEnum, TileStateEnum> tiles;
	
	public Board() {
		tiles = new HashMap<TileEnum, TileStateEnum>();
	}
	
	public TileStateEnum getTile(TileEnum tile) {
		// check if tile is null
		if (tile == null) return null;
		// return tile state
		return tiles.get(tile);
	}
	
	public void updateTile(TileEnum tile, TileStateEnum tileState) {
		// check if tile is null or tileState is null
		if (tile == null || tileState == null) return;
		// update tile
		tiles.put(tile, tileState);
	}
	
	public boolean validate() {
		
		// validate tiles
		for (int i = 1; i <= 61; i++) {
			if (tiles.get(TileEnum.valueOf("TILE_" + i)) == null) {
				return false;
			}
		}
		
		// board is valid
		return true;
	}
	
}
