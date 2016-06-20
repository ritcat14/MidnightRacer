package handlers;

import events.Event;
import events.EventListener;
import graphics.Screen;
import graphics.layers.states.*;

import java.awt.Graphics2D;

public class StateHandler implements EventListener {
    
    public static FileHandler fileHandler = new FileHandler();

    public static enum States {
        INSTALL,
        GAME,
        START,
        PAUSE,
        SETTINGS,
        GARAGE;
    };

    private static State state;
    public static State preState;
    public static States preEnumState;
    public static States currState;
  
    public StateHandler(){
      FileHandler f = StateHandler.fileHandler;
      if (!f.exists(f.systemFile)) changeState(States.INSTALL);
      else changeState(States.START);
    }

    public void update() {
        state.update();
    }

    public void render(Graphics2D g, Screen screen) {
        switch (currState) {
            case GAME:
                state.render(g, screen);
                break;
            default:
          		 state.render(g);
                break;
        }
    }
    
    public static void changeState(State ste, States st) {
        currState = st;
        state = ste;
    }

    public static void changeState(States ste) {
        preState = state;
        preEnumState = currState;
        //if (state != null) state.clear();
        currState = ste;
        switch (currState) {
            case GAME:
                StateHandler.state = new Game();
                break;
            case START:
                StateHandler.state = new Start();
                break;
            case PAUSE:
                StateHandler.state = new Pause();
                break;
            case SETTINGS:
          		 StateHandler.state = new Settings();
          		 break;
          	case GARAGE:
          	     StateHandler.state = new Garage(((Game)state).player);
          	     break;
          	 case INSTALL:
          	     StateHandler.state = new Install();
          	     break;
        }
    }
  
    public static void clear(){
      state = null;
      System.exit(0);
    }

    @Override
    public void onEvent(Event event) {
        state.onEvent(event);
    }

}
