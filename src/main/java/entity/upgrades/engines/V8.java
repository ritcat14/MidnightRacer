package entity.upgrades.engines;

import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;

public class V8 extends Engine {
    
    public V8(int x, int y){
        super(new Sprite(64, 0xff00ff), x, y, 5);
        sprite = new Sprite(64, 0, 0, new SpriteSheet("/cars/upgrades/engines/V8.png", 64));
    }
    
}
