package de.johannesrauch.hexxagon.test;

import de.johannesrauch.hexxagon.model.board.BoardGraph;
import de.johannesrauch.hexxagon.model.tile.TileEnum;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the board and board graph functionality.
 *
 * @author Johannes Rauch
 */
public class BoardTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * This method tests if tile 1 only has tile 2, 6 and 7 as neighbors in the board graph.
     *
     * @author Dennis Jehle
     * @author Johannes Rauch
     */
    @Test
    public void testTile1Neighbors() {
        BoardGraph boardGraph = new BoardGraph();
        List<TileEnum> neighbors = boardGraph.getNeighborsOf(TileEnum.TILE_1);
        for (TileEnum tile : TileEnum.values()) {
            if (tile == TileEnum.TILE_2
                    || tile == TileEnum.TILE_6
                    || tile == TileEnum.TILE_7) assertTrue(neighbors.contains(tile));
            else assertFalse(neighbors.contains(tile));
        }
    }

    /**
     * This method tests if tile 35 only has tile 26, 34 and 43 as neighbors in the board graph.
     *
     * @author Johannes Rauch
     */
    @Test
    public void testTile35Neighbors() {
        BoardGraph boardGraph = new BoardGraph();
        List<TileEnum> neighbors = boardGraph.getNeighborsOf(TileEnum.TILE_35);
        for (TileEnum tile : TileEnum.values()) {
            if (tile == TileEnum.TILE_26
                    || tile == TileEnum.TILE_34
                    || tile == TileEnum.TILE_43) assertTrue(neighbors.contains(tile));
            else assertFalse(neighbors.contains(tile));
        }
    }

    /**
     * This method tests if tile 31 only has tile 30, 22, 39, 23, 40 and 32 as neighbors in the board graph.
     *
     * @author Johannes Rauch
     */
    @Test
    public void testTile31Neighbors() {
        BoardGraph boardGraph = new BoardGraph();
        List<TileEnum> neighbors = boardGraph.getNeighborsOf(TileEnum.TILE_31);
        for (TileEnum tile : TileEnum.values()) {
            if (tile == TileEnum.TILE_30
                    || tile == TileEnum.TILE_22
                    || tile == TileEnum.TILE_39
                    || tile == TileEnum.TILE_23
                    || tile == TileEnum.TILE_40
                    || tile == TileEnum.TILE_32) assertTrue(neighbors.contains(tile));
            else assertFalse(neighbors.contains(tile));
        }
    }
}
