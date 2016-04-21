package graphics.layers.states;

import entity.mob.Player;
import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.layers.levels.City1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import tools.TileCoordinate;

public class Game extends State {
    
    private int WIDTH = Screen.WIDTH, HEIGHT = Screen.HEIGHT, SCALE = Screen.SCALE;
  
    private BufferedImage    image        = new BufferedImage(WIDTH / SCALE, HEIGHT / SCALE, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels       = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Player player;
  
    public static Level currLevel;
  
    public Game(){
      super();
      TileCoordinate playerSpawn = new TileCoordinate(5, 5);
      player = new Player("Kris", playerSpawn.x(), playerSpawn.y());
      City1 l1;
    try {
        l1 = new City1();
        l1.add(player);
        add(l1);
        currLevel = l1;
    } catch (Exception e1) {
        e1.printStackTrace();
    }
    }
  
    public void render(Graphics2D g, Screen screen){
        
        
        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        currLevel.setScroll((int)xScroll, (int)yScroll);
      
        for (int i = layerStack.size() - 1; i > -1; i--){
          if (layerStack.get(i) instanceof Level){
            layerStack.get(i).render(screen);
          }
        }
        
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
      
        g.setColor(new Color(0xff00ff));

        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
      
        super.render(g);
    }
}
