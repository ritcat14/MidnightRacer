package graphics.layers.levels;

import java.util.ArrayList;

import entity.Entity;
import entity.mob.Player;
import events.Event;
import graphics.Screen;
import graphics.layers.Layer;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import graphics.tiles.Tile;
import handlers.ResourceHandler;

public abstract class Level extends Layer {

    protected int             layerNum;

    private ArrayList<Sprite> tileSprites = new ArrayList<Sprite>();
    
    protected ArrayList<Entity> entities = new ArrayList<Entity>();

    protected SpriteSheet     sheet;

    protected int[][]         layerTiles;
    
    protected int[]           solids;
    
    protected Player player;

    protected int             width       = 0, height = 0;
    private int               xa          = 0, ya = 0;
    public static int         BLOCK_SIZE  = 64;
    public static int         bitOffset = 6; // 16 -> 4, 32 -> 5, 64 -> 6
    private int               backID;

    public Level(String dir, int layerNum, int backID) throws Exception {
        layerTiles = new int[layerNum][];
        this.backID = backID;
        this.layerNum = layerNum;
        String[][] data = null;
        try {
            data = ResourceHandler.getElementData(ResourceHandler.getResource(dir), layerNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null)
            load(data);
        else {
            System.out.println("Couldn't load level");
            throw new Exception("Couldn't load level, data is null");
        }

    }

    public void load(String[][] data) {
        sheet = ResourceHandler.getSheet("/maps/sheets/cityTileSet.png", BLOCK_SIZE * 7, BLOCK_SIZE * 7);
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
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int ID = Integer.parseInt(layerData[j]);
                    tileData[x + y * width] = ID;
                    j++;
                }
            }
            layerTiles[i] = tileData;
        }
        convertSolids(data[data.length - 1]);
    }
    
    private void convertSolids(String[] data){
        int i = 0;
        for (String s : data){
            if (s == null) continue;
            String[] parts = s.split(",");
            String bln = parts[1];
            boolean solid = Boolean.parseBoolean(bln);
            if (solid)i ++;
        }
        solids = new int[i];
        i = 0;
        for (String s : data){
            if (s == null) continue;
            String[] parts = s.split(",");
            String bln = parts[1];
            String ID = parts[0];
            boolean solid = Boolean.parseBoolean(bln);
            if (solid){
                solids[i] = Integer.parseInt(ID);
                System.out.println(ID);
                i ++;
            }
        }
    }
    
    public void add(Entity e){
        if (e instanceof Player) player = ((Player)e);
        entities.add(e);
    }
    
    public void remove(int i){
        if (i < entities.size() - 1) entities.remove(i);
        else throw new IndexOutOfBoundsException(i + " must be less than " + (entities.size() - 1));
    }

    @Override
    public void onEvent(Event event) {
        if (player != null) player.onEvent(event);
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
    
    public boolean isSolid(int ID) {
        for (int i = 0; i < solids.length; i++){
            if (solids[i] == ID) return true;
        }
        return false;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return new Tile(backID, /*tileSprites.get(backID - 1)*/new Sprite(BLOCK_SIZE, 0x0000ff), x, y, true);
        }
        
        int[] tileData;
        
        for (int i = layerTiles.length - 1; i > -1; i--){
            tileData = layerTiles[i];
            if (tileData[x + y * width] == 0) continue;
            else {
                int ID = tileData[x + y * width];
                return new Tile(ID, tileSprites.get(ID - 1), x, y, isSolid(ID));
            }
        }
        return new Tile(backID, /*tileSprites.get(backID - 1)*/new Sprite(BLOCK_SIZE, 0x0000ff), x, y, true);
    }

    public void update() {
        super.update();
        for (int i = entities.size() - 1; i > -1; i--){
            entities.get(i).update();
        }
    }

    public void render(Screen screen) {

        screen.setOffset(xa, ya);
        
        int x0 = xa >> bitOffset;
        int x1 = (xa + screen.width + BLOCK_SIZE) >> bitOffset;
        int y0 = ya >> bitOffset;
        int y1 = (ya + screen.height + BLOCK_SIZE) >> bitOffset;
    
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
        for (int i = entities.size() - 1; i > -1; i--){
            entities.get(i).render(screen);
        }
    }

}
