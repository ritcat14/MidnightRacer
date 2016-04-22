package entity.mob.car;

import graphics.sprite.Sprite;
import handlers.ResourceHandler;

public class Mustang extends Car {
    
    public Mustang(){
        sprite = new Sprite(16, 0, 0, ResourceHandler.getSheet("/cars/proto-car.png", 16, 16));
    }
    
}
