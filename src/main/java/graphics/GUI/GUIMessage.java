package graphics.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class GUIMessage extends GUIPanel {
    
    GUILabel message;
    int life;
    
    public GUIMessage(int width, int height, String text, int time) {
        super(0, 0, width, height, Color.GRAY);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15));
        add(message);
    }
    
    public GUIMessage(int width, int height, Color c, String text, int time) {
        super(0, 0, width, height, c);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15));
        add(message);
    }
    
    public GUIMessage(int width, int height, String text, Color c, int time) {
        super(0, 0, width, height, Color.GRAY);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15)).setColour(c);
        add(message);
    }
    
    public GUIMessage(int width, int height, Color c1, String text, Color c, int time) {
        super(0, 0, width, height, c1);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15)).setColour(c);
        add(message);
    }
    
    public GUIMessage(int width, int height, BufferedImage image, String text, Color c, int time) {
        super(image, 0, 0, width, height);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15)).setColour(c);
        add(message);
    }
    
    public GUIMessage(BufferedImage image, String text, Color c, int time) {
        super(image, 0, 0);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15)).setColour(c);
        add(message);
    }
    
    public GUIMessage(int width, int height, BufferedImage image, String text, int time) {
        super(image, 0, 0, width, height);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15));
        add(message);
    }
    
    public GUIMessage(BufferedImage image, String text, int time) {
        super(image, 0, 0);
        this.life = time;
        message = new GUILabel(5, 5, text).setFont(new Font("Arial", Font.PLAIN, 15));
        add(message);
    }
    
    private int time = 0;
    public void update(){
        time++;
        super.update();
        if (time % (life * 60) == 0) {
            remove();
        }        
    }
    
}
