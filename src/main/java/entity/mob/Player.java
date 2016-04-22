package entity.mob;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import tools.Vector2i;
import entity.mob.car.Car;
import entity.mob.car.Mustang;
import events.*;
import events.types.*;
import graphics.*;
import graphics.layers.levels.Level;
import graphics.layers.states.Game;
import graphics.sprite.AnimatedObject;
import graphics.sprite.Sprite;
import handlers.ResourceHandler;

public class Player extends Mob implements EventListener {

    public static Level    levelToGo = null;
    public static int      XPLevel   = 1;
    public double speed = 5;
    
    private static String  name      = null;
    
    private AnimatedObject animSprite = null;

    private BufferedImage  image      = null;
    
    private int mass = 350; //kg
    
    private Vector2i vel; // Velocity
    
    private double xa = 0, ya = 0;
    
    private int MAX_SPEED = 5;
    
    private boolean up, down, left, right;
    
    private Car car;

    @Deprecated
    public Player(String name, Keyboard input) {
        Player.name = name;
        load();
        car = new Mustang();
        sprite = car.getSprite();
        
        // Player default attributes
        health = 100;
    }

    public Player(String name, double x, double y) {
        this.x = x;
        this.y = y;
        load();
        Player.name = name;
        car = new Mustang();
        sprite = car.getSprite();

        // Player default attributes
        health = 100;
        speed = 0.2;
    }
    
    public void load(){
        //create animated sprites
    }

    public BufferedImage getFace() {
        return image;
    }

    public static String getName() {
        return name;
    }

    public void levelIn() {
        XPLevel++;
    }

    public static int getLevel() {
        return XPLevel;
    }

    public int getHealth() {
        return health;
    }

    public void setSpeed(double spd) {
        speed = spd;
    }

    public void setLocation(Vector2i p) {
        this.x = p.x;
        this.y = p.y;
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

    public void update() {
        
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

    public void render(Screen screen) {
        car.render(screen);
    }
}
