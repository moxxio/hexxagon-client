package de.johannesrauch.hexxagon.network.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Diese Klasse wird dazu verwendet, den Graphen zu erstellen, welcher das Spielfeld repräsentiert.
 * Sie müssen die Funktionsweise dieser Klasse nicht nachvollziehen, können allerdings gerne die Methoden verwenden.
 * Die toString Methode ist nützlich um Debug Ausgaben mit dem aktuellen Zustand der Klasse zu erzeugen.
 * 
 * @author Dennis Jehle 
 */
public class BoardGraph {
	
	public Board board;
	public HashMap<TileEnum, List<TileEnum>> tileMapping;
	
	
	
	public BoardGraph() {
		tileMapping = new HashMap<TileEnum, List<TileEnum>>();
		generateTileMapping();
	}
	
	// generate tileMapping
	
	private void generateTileMapping() {
		recursiveTileMapping(1, -5, -4, 0, 2, 6, 7);
	}
	
	private TileEnum assembleTileEnum(int t) {
		return TileEnum.valueOf("TILE_" + t);
	}
	
	private List<TileEnum> assembleTileEnumList(Integer... tiles) {
		List<TileEnum> list = new ArrayList<>();
		for (Integer t : tiles) {
			list.add(assembleTileEnum(t));
		}
		return list;
	}
	
	private void recursiveTileMapping(int t, int a, int b, int c, int d, int e, int f) {
		
		if (t == 62) return;
		
		TileEnum tile = assembleTileEnum(t);
		
		switch (t) {
			case 1:
				tileMapping.put(tile, assembleTileEnumList(d,e,f));
				break;
			case 2:
			case 3:
			case 4:
				tileMapping.put(tile, assembleTileEnumList(c,d,e,f));
				break;
			case 5:
				tileMapping.put(tile, assembleTileEnumList(c,e,f));
				++e;++f;
				break;
			case 6:
			case 12:
			case 19:
				tileMapping.put(tile, assembleTileEnumList(b,d,e,f));
				if (t == 12 || t == 19) {
					--a;
				}
				break;
			case 11:
			case 18:
			case 26:
				tileMapping.put(tile, assembleTileEnumList(a,c,e,f));
				if (t == 11 || t == 18) {
					++e;++f;
				}
				--b;
				break;
			case 27:
				tileMapping.put(tile, assembleTileEnumList(b,d,f));
				--a;
				break;
			case 35:
				tileMapping.put(tile, assembleTileEnumList(a,c,e));
				--f;
				break;
			case 36:
			case 44:
			case 51:
				tileMapping.put(tile, assembleTileEnumList(a,b,d,f));
				--e;
				break;
			case 43:
			case 50:
			case 56:
				tileMapping.put(tile, assembleTileEnumList(a,b,c,e));
				++a;++b;--f;
				break;
			case 57:
				tileMapping.put(tile, assembleTileEnumList(a,b,d));
				break;
			case 58:
			case 59:
			case 60:
				tileMapping.put(tile, assembleTileEnumList(a,b,c,d));
				break;
			case 61:
				tileMapping.put(tile, assembleTileEnumList(a,b,c));
				break;
			default:
				tileMapping.put(tile, assembleTileEnumList(a,b,c,d,e,f));
				break;
		}
		
		recursiveTileMapping(t+1, a+1, b+1, c+1, d+1, e+1, f+1);
	}
	
	// print board
	
	private String getTileStateShort(int tileNumber) {
		TileStateEnum tse = board.getTile(TileEnum.valueOf("TILE_" + tileNumber));
		switch (tse) {
			case FREE:
				return "████";
			case PLAYERONE:
				return "PL01";
			case PLAYERTWO:
				return "PL02";
			case BLOCKED:
				return "BLKD";
		}
		return "ERR!";
	}
	
	@Override
	public String toString() {
		
		String result = "\n\n";
		
		result += String.format("                  ┏━━━%4s━━━┓       \n", getTileStateShort(27));
		result += String.format("            ┏━━━%4s━━━╋━━━%4s━━━┓     \n", getTileStateShort(19), getTileStateShort(36));
		result += String.format("       ┏━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┓   \n", getTileStateShort(12), getTileStateShort(28), getTileStateShort(44));
		result += String.format(" ┏━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┓ \n", getTileStateShort(6), getTileStateShort(20), getTileStateShort(37), getTileStateShort(51));
		result += String.format("%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s\n", getTileStateShort(1), getTileStateShort(13), getTileStateShort(29), getTileStateShort(45), getTileStateShort(57));
		result += String.format(" ┣━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┫ \n", getTileStateShort(7), getTileStateShort(21), getTileStateShort(38), getTileStateShort(52));
		result += String.format("%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s\n", getTileStateShort(2), getTileStateShort(14), getTileStateShort(30), getTileStateShort(46), getTileStateShort(58));
		result += String.format(" ┣━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┫ \n", getTileStateShort(8), getTileStateShort(22), getTileStateShort(39), getTileStateShort(53));
		result += String.format("%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s\n", getTileStateShort(3), getTileStateShort(15), getTileStateShort(31), getTileStateShort(47), getTileStateShort(59));
		result += String.format(" ┣━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┫ \n", getTileStateShort(9), getTileStateShort(23), getTileStateShort(40), getTileStateShort(54));
		result += String.format("%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s\n", getTileStateShort(4), getTileStateShort(16), getTileStateShort(32), getTileStateShort(48), getTileStateShort(60));
		result += String.format(" ┣━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┫ \n", getTileStateShort(10), getTileStateShort(24), getTileStateShort(41), getTileStateShort(55));
		result += String.format("%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s\n", getTileStateShort(5), getTileStateShort(17), getTileStateShort(33), getTileStateShort(49), getTileStateShort(61));
		result += String.format(" ┗━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┛ \n", getTileStateShort(11), getTileStateShort(25), getTileStateShort(42), getTileStateShort(56));
		result += String.format("       ┗━━━%4s━━━╋━━━%4s━━━╋━━━%4s━━━┛   \n", getTileStateShort(18), getTileStateShort(34), getTileStateShort(50));
		result += String.format("            ┗━━━%4s━━━╋━━━%4s━━━┛     \n", getTileStateShort(26), getTileStateShort(43));
		result += String.format("                  ┗━━━%4s━━━┛       \n", getTileStateShort(35));
		
		return result;
	}
	
	
}
