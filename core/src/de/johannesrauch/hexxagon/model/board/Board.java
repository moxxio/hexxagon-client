package de.johannesrauch.hexxagon.model.board;

import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;

import java.util.HashMap;

public class Board {

    private HashMap<TileEnum, TileStateEnum> tiles;

    public Board() {
        this.tiles = new HashMap<>();
    }

    public HashMap<TileEnum, TileStateEnum> getTiles() {
        return tiles;
    }
}
