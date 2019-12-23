package de.johannesrauch.hexxagon.model.board;

import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<TileEnum, TileStateEnum> tiles;

    public Board() {
        this.tiles = new HashMap<>();
    }

    public Map<TileEnum, TileStateEnum> getTiles() {
        return tiles;
    }

    public TileStateEnum getTileState(TileEnum tile) {
        return tiles.get(tile);
    }
}
