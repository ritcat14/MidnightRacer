package entity;

import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;
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

    public List<Entity> getEntities(Entity e, int radius) {
        List<Entity> result = new ArrayList<Entity>();
        level = Game.currLevel;
        if (level != null){
            int ex = (int)e.getX();
            int ey = (int)e.getY();
            for (int i = 0; i < level.entities.size(); i++) {
                Entity entity = level.entities.get(i);
                if (entity.equals(e))
                    continue;
                int x = (int)entity.getX();
                int y = (int)entity.getY();
                int dx = Math.abs(x - ex);
                int dy = Math.abs(y - ey);
                double distance = Math.sqrt((dx * dx) + (dy * dy));
                if (distance <= radius)
                    result.add(entity);
            }
        }
        return result;
    }
  
    public Rectangle getBounds(){
      Rectangle r = new Rectangle((int)(x-Level.BLOCK_SIZE), (int)(y-Level.BLOCK_SIZE), sprite.getWidth(), sprite.getHeight());
      return r;
    }
	
}