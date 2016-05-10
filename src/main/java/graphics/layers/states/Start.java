package graphics.layers.states;

import java.awt.Color;

import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUIPanel;
import handlers.ResourceHandler;
import handlers.StateHandler;

public class Start extends State {

    public Start() {
        super();
        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.GRAY);
        try {
            main = new GUIPanel(ResourceHandler.getImage("/GUI/start screen.png"));
        } catch (Exception e){
            e.printStackTrace();
        }

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
            public void update() {
                super.update();
                animate();
            }
        };
        main.add(start);

        GUIButton settings = new GUIButton(0, 200, 300, 50, "SETTINGS") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e)) {
                    StateHandler.changeState(StateHandler.States.SETTINGS);
                    return true;
                }
                return false;
            }

            @Override
            public void update() {
                super.update();
                animate();
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

            @Override
            public void update() {
                super.update();
                animate();
            }
        };

        main.add(exit);

        gh.add(main);
    }

}
