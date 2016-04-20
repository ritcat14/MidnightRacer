package graphics.GUI;

import tools.Vector2i;

import events.*;
import events.types.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class GUIButton extends GUIComponent {

    private GUILabel label;

    private BufferedImage    image          = null;
    private Color c;

    public GUIButton(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        Vector2i lp = new Vector2i(x,y);
        lp.x += 4;
        lp.y += height - 5;
        label = new GUILabel((int)lp.x, (int)lp.y, text);
        add(label);
        c = new Color(0xAAAAAA);
    }

    public GUIButton(int x, int y, BufferedImage image, String text) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        setImage(image);
        c = new Color(0xAAAAAA);
    }
  
    public void setColour(int col){
      c = new Color(col);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setText(String text) {
        label.text = text;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onMousePressed((MousePressedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onMouseReleased((MouseReleasedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onMouseMoved((MouseMovedEvent)event);
            }
        });
    }
  
    public boolean onMousePressed(MousePressedEvent e) {
        if (this.bounds.contains(new Point(e.getX(), e.getY()))) return true;
        else
            return false;
    }

    public boolean onMouseReleased(MouseReleasedEvent e) {
        if (this.bounds.contains(new Point(e.getX(), e.getY()))) return true;
        else return false;
    }

    public boolean onMouseMoved(MouseMovedEvent e) {
        if (this.bounds.contains(new Point(e.getX(), e.getY()))) return true;
        else return false;
    }

    public void update() {
      super.update();
    }

    public void render(Graphics2D g) {
      if (image != null) g.drawImage(image, x, y, width, height, null); 
      else {
        g.setColor(c);
        g.fillRect(x, y, width, height);
      }
      super.render(g);
    }

}
