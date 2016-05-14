package entity.mob;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import tools.Vector2i;
import entity.Entity;
import entity.mob.car.Car;
import events.*;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import graphics.*;
import graphics.layers.levels.Level;
import graphics.sprite.AnimatedObject;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import handlers.ResourceHandler;

public class Player extends Mob implements EventListener {

    public static Level    levelToGo = null;
    public static int      XPLevel   = 1;

    private static String  name      = null;

    private BufferedImage  image     = null;

    private AnimatedObject currAnim;

    private Vector2i       vel       = new Vector2i(); // Velocity

    public boolean         up, down, left, right, walking, playerEnter;

    private enum State {
        WALKING,
        DRIVING,
        SHOOTING;
    }

    private State state = State.WALKING;

    private Car   car   = null;

    @Deprecated
    public Player(String name, Keyboard input) {
        Player.name = name;
        load();
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
    }

    public void load() {
        //create animated sprites
        SpriteSheet sheet =
                            new SpriteSheet(ResourceHandler.getSheet("/player/walking/playerWalk.png", Level.BLOCK_SIZE * 6,
                                                                     Level.BLOCK_SIZE), 0, 0, 6, 1, Level.BLOCK_SIZE);
        currAnim = new AnimatedObject(sheet, Level.BLOCK_SIZE, Level.BLOCK_SIZE, 6);
        sprite = currAnim.getSprite();
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

    public Car getCar() {
        return car;
    }

    public static int getLevel() {
        return XPLevel;
    }

    public int getHealth() {
        return health;
    }

    public void enter(Car car) {
        state = State.DRIVING;
        this.car = car;
        x = car.getX();
        y = car.getY();
    }

    public void exit() {
        if (state == State.DRIVING) {
            state = State.WALKING;
            car = null;
        }
    }

    public void setLocation(Vector2i p) {
        this.x = p.dX;
        this.y = p.dY;
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
        checkCollision();
        if (state == State.DRIVING) {
            car.update();
            moveCar();
        } else {
            if (walking)
                currAnim.update();
            else
                currAnim.setFrame(0);

            double xa = 0, ya = 0;
            double speed = 1.5;
            if (walking) {
                if (up)
                    sprite = currAnim.getSprite();
                else if (down)
                    sprite = Sprite.rotate(currAnim.getSprite(), Math.toRadians(90));
                if (left)
                    sprite = Sprite.rotate(currAnim.getSprite(), Math.toRadians(180));
                else if (right)
                    sprite = Sprite.rotate(currAnim.getSprite(), Math.toRadians(360));
            }
            if (up)
                ya -= speed;
            else if (down)
                ya += speed;
            if (left)
                xa -= speed;
            else if (right)
                xa += speed;

            if (xa != 0 || ya != 0) {
                move(xa, ya);
                walking = true;
            } else
                walking = false;
        }
    }

    private void checkCollision() {
        List<Entity> entitiesInRange = getEntities(this, 600);
        for (Entity e : entitiesInRange) {
            if (this.getBounds().intersects(e.getBounds())) {
                if (e instanceof Car) {
                    Car c = (Car)e;
                    if (playerEnter) {
                        if (car == null) {
                            enter(c);
                        } else
                            exit();
                        playerEnter = false;
                    }
                }
            }
        }
    }

    private double upTime = 0, downTime = 0, leftTime = 0, rightTime = 0;

    private void moveCar() {

        car.position = new Vector2i(x, y);

        double maxAcceleration = car.gear * (car.speed * 5);

        if (car.gearUp && car.gear < car.gearMax)
            car.gear++;
        if (car.gearDown && car.gear > 0)
            car.gear--;

        // CAR PHYSICS HERE
        if (up && upTime < maxAcceleration)
            upTime += car.speed;
        else if (!up && upTime > -maxAcceleration)
            upTime -= car.speed;

        if (down && downTime < maxAcceleration)
            downTime += car.speed;
        else if (!down && downTime > -maxAcceleration)
            downTime -= car.speed;

        if (left && leftTime < maxAcceleration)
            leftTime += car.speed;
        else if (!left && leftTime > -maxAcceleration)
            leftTime -= car.speed;

        if (right && rightTime < maxAcceleration)
            rightTime += car.speed;
        else if (!right && rightTime > -maxAcceleration)
            rightTime -= car.speed;


        vel = new Vector2i(rightTime - leftTime, downTime - upTime);

        if (vel.dX != 0 || vel.dY != 0)
            car.setSprite(Sprite.rotate(car.preSprite, vel.angle()));

        if (vel.dY != 0 || vel.dX != 0) {
            move(vel.dX, vel.dY);
        }
    }

    public void render(Screen screen) {
        if (state == State.DRIVING)
            car.render(screen);
        else if (state == State.WALKING)
            screen.renderMob((int)(x - Level.BLOCK_SIZE), (int)(y - Level.BLOCK_SIZE), this);
    }

    public boolean onKeyPressed(KeyPressedEvent event) {
        switch (event.getKey()) {
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
            case KeyEvent.VK_SHIFT:
                if (state == State.DRIVING){
                    if(car.gear < car.gearMax) car.gear++;
                    return true;
                }
                return false;
            case KeyEvent.VK_CONTROL:
                if (state == State.DRIVING){
                    if (car.gear > 1) car.gear--;
                    return true;
                }
                return false;
            case KeyEvent.VK_E:
                if (!playerEnter)
                    playerEnter = true;
                else
                    playerEnter = false;
                return true;
        }
        return false;
    }

    public boolean onKeyReleased(KeyReleasedEvent event) {
        switch (event.getKey()) {
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
