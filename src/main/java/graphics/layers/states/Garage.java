package graphics.layers.states;

import java.awt.Color;

import graphics.Screen;
import graphics.GUI.GUIPanel;

public class Garage extends State {
    
    public Garage(){
        super();
        GUIPanel main = new GUIPanel(100, 100, Screen.WIDTH - 200, Screen.HEIGHT - 200, new Color(0xaaaaaa));
        
    }
    
}
