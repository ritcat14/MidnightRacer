package entity.mob;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import tools.Variables;
import tools.Vector2i;
import entity.Entity;
import entity.mob.car.Car;
import events.*;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import graphics.*;
import graphics.GUI.GUIMessage;
import graphics.layers.levels.Level;
import graphics.sprite.AnimatedObject;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import graphics.tiles.Hotspot;
import handlers.FileHandler;
import handlers.ResourceHandler;
import handlers.StateHandler;

public class Player extends Mob implements EventListener {

    public static Level    levelToGo = null;
    public static int      XPLevel   = 1;

    private BufferedImage  image     = null;
    
    private double xa = 0, ya = 0;

    private AnimatedObject currAnim;
    
    private Hotspot currHotspot;
    
    private int Size;

    public boolean         up, down, left, right, walking, playerEnter;

    private enum State {
        WALKING,
        DRIVING,
        SHOOTING;
    }

    private State state = State.WALKING;
    
    private FileHandler f = new FileHandler();

    private Car   car   = null;

    public Player(double x, double y, int size) {
        this.x = x;
        this.y = y;
        this.Size = size;
        load();
        // Player default attributes
        health = 100;
    }

    public void load() {
        //create animated sprites
        SpriteSheet sheet =
                            new SpriteSheet(ResourceHandler.getSheet("/player/walking/playerWalk.png", 96,
                                                                     16), 0, 0, 6, 1, 16);
        currAnim = new AnimatedObject(sheet, Size, Size, 6);
        sprite = currAnim.getSprite();
        
        // Read from file
        if (Variables.installed){
            Variables.money = Integer.parseInt(f.getData(f.playerFile, ":", "Money"));
            Variables.name = f.getData(f.playerFile, ":", "Name");
        } else {
            Variables.money = 100;
        }
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
        int time = Variables.runningTime;
        if (time % 7200 == 0) {
            Variables.money --;
            GUIMessage msg = new GUIMessage(80, 15, Color.BLACK, ((Variables.runningTime / 60) / 60) + ") Saving...", Color.CYAN, 3);
            StateHandler.state.getRenderer().add(msg);
            save();
        }
        checkCollision();
        if (state == State.DRIVING) {
            car.update();
            moveCar();
        } else {
            if (walking)
                currAnim.update();
            else
                currAnim.setFrame(0);

            xa = 0;
            ya = 0;
            
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
                } else if (e instanceof Hotspot){
                    String building = ((Hotspot)e).building;
                    if (building.equals("Garage") && StateHandler.currState != StateHandler.States.GARAGE) {
                        clearMovement((Hotspot)e);
                        StateHandler.changeState(StateHandler.States.GARAGE);
                    }

                    currHotspot = (Hotspot)e;
                }
            }
        }
    }

    private double upTime = 0, downTime = 0, leftTime = 0, rightTime = 0;
    private void moveCar(){
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


        car.vel = new Vector2i(rightTime - leftTime, downTime - upTime);

        if (car.vel.dX != 0 || car.vel.dY != 0)
            car.setSprite(Sprite.rotate(car.preSprite, car.vel.angle()));

        if (car.vel.dY != 0 || car.vel.dX != 0) {
            move(car.vel.dX, car.vel.dY);
        }
    }
    
    public Hotspot getHotspot(){
        return currHotspot;
    }
    
    public void clearMovement(Hotspot e){
        System.out.println("Clearing movement");
        up = false;
        down = false;
        left = false;
        right = false;
        xa = 0;
        ya = 0;
        if (car != null) car.vel = new Vector2i(0,0);
        this.x = e.getX() + ((e.getSprite().getWidth() / 3) * 2);
        this.y = e.getY() + (e.getSprite().getHeight() + (Size * 4));
    }

    public void render(Screen screen) {
        if (state == State.DRIVING)
            car.render(screen);
        else if (state == State.WALKING)
            screen.renderMob((int)(x - Size), (int)(y - Size), this);
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
    
    public void save(){
        f.save(f.playerFile, Variables.getPlayerData());
    }
    
}
