package entity.mob;

import java.awt.image.BufferedImage;

import tools.Vector2i;
import events.*;
import events.types.*;
import graphics.*;
import graphics.layers.levels.Level;
import graphics.sprite.AnimatedObject;

public class Player extends Mob implements EventListener {

    public static Level    levelToGo = null;
    public static int      XPLevel   = 1;
    public double speed = 5;
    
    private static String  name      = null;

    private AnimatedObject down       = null, up = null, left = null, right = null;
    private AnimatedObject animSprite = null;

    private BufferedImage  image      = null;

    @Deprecated
    public Player(String name, Keyboard input) {
        Player.name = name;
        load();
        animSprite = up;
        sprite = animSprite.getSprite();

        // Player default attributes
        health = 100;
    }

    public Player(String name, double x, double y) {
        this.x = x;
        this.y = y;
        load();
        Player.name = name;

        // Player default attributes
        health = 100;

        up = AnimatedObject.up;
        /*down = AnimatedObject.down;
        left = AnimatedObject.left;
        right = AnimatedObject.right;
        animSprite = down;*/
        sprite = animSprite.getSprite();
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
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onKeyPressed((KeyPressedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onKeyReleased((KeyReleasedEvent)event);
            }
        });
    }

    private static int time = 0;

    public void update() {
        health = 100;

        sprite = animSprite.getSprite();
        time++;

        if (walking)
            animSprite.update();
        else
            animSprite.setFrame(0);

        double xa = 0, ya = 0;

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public boolean onKeyPressed(KeyPressedEvent e) {
        return true;
    }

    public boolean onKeyReleased(KeyReleasedEvent e) {
        return true;
    }

    public void render(Screen screen) {
        screen.renderMob((int)(x - 16), (int)(y - 16), sprite);
    }
}
