package graphics.layers.states;

import events.types.MousePressedEvent;
import graphics.Screen;
import graphics.GUI.GUIButton;
import graphics.GUI.GUIPanel;
import handlers.ResourceHandler;
import handlers.StateHandler;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Settings extends State {

    private BufferedImage panel, backBtn;

    public Settings() {
        super();
        loadImages();

        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.BLACK);

        if (panel != null)
            main = new GUIPanel(panel);

        GUIButton back = new GUIButton(0, 550, 300, 50, "BACK") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                System.out.println("Pressed back");
                if (super.onMousePressed(e))
                    StateHandler.changeState(StateHandler.States.START);
                return true;
            }

            @Override
            public void update() {
                super.update();
                animate();
            }
        };

        if (backBtn != null)
            back = new GUIButton((Screen.WIDTH / 2) - (backBtn.getWidth() / 2), Screen.HEIGHT - backBtn.getHeight(), backBtn) {
                @Override
                public boolean onMousePressed(MousePressedEvent e) {
                    System.out.println("Pressed back");
                    if (super.onMousePressed(e))
                        StateHandler.changeState(StateHandler.States.START);
                    return true;
                }
            };

        main.add(back);

        gh.add(main);
    }

    public void loadImages() {
        try {
            panel = ResourceHandler.getImage("/GUI/menus/settings menu.png");
            backBtn = ResourceHandler.getImage("/GUI/buttons/back.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
