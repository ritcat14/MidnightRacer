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
    protected ArrayList<Tile>     tileList       = new ArrayList<Tile>();

    protected ArrayList<String[]> layers      = new ArrayList<String[]>();

    private ArrayList<Sprite>     tileSprites = new ArrayList<Sprite>();

    protected SpriteSheet         sheet;
  
    protected int						 width       = 0, height  = 0;

    protected int                 xScroll     = 0, yScroll = 0;
    private int xa = 400, ya = 300;

    public Level(String dir, int layerNum) throws Exception {
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
        this.width = width * 32;
        this.height = height * 32;
        // Create all the tile sprites we need
        int xNum = sheet.getWidth() / 32;
        int yNum = sheet.getHeight() / 32;

        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
                tileSprites.add(new Sprite(32, x, y, sheet));
            }
        }
        for (int i = layerNum - 1; i > -1; i--) {
            System.out.println("Loading layer " + i);
            String[] layerData = data[i];
            int j = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int ID = Integer.parseInt(layerData[j]);
                    if (ID != 0) tileList.add(new Tile(ID, tileSprites.get(ID - 1), x * 32, y * 32));
                    j++;
                }
            }
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
        return xScroll;
    }

    public int yScroll() {
        return yScroll;
    }
  
    public void setScroll(int xScroll, int yScroll) {
        this.xScroll = xScroll;
        this.yScroll = yScroll;
    }
  
    public Tile getTile(int x, int y){
      if (x < 0 || y < 0 || x >= width || y >= height){
        return tileList.get(29);
      }
      for (int i = tileList.size() - 1; i > -1; i--){
        if (tileList.get(i).getX() == x && tileList.get(i).getY() == y) {
          return tileList.get(i);
        }
      }
      return tileList.get(29);
    }
  
    public void update(){
      super.update();
    }

    public void render(Screen screen) {      
        for (int i = tileList.size() - 1; i > -1; i--){
          tileList.get(i).render(screen);
        }
    }

}
