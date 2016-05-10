package graphics.GUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GUISlideShow extends GUIComponent {

    private BufferedImage[] images;

    private int             frameRate;

    private int             frame  = 0;

    private int             length = 1;

    private BufferedImage   currFrame;

    public GUISlideShow(int x, int y, int width, int height, BufferedImage[] images, int frameRate) {
        super(x, y, width, height);
        this.frameRate = frameRate;
        this.images = images;
        this.length = images.length;
        currFrame = images[0];

    }

    public GUISlideShow(int x, int y, BufferedImage[] images, int frameRate) {
        super(x, y, images[0].getWidth(), images[0].getHeight());
        this.frameRate = frameRate;
        this.images = images;
        this.length = images.length;
        currFrame = images[0];
    }

    public void setFrame(int frame) {
        this.frame = frame;
        currFrame = images[frame];
    }

    private int time = 0;

    public void update() {
        super.update();
        time++;
        if (frameRate != 0) {
            if (time % frameRate == 0) {
                if (frame >= length - 1)
                    frame = 0;
                else
                    frame++;
                currFrame = images[frame];
            }
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(currFrame, x, y, width, height, null);
    }

}
