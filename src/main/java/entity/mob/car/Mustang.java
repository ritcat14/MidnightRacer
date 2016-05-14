package entity.mob.car;

import graphics.layers.levels.Level;
import graphics.sprite.Sprite;
import handlers.ResourceHandler;

public class Mustang extends Car {
    
    public Mustang(double x, double y){
        super(new Sprite(Level.BLOCK_SIZE, 0, 0, ResourceHandler.getSheet("/player/cars/proto-car.png", Level.BLOCK_SIZE, Level.BLOCK_SIZE)), x, y);
    }
    
}
