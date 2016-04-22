package entity.mob.car;

import java.awt.event.KeyEvent;

import tools.Vector2i;
import entity.mob.Mob;
import entity.mob.car.engines.Engine;
import entity.mob.car.tyres.Tyre;
import events.*;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import graphics.Screen;
import graphics.layers.states.Game;

public abstract class Car extends Mob implements EventListener {
    
    private Engine engine;
    private Tyre[] typre = new Tyre[3];
    
    private int mass = 350; //kg
    
    public double speed = 0.2;
    
    private Vector2i vel; // Velocity
    
    private double xa = 0, ya = 0;
    
    private int MAX_SPEED = 5;
    
    private boolean up, down, left, right;
    
    private Vector2i position;
    
    public Car(){
        position = new Vector2i(x,y);
    }
    
    public Vector2i getLocation(){
        return position;
    }
    
    @Override
    public void render(Screen screen){
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }

    public void setLocation(Vector2i p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    @Override
    public void update(){
        
        // CAR PHYSICS HERE
        
        position = new Vector2i(x,y);
        if (up && ya > -MAX_SPEED) ya -= speed;
        else {
            if (ya < 0) ya += speed;
            }
        if (down && ya < MAX_SPEED) ya += speed;
        else {
            if (ya > 0) ya -= speed;
            }
        if (left && xa > -MAX_SPEED) xa -= speed;
        else {
            if (xa < 0) xa += speed;
            }
        if (right && xa < MAX_SPEED) xa += speed;
        else {
            if (xa > 0) xa -= speed;
            }
        
        vel = new Vector2i(xa, ya);
        
        level = Game.currLevel;
        
        health = 100;

        if (vel.x != 0 || vel.y != 0) {
            move(vel.x, vel.y);
        }
    }

    public void setSpeed(double spd) {
        speed = spd;
    }

    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.KEY_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onKeyPressed((KeyPressedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.KEY_RELEASED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onKeyReleased((KeyReleasedEvent)event);
            }
        });
    }

    public boolean onKeyPressed(KeyPressedEvent event) {
        switch(event.getKey()){
            case KeyEvent.VK_W:
                up = true;
                return true;
            case KeyEvent.VK_A:
                left = true;
                return true;
            case KeyEvent.VK_S:
                down = true;
                return true;
            case KeyEvent.VK_D:
                right = true;
                return true;
        }
        return false;
    }

    public boolean onKeyReleased(KeyReleasedEvent event) {
        switch(event.getKey()){
            case KeyEvent.VK_W:
                up = false;
                return true;
            case KeyEvent.VK_A:
                left = false;
                return true;
            case KeyEvent.VK_S:
                down = false;
                return true;
            case KeyEvent.VK_D:
                right = false;
                return true;
        }
        return false;
    }
}
