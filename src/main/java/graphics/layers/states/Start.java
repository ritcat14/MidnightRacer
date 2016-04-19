package graphics.layers.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIPanel;
import handlers.GUIHandler;
import handlers.StateHandler;
import events.Mouse;

public class Start extends State {

    public Start() {
        super();
        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.GRAY);

        GUILabel title = new GUILabel(0, 50, "MIDNIGHT RIDER").setFont(new Font("Impact", Font.PLAIN, 50)).setColour(Color.RED);
        main.add(title);

        GUIButton start = new GUIButton(0, 100, 300, 50, "START") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e)) {
                    StateHandler.changeState(StateHandler.States.GAME);
                    return true;
                }
                return false;
            }
          
            @Override
            public void update(){
              super.update();
              if (this.bounds.contains(new Point(Mouse.getX(), Mouse.getY()))) if (this.width < 330) this.width += 3;
              else if (this.width > 300) this.width = 300;
            }
        };
        main.add(start);
      
        GUIButton settings = new GUIButton(0, 200, 300, 50, "SETTINGS"){
          @Override
          public boolean onMousePressed(MousePressedEvent e){
            if (super.onMousePressed(e)) {
              StateHandler.changeState(StateHandler.States.SETTINGS);
              return true;
            }
            return false;
          }
        };
        main.add(settings);

        GUIButton exit = new GUIButton(0, 300, 300, 50, "EXIT") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e)) {
                    StateHandler.clear();
                    return true;
                }
                return false;
            }
        };
      
        main.add(exit);

        gh.add(main);
    }

}
