package entity.mob.car;

import graphics.layers.levels.Level;
import graphics.sprite.Sprite;
import handlers.ResourceHandler;

public class Mustang extends Car {
    
    public Mustang(double x, double y){
        super(new Sprite(Level.BLOCK_SIZE, 0, 1, ResourceHandler.getSheet("/player/cars/carSprites.png", Level.BLOCK_SIZE * 4, Level.BLOCK_SIZE * 4)), x, y);
    }
    
}
