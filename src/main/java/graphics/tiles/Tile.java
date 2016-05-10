package graphics.tiles;

import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;

public class Tile {

    protected int    ID;
    protected Sprite sprite;
    protected int    x, y;
    protected boolean solid;

    public Tile(int ID, Sprite sprite, int x, int y, boolean solid) {
        this.ID = ID;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.solid = solid;
    }

    public Tile(int ID, Sprite sprite, int x, int y) {
        this.ID = ID;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.solid = false;
    }

    public int getID() {
        return ID;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile setSprite(Sprite sprite) {
        this.sprite = sprite;
        return this;
    }

    public Tile setID(int ID) {
        this.ID = ID;
        return this;
    }
    
    public boolean isSolid(){
        return solid;
    }

    public void update() {}

    public void render(int x, int y, Screen screen) {
        this.x = x;
        this.y = y;
        render(screen);
    }

    private void render(Screen screen) {
        screen.renderTile(x << Level.bitOffset, y << Level.bitOffset, this);
    }

}
