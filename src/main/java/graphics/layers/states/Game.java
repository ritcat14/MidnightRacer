package graphics.layers.states;

import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.layers.levels.City1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends State {
  
    private BufferedImage    image        = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    private int[]            pixels       = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
  
    public Game(){
      super();      
      City1 l1;
    try {
        l1 = new City1();
        add(l1);
    } catch (Exception e1) {
        e1.printStackTrace();
    }
    }
  
    public void render(Graphics2D g, Screen screen){
      
        for (int i = layerStack.size() - 1; i > -1; i--){
          if (layerStack.get(i) instanceof Level){
            layerStack.get(i).render(screen);
          }
        }
        
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
      
        g.setColor(new Color(0xff00ff));

        g.drawImage(image, 0, 0, 800, 600, null);
      
        super.render(g);
    }
}
