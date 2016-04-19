package graphics.layers;

import java.awt.Graphics2D;

import events.Event;
import events.EventListener;
import graphics.Screen;

public class Layer implements EventListener {

	public void onEvent(Event event) {}
	
	public void update() {}
	
	public void render(Screen screen) {}
	
	public void render(Graphics2D g) {}

}
