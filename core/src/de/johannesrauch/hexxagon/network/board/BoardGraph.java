package de.johannesrauch.hexxagon.network.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the game board. Each tile is adjacent to those tile in the graph,
 * which are adjacent on the board.
 *
 * @author Dennis Jehle
 * @author Johannes Rauch
 */
public class BoardGraph {

    private HashMap<TileEnum, List<TileEnum>> tileMapping;

    public BoardGraph() {
        tileMapping = new HashMap<>();
        generateTileMapping();
    }

    private List<TileEnum> assembleTileEnumList(Integer... tiles) {
        List<TileEnum> list = new ArrayList<>();
        if (tiles != null) {
            for (Integer t : tiles) {
                list.add(getTileEnumFromInt(t));
            }
        }
        return list;
    }

    private void generateTileMapping() {
        int a = -5, b = -4, c = 0, d = 2, e = 6, f = 7;
        for (int t = 1; t <= 61; t++) {
            TileEnum tile = getTileEnumFromInt(t);
            switch (t) {
                case 1:
                    tileMapping.put(tile, assembleTileEnumList(d, e, f));
                    break;
                case 2:
                case 3:
                case 4:
                    tileMapping.put(tile, assembleTileEnumList(c, d, e, f));
                    break;
                case 5:
                    tileMapping.put(tile, assembleTileEnumList(c, e, f));
                    ++e;
                    ++f;
                    break;
                case 6:
                case 12:
                case 19:
                    tileMapping.put(tile, assembleTileEnumList(b, d, e, f));
                    if (t == 12 || t == 19) {
                        --a;
                    }
                    break;
                case 11:
                case 18:
                case 26:
                    tileMapping.put(tile, assembleTileEnumList(a, c, e, f));
                    if (t == 11 || t == 18) {
                        ++e;
                        ++f;
                    }
                    --b;
                    break;
                case 27:
                    tileMapping.put(tile, assembleTileEnumList(b, d, f));
                    --a;
                    break;
                case 35:
                    tileMapping.put(tile, assembleTileEnumList(a, c, e));
                    --f;
                    break;
                case 36:
                case 44:
                case 51:
                    tileMapping.put(tile, assembleTileEnumList(a, b, d, f));
                    --e;
                    break;
                case 43:
                case 50:
                case 56:
                    tileMapping.put(tile, assembleTileEnumList(a, b, c, e));
                    ++a;
                    ++b;
                    --f;
                    break;
                case 57:
                    tileMapping.put(tile, assembleTileEnumList(a, b, d));
                    break;
                case 58:
                case 59:
                case 60:
                    tileMapping.put(tile, assembleTileEnumList(a, b, c, d));
                    break;
                case 61:
                    tileMapping.put(tile, assembleTileEnumList(a, b, c));
                    break;
                default:
                    tileMapping.put(tile, assembleTileEnumList(a, b, c, d, e, f));
                    break;
            }
            a++; b++; c++; d++; e++; f++;
        }
    }

    private TileEnum getTileEnumFromInt(int t) {
        return TileEnum.valueOf("Tile_" + t);
    }
}
