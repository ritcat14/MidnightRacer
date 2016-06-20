package entity.upgrades.engines;

import entity.upgrades.Upgrade;
import graphics.sprite.Sprite;

public class Engine extends Upgrade {
    
    protected int max_speed, max_acceleration, horsePower;
    
    public Engine(Sprite sprite, int x, int y, int cost){
        super(sprite, x, y, cost);
    }
    
}
