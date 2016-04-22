package graphics.GUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GUISlideShow extends GUIComponent {
    
    private BufferedImage[] images;
    
    private int frameRate;
    
    private int frame = 0;
    
    private BufferedImage currFrame;
    
    public GUISlideShow(int x, int y, int width, int height, BufferedImage[] images, int frameRate){
        super(x, y, width, height);
        this.frameRate = frameRate;
    }
    
    public GUISlideShow(int x, int y, BufferedImage[] images, int frameRate){
        super(x, y, images[0].getWidth(), images[0].getHeight());
        this.frameRate = frameRate;
    }
    
    private int time = 0;
    public void update(){
        super.update();
        time++;
        int timeToUpdate = frameRate / 60;
        if (time % timeToUpdate == 0) frame++;
        currFrame = images[frame];
    }
    
    public void render(Graphics2D g){
        g.drawImage(currFrame, x, y, null);
    }
    
}
