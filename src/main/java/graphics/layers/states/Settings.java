package graphics.layers.states;

import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIPanel;
import handlers.StateHandler;

import java.awt.Color;
import java.awt.Font;

public class Settings extends State {
  
  public Settings(){
    super();
    
    GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.BLACK);
    
    GUILabel title = new GUILabel(0, 32, "SETTINGS").setFont(new Font("Impact", Font.PLAIN, 32)).setColour(Color.RED);
    main.add(title);
    
    GUIButton back = new GUIButton(0, 550, 300, 50, "BACK"){
      @Override
      public boolean onMousePressed(MousePressedEvent e) {
        System.out.println("Pressed back");
        if (super.onMousePressed(e)) StateHandler.changeState(StateHandler.States.START);
        return true;
    	}
    };
    main.add(back);
    
    gh.add(main);
  }
  
}
