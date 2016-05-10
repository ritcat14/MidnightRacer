package graphics;

import entity.mob.Mob;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import graphics.tiles.Tile;
import graphics.layers.levels.Level;

public class Screen {

	public int width = 0;
	public int height = 0;
	public int[] pixels = null;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	public int xOffset = 0, yOffset = 0;
	public int BLOCK_SIZE = Level.BLOCK_SIZE;
	
	public static int WIDTH = 800, HEIGHT = 600;
	public static double SCALE = 1;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffffff;
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.SPRITE_HEIGHT; y++){
			int ya = y + yp;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				if(sheet.pixels[x + y * sheet.SPRITE_WIDTH] != 0xffff00ff) pixels[xa + ya * width] = pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++){
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if(col != 0xffff00ff) pixels[xa + ya * width] = color;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++){
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if(col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.getSprite().SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.getSprite().SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.getSprite().SIZE || xa >= width || ya < 0 || ya >= height) continue;
				if(xa < 0)xa=0;
				int col = tile.getSprite().pixels[x + y * tile.getSprite().getWidth()];
				if(col != 0xffff00ff && col != 0xff00ff) pixels[xa + ya * width] = col;
			}
		}
	}
  
	public void renderMob(int xp, int yp, Sprite sprite){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < BLOCK_SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < BLOCK_SIZE; x++) {
				int xa = x + xp;
				if (xa < -BLOCK_SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0)xa=0;
				int col = sprite.pixels[x + y * BLOCK_SIZE];
				if(col != 0xffff00ff)	pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < BLOCK_SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < BLOCK_SIZE; x++) {
				int xa = x + xp;
				if (xa < -BLOCK_SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0)xa=0;
				int col = mob.getSprite().pixels[x + y * BLOCK_SIZE];
				if(col != 0xffff00ff)	pixels[xa + ya * width] = col;
			}
		}
	}
  
    public void fillRect(int xp, int yp, int width, int height, int colour){
      for (int x = xp; x < xp + width; x++){
        for (int y = yp; y < yp + height; y++){
          pixels[x + y * width] = colour;
        }
      }
    }
  
    public void drawRect(int xp, int yp, int width, int height, int colour, boolean fixed) {
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
        for (int x = xp; x < xp + width; x++){
            if (x < 0 || x >= this.width || yp >= this.height) continue;
            if (yp > 0) pixels[x + yp * this.width] = colour;
            if (yp + height >= this.height) continue;
            if (yp + height > 0) pixels[x + (yp + height) * this.width] = colour;
        }
        for (int y = yp; y <= yp + height; y++){
            if (xp >= this.width || y < 0 || y >= this.height) continue;
            if(xp > 0) pixels[xp + y * this.width] = colour;
            if (xp + width >= this.width) continue;
            if(xp + width > 0) pixels[(xp + width) + y * this.width] = colour;
        }
    }

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}