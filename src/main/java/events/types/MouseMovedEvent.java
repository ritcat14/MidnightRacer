package events.types;

import events.Event;

public class MouseMovedEvent extends Event {

	private int x = 0, y = 0;
	private boolean dragged = false;
	
	public MouseMovedEvent(int x, int y, boolean dragged) {
		super(Event.Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
		this.dragged = dragged;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getDragged() {
		return dragged;
	}
	
}
