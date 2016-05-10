package entity.mob;

import java.awt.image.BufferedImage;

import tools.Vector2i;
import entity.mob.car.Car;
import entity.mob.car.Mustang;
import events.*;
import graphics.*;
import graphics.layers.levels.Level;

public class Player extends Mob implements EventListener {

    public static Level    levelToGo = null;
    public static int      XPLevel   = 1;
    
    private static String  name      = null;

    private BufferedImage  image      = null;
    
    private Car car;

    @Deprecated
    public Player(String name, Keyboard input) {
        Player.name = name;
        load();
        sprite = car.getSprite();
        
        // Player default attributes
        health = 100;
    }

    public Player(String name, double x, double y) {
        this.x = x;
        this.y = y;
        car = new Mustang(x, y);
        load();
        Player.name = name;
        sprite = car.getSprite();

        // Player default attributes
        health = 100;
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
    
    public Car getCar(){
        return car;
    }

    public static int getLevel() {
        return XPLevel;
    }

    public int getHealth() {
        return health;
    }

    public void setLocation(Vector2i p) {
        this.x = p.dX;
        this.y = p.dY;
    }
    
    public void onEvent(Event event) {
        car.onEvent(event);
    }

    public void update() {
        car.update();
        setLocation(car.getLocation());
    }

    public void render(Screen screen) {
        car.render(screen);
    }
}
