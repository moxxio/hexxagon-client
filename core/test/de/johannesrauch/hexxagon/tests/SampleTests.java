package de.johannesrauch.hexxagon.tests;

import static org.junit.Assert.*;

import java.util.List;

import de.johannesrauch.hexxagon.network.board.BoardGraph;
import de.johannesrauch.hexxagon.network.board.TileEnum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleTests {

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
	 * Dieser UnitTest prüft, ob die Kachel 1 die Nachbarn Kachel 2, 6 und 7 hat.
	 * Außerdem wird geprüft, dass alle Kacheln außer Kachel 2,6,7 keine Nachbarn von Kachel 1 sind.
	 * 
	 * @author Dennis Jehle
	 */
	@Test
	public void checkTile1Neighbors() {
		BoardGraph boardGraph = new BoardGraph();
		List<TileEnum> neighbors = boardGraph.tileMapping.get(TileEnum.TILE_1);
		assertTrue(neighbors.contains(TileEnum.TILE_2));
		assertTrue(neighbors.contains(TileEnum.TILE_6));
		assertTrue(neighbors.contains(TileEnum.TILE_7));
		
		for (int i = 1; i <= 61; i++) {
			if (i == 2 || i == 6 || i == 7) continue;
			
			assertFalse(neighbors.contains(TileEnum.valueOf("TILE_" + i)));
		}
	}

}
