package entity.mob.car;

import entity.upgrades.engines.V8;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;
import handlers.ResourceHandler;

public class Mustang extends Car {
    
    public Mustang(double x, double y){
        super(new Sprite(Level.ENTITY_SIZE, 0, 1, ResourceHandler.getSheet("/player/cars/carSprites.png", Level.ENTITY_SIZE * 4, Level.ENTITY_SIZE * 4)), x, y);
        e = new V8(0, 0);
    }
    
}
