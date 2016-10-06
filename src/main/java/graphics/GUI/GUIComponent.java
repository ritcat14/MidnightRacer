package graphics.GUI;

import tools.Vector2i;
import events.Event;
import events.EventListener;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GUIComponent implements EventListener {

    public int                        x, y;
    public int                        width, height;
    protected int                     time       = 0;
    protected Rectangle               bounds;
    protected boolean                 removed    = false;

    protected ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

    public GUIComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public GUIComponent(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        bounds = new Rectangle(x, y, width, height);
    }

    public void setPosition(Vector2i position) {
        this.x = (int)position.dX;
        this.y = (int)position.dY;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update() {
        time++;
        for (int i = components.size() - 1; i > -1; i--) {
            if (components.get(i) == null)
                continue;
            components.get(i).update();
        }
    }

    public void renderRelative(Graphics2D g) {
        for (int i = components.size() - 1; i > -1; i--) {
            GUIComponent curr = components.get(i);
            if (curr == null)
                continue;
            curr.setPosition(x + curr.x, y + curr.y);
            curr.render(g);
        }
    }

    public void render(Graphics2D g) {
        for (int i = components.size() - 1; i > -1; i--) {
            if (components.get(i) == null)
                continue;
            components.get(i).render(g);
        }
    }

    public void add(GUIComponent g) {
        if (g != null)
            components.add(g);
    }

    public void remove(GUIComponent g) {
        components.remove(g);
    }

    public void remove(int index) {
        components.remove(index);
    }
    
    public void remove() {
        removed = true;
    }
    
    public boolean isRemoved(){
        return removed;
    }

    public Rectangle getBounds() {
        bounds = new Rectangle(x, y, width, height);
        return bounds;
    }
    
    public ArrayList<GUIComponent> getComponents() {
        return components;
    }
    
    public void onEvent(Event event) {
    }

}
