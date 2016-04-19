package handlers;

import events.Event;
import graphics.GUI.GUIComponent;
import graphics.layers.Layer;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GUIHandler extends Layer {
  
  private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();
  
  public void update(){
    for (int i = components.size() - 1; i > -1; i--){
      if (components.get(i) == null) continue;
      components.get(i).update();
    }
  }
  
  public void render(Graphics2D g){
    for (int i = components.size() - 1; i > -1; i--){
      if (components.get(i) == null) continue;
      components.get(i).render(g);
    }
  }
  
  public void add(GUIComponent g){
    if (g != null) components.add(g);
  }
  
  public void remove(GUIComponent g){
    components.remove(g);
  }
  
  public void remove(int index){
    components.remove(index);
  }
  
  public void onEvent(Event event) {
    for (int i = components.size() - 1; i > -1; i--) components.get(i).onEvent(event);
  }
  
}
