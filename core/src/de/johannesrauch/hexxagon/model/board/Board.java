package de.johannesrauch.hexxagon.model.board;

import de.johannesrauch.hexxagon.model.tile.TileEnum;
import de.johannesrauch.hexxagon.model.tile.TileStateEnum;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class represents a state of the game board.
 */
public class Board {

    private Map<TileEnum, TileStateEnum> tiles;

    /**
     * This is the standard constructor. It creates the tile map.
     */
    public Board() {
        this.tiles = new EnumMap<>(TileEnum.class);
    }

    /**
     * This method returns the tile map.
     *
     * @return the tile map
     */
    public Map<TileEnum, TileStateEnum> getTiles() {
        return tiles;
    }

    /**
     * This method returns the state of a specific tile.
     *
     * @param tile the tile you want the state of
     * @return the state of the given tile
     */
    public TileStateEnum getTileState(TileEnum tile) {
        return tiles.get(tile);
    }
}
