package graphics.layers.states;

import entity.mob.Player;
import graphics.Screen;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIPanel;
import graphics.layers.levels.Level;
import graphics.layers.levels.City1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.imageio.ImageIO;

import tools.TileCoordinate;

public class Game extends State {

    private int           WIDTH  = Screen.WIDTH, HEIGHT = Screen.HEIGHT;
    private double SCALE = Screen.SCALE;

    private BufferedImage image  = new BufferedImage((int)(WIDTH / SCALE), (int)(HEIGHT / SCALE), BufferedImage.TYPE_INT_RGB);
    private int[]         pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Player        player;
    private GUILabel gear;
    private GUIPanel bottom;

    public static Level   currLevel;

    public Game() {
        super();
        TileCoordinate playerSpawn = new TileCoordinate(5, 5);
        player = new Player("Kris", playerSpawn.x(), playerSpawn.y());
        BufferedImage image;
        bottom = new GUIPanel(0,0,0,0,Color.BLACK);
        City1 l1;
        try {
            l1 = new City1();
            l1.add(player);
            add(l1);
            currLevel = l1;
            image = ImageIO.read(Game.class.getResource("/GUI/HUD/HUD.png"));
            bottom = new GUIPanel(image, 0, Screen.HEIGHT - image.getHeight());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        gear = new GUILabel(bottom.x + 4, bottom.y + (bottom.height - 5), "GEAR: ").setColour(Color.GRAY);
        bottom.add(gear);
        
        gh.add(bottom);
    }
    
    public void update(){
        super.update();
        gear.setText("GEAR: " + player.getCar().getGear());
    }

    public void render(Graphics2D g, Screen screen) {
        if (player != null){
            double xScroll = player.getX() - screen.width / 2;
            double yScroll = player.getY() - screen.height / 2;
            currLevel.setScroll((int)xScroll, (int)yScroll);
        }

        for (int i = layerStack.size() - 1; i > -1; i--) {
            if (layerStack.get(i) instanceof Level) {
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
