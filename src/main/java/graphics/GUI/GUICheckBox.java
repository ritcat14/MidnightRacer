package graphics.GUI;

import handlers.ResourceHandler;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import events.*;
import events.types.*;

public class GUICheckBox extends GUIComponent {
    
    private boolean checked;
    private BufferedImage standard, selected;
    
    public GUICheckBox(int x, int y){
        super(x, y, 0, 0);
        try {
            standard = ResourceHandler.getImage("/GUI/buttons/standard.png");
            selected = ResourceHandler.getImage("GUI/buttons/selected.png");
        } catch (Exception e){ e.printStackTrace(); }
        this.width = standard.getWidth();
        this.height = standard.getHeight();
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onMousePressed((MousePressedEvent)event);
            }
        });
    }
  
    public boolean onMousePressed(MousePressedEvent e) {
        if (this.bounds.contains(new Point(e.getX(), e.getY()))) return true;
        else return false;
    }
    
    @Override
    public void render(Graphics2D g){
        if (checked) g.drawImage(selected, x, y, null);
        else g.drawImage(standard, x, y, null);
    }
    
}
