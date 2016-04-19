package graphics.GUI;

import events.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GUIPanel extends GUIComponent implements EventListener {

    protected Color         c;
    protected BufferedImage image;

    private enum Type {
        RECT,
        IMAGE;
    };

    private Type type;

    public GUIPanel(int x, int y, int width, int height, Color c) {
        super(x, y, width, height);
        this.c = c;
        type = Type.RECT;
    }

    public GUIPanel(BufferedImage image) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
        type = Type.IMAGE;
    }

    public GUIPanel(BufferedImage image, int x, int y) {
        super(x, y,image.getWidth(), image.getHeight());
        this.image = image;
        type = Type.IMAGE;
    }

    public GUIPanel(BufferedImage image, int x, int y, int width, int height) {
        super(0, 0, width, height);
        this.image = image;
        type = Type.IMAGE;
    }

    @Override
    public void render(Graphics2D g) {
        switch (type) {
            case RECT:
                g.setColor(c);
                g.fillRect(x, y, width, height);
                break;
            case IMAGE:
                g.drawImage(image, x, y, width, height, null);
                break;
        }
      super.render(g);
    }
  
    @Override
    public void update(){
      super.update();
    }

    @Override
    public void onEvent(Event event) {
        for (int i = components.size() - 1; i > -1; i --) components.get(i).onEvent(event);
    }
}
