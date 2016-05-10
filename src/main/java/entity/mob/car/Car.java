package entity.mob.car;

import java.awt.event.KeyEvent;

import tools.Vector2i;
import entity.mob.Mob;
import events.*;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import graphics.Screen;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;

public abstract class Car extends Mob implements EventListener {
    
    public float speed = 0.2f;
    
    public int gear = 1;
    public int gearMax = 6;
    
    private Vector2i vel = new Vector2i(); // Velocity
    
    private boolean up, down, left, right, gearUp, gearDown;
    
    private Vector2i position;
    
    private Sprite preSprite;
    
    public Car(Sprite sprite, double x, double y){
        this.x = x;
        this.y = y;
        position = new Vector2i(x,y);
        preSprite = sprite;
        this.sprite = sprite;
    }
    
    public Vector2i getLocation(){
        return position;
    }
    
    @Override
    public void render(Screen screen){
        screen.renderMob((int)(x - Level.BLOCK_SIZE), (int)(y - Level.BLOCK_SIZE), this);
    }

    public void setLocation(Vector2i p) {
        this.x = p.dX;
        this.y = p.dY;
    }
    
    public int getGear(){
        return gear;
    }
    
    private double upTime = 0, downTime = 0, leftTime = 0, rightTime = 0;
    @Override
    public void update(){
        
        position = new Vector2i(x,y);
        
        double maxAcceleration = gear * (speed * 5);
        
        if (gearUp && gear < gearMax) gear++;
        if (gearDown && gear > 0) gear--;
        
        // CAR PHYSICS HERE
        if (up && upTime < maxAcceleration) upTime += speed;
        else if (!up && upTime > -maxAcceleration) upTime -= speed;
        
        if (down && downTime < maxAcceleration) downTime += speed;
        else if (!down && downTime > -maxAcceleration) downTime-= speed;
        
        if (left && leftTime < maxAcceleration) leftTime += speed;
        else if (!left && leftTime > -maxAcceleration) leftTime -= speed;
        
        if (right && rightTime < maxAcceleration) rightTime += speed;
        else if (!right && rightTime > -maxAcceleration) rightTime -= speed;
        
        
        vel = new Vector2i(rightTime - leftTime, downTime - upTime);
        
        if (vel.dX != 0 || vel.dY != 0) sprite = Sprite.rotate(preSprite, vel.angle());
        
        if (vel.dY != 0 || vel.dX != 0) {
            move(vel.dX, vel.dY);
        }
    }

    public void setSpeed(float spd) {
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
            case KeyEvent.VK_SHIFT:
                if(gear < gearMax) gear++;
                return true;
            case KeyEvent.VK_CONTROL:
                if (gear > 1) gear--;
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
