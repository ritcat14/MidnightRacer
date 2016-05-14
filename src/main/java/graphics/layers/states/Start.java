package graphics.layers.states;

import java.awt.Color;
import java.awt.image.BufferedImage;

import events.types.MousePressedEvent;
import graphics.Screen;
import graphics.GUI.GUIButton;
import graphics.GUI.GUIPanel;
import handlers.ResourceHandler;
import handlers.StateHandler;

public class Start extends State {
    
    private BufferedImage panel, startBtn, settBtn, exitBtn;

    public Start() {
        super();
        loadImages();
        
        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.GRAY);
        
        if (panel != null) main = new GUIPanel(panel);

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
        
        if (startBtn != null) start = new GUIButton((Screen.WIDTH / 2) - (startBtn.getWidth() / 2), 100, startBtn) {
                @Override
                public boolean onMousePressed(MousePressedEvent e) {
                    if (super.onMousePressed(e)) {
                        StateHandler.changeState(StateHandler.States.GAME);
                        return true;
                    }
                    return false;
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
        
        if (settBtn != null) settings = new GUIButton((Screen.WIDTH / 2) - (settBtn.getWidth() / 2), 150, settBtn) {
                @Override
                public boolean onMousePressed(MousePressedEvent e) {
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

            @Override
            public void update() {
                super.update();
                animate();
            }
        };
        
        if (exitBtn != null) exit = new GUIButton((Screen.WIDTH / 2) - (exitBtn.getWidth() / 2), 200, exitBtn) {
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

    public void loadImages() {
        try {
            panel = ResourceHandler.getImage("/GUI/menus/start screen.png");
            startBtn = ResourceHandler.getImage("/GUI/buttons/start.png");
            settBtn = ResourceHandler.getImage("/GUI/buttons/settings.png");
            exitBtn = ResourceHandler.getImage("/GUI/buttons/exit.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
