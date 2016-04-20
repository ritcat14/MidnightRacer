package entity;

import java.awt.Rectangle;
import java.util.Random;

import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.layers.states.Game;
import graphics.sprite.Sprite;

public class Entity{
	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected final Random random = new Random();
	protected Level level;
	
	public Entity(){}
	
	public Entity(int x, int y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.level = Game.currLevel;
	}
	
	public void update() {}
	
	public void render(Screen screen){
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public void remove() {
		removed = true;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
  
    public Rectangle getBounds(){
      Rectangle r = new Rectangle((int)(x-16), (int)(y-16), sprite.getWidth(), sprite.getHeight());
      return r;
    }
	
}