package entity.mob.car;

import tools.Vector2i;
import entity.mob.Mob;
import entity.upgrades.Upgrade;
import entity.upgrades.engines.Engine;
import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;

public abstract class Car extends Mob {
    
    public float speed = 0.2f;
    
    public int gear = 1;
    public int gearMax = 6;
    
    public Vector2i vel = new Vector2i(); // Velocity
    
    public boolean gearUp, gearDown;
    
    protected Engine e;
    
    public Vector2i position;
    
    public Sprite preSprite;
    
    public Car(Sprite sprite, double x, double y){
        this.x = x;
        this.y = y;
        position = new Vector2i(x,y);
        preSprite = sprite;
        this.sprite = sprite;
    }
    
    public Vector2i getLocation(){
        return position;
    }
    
    @Override
    public void render(Screen screen){
        screen.renderMob((int)(x - Level.ENTITY_SIZE), (int)(y - Level.ENTITY_SIZE), this);
    }
    
    public Upgrade getEngine(){
        return e;
    }
    
    public int getGear(){
        return gear;
    }
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    @Override
    public void update(){
        x = position.dX;
        y = position.dY;
    }

    public void setSpeed(float spd) {
        speed = spd;
    }
}
