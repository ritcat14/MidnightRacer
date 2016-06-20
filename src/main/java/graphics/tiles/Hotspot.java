package graphics.tiles;

import entity.Entity;
import graphics.Screen;
import graphics.sprite.Sprite;

public class Hotspot extends Entity {
    public String building;
    
    public Hotspot(double x, double y, double width, double height, String building) {
        this.x = x;
        this.y = y;
        this.building = building;
        sprite = new Sprite((int) width, (int) height, 0xff0000);
    }
    
    @Override
    public void render(Screen screen){
        screen.renderSprite((int)(x), (int)(y), sprite, true);
    }
    
}
