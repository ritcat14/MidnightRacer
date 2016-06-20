package graphics.layers.levels;

import java.util.ArrayList;

import tools.TileCoordinate;
import entity.Entity;
import entity.mob.Player;
import entity.mob.car.Mustang;
import events.Event;
import graphics.Screen;
import graphics.layers.Layer;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import graphics.tiles.Hotspot;
import graphics.tiles.Tile;
import handlers.ResourceHandler;

public abstract class Level extends Layer {
    
    protected int layerNum;

    private ArrayList<Sprite> tileSprites = new ArrayList<Sprite>();

    public ArrayList<Entity>  entities    = new ArrayList<Entity>();

    protected SpriteSheet     sheet;

    protected int[][]         layerTiles;

    protected int[]           solids;

    protected Player          player;

    protected int             width       = 0, height = 0;
    private int               xa          = 0, ya = 0;
    public static int         BLOCK_SIZE  = 32;
    public static int         ENTITY_SIZE  = 32;
    public static int         bitOffset   = (int)(Math.log(BLOCK_SIZE) / Math.log(2));
    private int               backID;

    public Level(String dir, int backID) throws Exception {
        this.backID = backID;
        String[][] data = null;
        try {
            data = ResourceHandler.getElementData(ResourceHandler.getResource(dir));
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
        String[] fileInfo = data[data.length - 1];
        sheet = ResourceHandler.getSheet("/maps/sheets/" + fileInfo[0], Integer.parseInt(fileInfo[1]), Integer.parseInt(fileInfo[2]));
        String[] mapInfo = data[data.length - 4];
        int width = Integer.parseInt(mapInfo[0]);
        int height = Integer.parseInt(mapInfo[1]);
        Level.BLOCK_SIZE = Integer.parseInt(mapInfo[2]);
        layerNum = Integer.parseInt(mapInfo[3]);
        layerTiles = new int[layerNum][];
        Level.bitOffset = (int)(Math.log(Level.BLOCK_SIZE) / Math.log(2));
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
        convertSolids(data[data.length - 3]);
        
        createHotspots(data[data.length - 2]);
        
        TileCoordinate t = new TileCoordinate(5,5);
        entities.add(new Mustang(t.x(), t.y()));
    }
    
    private void createHotspots(String[] data){
        for (String s : data) {
            String[] parts = s.split(",");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double width = Double.parseDouble(parts[2]);
            double height = Double.parseDouble(parts[3]);
            entities.add(new Hotspot(x, y, width, height, parts[parts.length - 1]));
        }
    }

    private void convertSolids(String[] data) {
        int i = 0;
        for (String s : data) {
            if (s == null)
                continue;
            String[] parts = s.split(",");
            String bln = parts[1];
            boolean solid = Boolean.parseBoolean(bln);
            if (solid)
                i++;
        }
        solids = new int[i];
        i = 0;
        for (String s : data) {
            if (s == null)
                continue;
            String[] parts = s.split(",");
            String bln = parts[1];
            String ID = parts[0];
            boolean solid = Boolean.parseBoolean(bln);
            if (solid) {
                solids[i] = Integer.parseInt(ID);
                i++;
            }
        }
    }

    public void add(Entity e) {
        if (e instanceof Player)
            player = ((Player)e);
        entities.add(e);
    }

    public void remove(int i) {
        if (i < entities.size() - 1)
            entities.remove(i);
        else
            throw new IndexOutOfBoundsException(i + " must be less than " + (entities.size() - 1));
    }

    @Override
    public void onEvent(Event event) {
        if (player != null)
            player.onEvent(event);
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
        for (int i = 0; i < solids.length; i++) {
            if (solids[i] == ID)
                return true;
        }
        return false;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return new Tile(backID, tileSprites.get(backID - 1), x, y, true);
        }

        int[] tileData;

        for (int i = layerTiles.length - 2; i > -1; i--) {
            tileData = layerTiles[i];
            if (tileData[x + y * width] == 0)
                continue;
            else {
                int ID = tileData[x + y * width];
                return new Tile(ID, tileSprites.get(ID - 1), x, y, isSolid(ID));
            }
        }
        return new Tile(backID, tileSprites.get(backID - 1), x, y, true);
    }

    public Tile getTile(int x, int y, int layer) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }

        int[] tileData = layerTiles[layer - 1];
        int ID = tileData[x + y * width];
        if (ID != 0) {
            return new Tile(ID, tileSprites.get(ID - 1), x, y, isSolid(ID));
        } else
            return null;
    }

    public void update() {
        super.update();
        for (int i = entities.size() - 1; i > -1; i--) {
            int x0 = xa >> bitOffset;
            int x1 = (xa + Screen.WIDTH + BLOCK_SIZE) >> bitOffset;
            int y0 = ya >> bitOffset;
            int y1 = (ya + Screen.HEIGHT + BLOCK_SIZE) >> bitOffset;
            
            int x = (int)entities.get(i).getX() >> bitOffset;
            int y = (int)entities.get(i).getY() >> bitOffset;
            
            if (x < x0 || y < y0 || x >= x1 || y >= y1) {
            } else entities.get(i).update();
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
        for (int i = entities.size() - 1; i > -1; i--) {
            entities.get(i).render(screen);
        }

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                Tile t = getTile(x, y, layerNum);
                if (t != null)
                    t.render(x, y, screen);
                else
                    continue;
            }
        }
    }

}
