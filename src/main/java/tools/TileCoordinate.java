package tools;

import graphics.layers.levels.Level;

public class TileCoordinate {
	
	private int x = 0, y= 0;
	private final int TILE_SIZE = Level.BLOCK_SIZE;
	
	public TileCoordinate(int x, int y){
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int[] xy(){
		int[] r = new int[2];
		r[0] = x;
		r[1] = y;
		return r;
	}

}
