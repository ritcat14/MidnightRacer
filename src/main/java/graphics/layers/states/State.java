package graphics.layers.states;

import events.Event;
import events.EventListener;
import graphics.Screen;
import graphics.layers.Layer;
import handlers.GUIHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class State implements EventListener {

    protected ArrayList<Layer> layerStack = new ArrayList<Layer>();
    protected GUIHandler gh;
  
    public State(){
      gh = new GUIHandler();
      add(gh);
    }

    public void render(Graphics2D g) {
        for (int i = layerStack.size() - 1; i > -1; i--) {
            if (layerStack.get(i) == null)
                continue;
            layerStack.get(i).render(g);
        }
    }

    public void render(Screen screen) {
        for (int i = layerStack.size() - 1; i > -1; i--) {
            if (layerStack.get(i) == null)
                continue;
            layerStack.get(i).render(screen);
        }
    }

    public void render(Graphics2D g, Screen screen) {
        for (int i = layerStack.size() - 1; i > -1; i--) {
            if (layerStack.get(i) == null) continue;
            layerStack.get(i).render(screen);
            layerStack.get(i).render(g);
        }
    }

    public void update() {
        for (int i = layerStack.size() - 1; i > -1; i--) {
            if (layerStack.get(i) == null) continue;
            layerStack.get(i).update();
        }
    }
  
    public void clear(){
      layerStack.clear();
    }

    @Override
    public void onEvent(Event event) {
        for (int i = layerStack.size() - 1; i > -1; i--) {
            layerStack.get(i).onEvent(event);
        }
    }
  
    public void add(Layer layer){
      layerStack.add(layer);
    }
  
    public void remove(Layer layer){
      layerStack.remove(layer);
    }
  
    public void add(int layer){
      layerStack.remove(layer);
    }

}
