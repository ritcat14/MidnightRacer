package graphics.layers.levels;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.types.KeyPressedEvent;
import graphics.Screen;
import graphics.layers.Layer;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import graphics.tiles.Tile;
import handlers.ResourceHandler;

public abstract class Level extends Layer {

    protected int                 layerNum;

    private ArrayList<Sprite>     tileSprites = new ArrayList<Sprite>();

    protected SpriteSheet         sheet;
    
    protected int[][] layerTiles;
  
    protected int						 width       = 0, height  = 0;
    private int xa = 0, ya = 0;
    private int BLOCK_SIZE = 16;
    private int backID;

    public Level(String dir, int layerNum, int backID) throws Exception {
        layerTiles = new int[layerNum][];
        this.backID = backID;
        this.layerNum = layerNum;
        String[][] data = null;
        try {
            data = ResourceHandler.getElementData(ResourceHandler.getResource(dir), 2);
        } catch (Exception e) { e.printStackTrace(); }
        if (data != null) load(data);
        else {
            System.out.println("Couldn't load level");
            throw new Exception("Couldn't load level, data is null");
        }
      
    }

    public void load(String[][] data) {
        sheet = ResourceHandler.getSheet("/maps/sheets/cityTileSet.png", 224, 224);
        int width = Integer.parseInt((data[data.length - 3])[0]);
        int height = Integer.parseInt((data[data.length - 2])[0]);
        this.width = width;
        this.height = height;
        // Create all the tile sprites we need
        int xNum = sheet.getWidth() / BLOCK_SIZE;
        int yNum = sheet.getHeight() / BLOCK_SIZE;

        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
                tileSprites.add(new Sprite(BLOCK_SIZE, x, y, sheet));
            }
        }
        for (int i = layerNum - 1; i > -1; i--) {
            System.out.println("Loading layer " + i);
            String[] layerData = data[i];
            
            int j = 0;
            int[] tileData = new int[width * height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int ID = Integer.parseInt(layerData[j]);
                    tileData[x + y * width] = ID;
                    j++;
                }
            }
            layerTiles[i] = tileData;
        }
    }
  
    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.KEY_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onKeyPressed((KeyPressedEvent)event);
            }
        });
    }
  
    public boolean onKeyPressed(KeyPressedEvent event){
      if (event.getKey() == KeyEvent.VK_W) {
        ya++;
        return true;
      }
      if (event.getKey() == KeyEvent.VK_A) {
        xa++;
        return true;
      }
      if (event.getKey() == KeyEvent.VK_S) {
        ya--;
        return true;
      }
      if (event.getKey() == KeyEvent.VK_D) {
        xa--;
        return true;
      }
      return false;
    }

    public int xScroll() {
        return xa;
    }

    public int yScroll() {
        return ya;
    }
  
    public void setScroll(int xa, int ya) {
        this.xa = xa;
        this.ya = ya;
    }
  
    public Tile getTile(int x, int y){
      if (x < 0 || y < 0 || x >= width || y >= height){
          return new Tile(backID, tileSprites.get(backID - 1), x, y);
      }
      for (int i = 0; i < layerNum; i++){
          int[] tileData = layerTiles[i];
          int ID = tileData[x + y * width];
          if (ID == 0) continue;
          else return new Tile(ID, tileSprites.get(ID - 1), x, y);
      }
      return new Tile(backID, tileSprites.get(backID - 1), x, y);
    }
  
    public void update(){
      super.update();
    }

    public void render(Screen screen) {
        setScroll((int)xa, (int)ya);
        
        screen.setOffset(xa, ya);
        int x0 = xa >> 4;
        int x1 = (xa + screen.width + BLOCK_SIZE) >> 4;
        int y0 = ya >> 4;
        int y1 = (ya + screen.height + BLOCK_SIZE) >> 4;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
    }

}
