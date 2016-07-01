package graphics.layers.states;

import events.types.MousePressedEvent;
import graphics.Screen;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIPanel;
import handlers.FileHandler;
import handlers.ResourceHandler;
import handlers.StateHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import tools.Variables;

public class Install extends State {

    private BufferedImage panel;

    public Install() {
        super();
        Variables.installed = false;
        loadImages();

        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.BLACK);

        if (panel != null)
            main = new GUIPanel(panel);

        GUIButton install = new GUIButton((Screen.WIDTH / 2) - 150, (Screen.HEIGHT / 2) - 25, 300, 50, "INSTALL") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e)){
                    FileHandler f = StateHandler.fileHandler;
                    if (!f.install()) System.out.println("Can't install");
                    else StateHandler.changeState(StateHandler.States.START);
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

        main.add(install);
        
        GUILabel warning = new GUILabel(install.x, install.y + 80, "Warning:\n By clicking install, you agree this application\n can install files required to run\n at the following locations:\n 1) "
                + StateHandler.fileHandler.playerFile + "\n 2) "
                + StateHandler.fileHandler.systemFile).setColour(Color.RED).setFont(new Font("Arial", Font.PLAIN, 20));
        main.add(warning);

        gh.add(main);
    }

    public void loadImages() {
        try {
            panel = ResourceHandler.getImage("/GUI/menus/settings menu.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
