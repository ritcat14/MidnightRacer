package graphics.tiles;

import graphics.Screen;
import graphics.sprite.Sprite;

public class Tile {
  
  protected int ID;
  protected Sprite sprite;
  protected int x, y;
  
  public Tile(int ID, Sprite sprite, int x, int y){
    this.ID = ID;
    this.sprite = sprite;
    this.x = x;
    this.y = y;
  }
  
  public int getID() {
    return ID;
  }
  
  public Sprite getSprite() {
    return sprite;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public Tile setSprite(Sprite sprite){
    this.sprite = sprite;
    return this;
  }
  
  public Tile setID(int ID){
    this.ID = ID;
    return this;
  }
  
  public void update(){
    
  }
  
  public void render(Screen screen){
    screen.renderTile(y, x, this);
  }
  
}
