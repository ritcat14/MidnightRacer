package entity.mob.car;

import entity.mob.Mob;
import entity.mob.car.engines.Engine;
import entity.mob.car.tyres.Tyre;
import graphics.Screen;

public abstract class Car extends Mob {
    
    private Engine engine;
    private Tyre[] typre = new Tyre[3];
    
    public Car(){}
    
    @Override
    public void render(Screen screen){
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }
    
    @Override
    public void update(){
        
    }
}
