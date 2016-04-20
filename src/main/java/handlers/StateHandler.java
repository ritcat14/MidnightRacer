package handlers;

import events.Event;
import events.EventListener;
import graphics.Screen;
import graphics.layers.states.*;

import java.awt.Graphics2D;

public class StateHandler implements EventListener {

    public static enum States {
        GAME,
        START,
        PAUSE,
        SETTINGS;
    };

    private static State state;
    public static States currState;
  
    public StateHandler(){
      changeState(States.START);
    }

    public void update() {
        state.update();
    }

    public void render(Graphics2D g, Screen screen) {
        switch (currState) {
            case GAME:
                state.render(g, screen);
                break;
            case START:
          		 state.render(g);
                break;
            case PAUSE:
                state.render(g);
                break;
            case SETTINGS:
          		 state.render(g);
          		 break;
        }
    }

    public static void changeState(States ste) {
        if (state != null) state.clear();
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
