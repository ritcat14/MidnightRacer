package graphics.layers.states;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tools.Variables;
import entity.mob.Player;
import entity.upgrades.Upgrade;
import events.*;
import events.types.MousePressedEvent;
import graphics.Screen;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIPanel;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import handlers.ResourceHandler;
import handlers.StateHandler;

public class Garage extends State {

    BufferedImage       backBtn, panel;
    
    ArrayList<Upgrade> upgradeButtons = new ArrayList<Upgrade>();

    GUIPanel            main;
    
    GUILabel playerMoney;
    
    Sprite[] buttonSprites;

    public final Player player;

    public Garage(Player p) {
        super();
        this.player = p;
        loadImages();

        main = new GUIPanel(0, 0, 800, 600, Color.BLACK);

        if (panel != null)
            main = new GUIPanel(panel);

        GUIButton back = new GUIButton(0, 550, 300, 50, "BACK") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e)) {
                    if (player.getHotspot() != null)
                        player.clearMovement(player.getHotspot());
                    StateHandler.changeState(StateHandler.preState, StateHandler.preEnumState);
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

        if (backBtn != null)
            back = new GUIButton((Screen.WIDTH / 2) - (backBtn.getWidth() / 2), Screen.HEIGHT - backBtn.getHeight(), backBtn) {
                @Override
                public boolean onMousePressed(MousePressedEvent e) {
                    if (super.onMousePressed(e)) {
                        if (player.getHotspot() != null)
                            player.clearMovement(player.getHotspot());
                        StateHandler.changeState(StateHandler.preState, StateHandler.preEnumState);
                        return true;
                    }
                    return false;
                }
            };
        main.add(back);
        
        playerMoney = new GUILabel(0, 50, "$ " + Variables.money);
        main.add(playerMoney);

        generateButtons();

        gh.add(main);
    }
    
    @Override
    public void onEvent(Event e){
        super.onEvent(e);
        EventDispatcher dispatcher = new EventDispatcher(e);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return onMousePressed((MousePressedEvent)event);
            }
        });
    }
  
    public boolean onMousePressed(MousePressedEvent e) {
        System.out.println("Mouse Pressed Event fired");
        for (Upgrade b : upgradeButtons){
            System.out.println("Passing event to upgrade");
            if (b.onMousePressed(e)){
                playerMoney.setText("$ " + Variables.money);
                System.out.println("Setting car sprite");
                if (player.getCar() != null) player.getCar().setSprite(buttonSprites[upgradeButtons.indexOf(b)]);
                return true;
            }
        }
        return false;
    }

    private void generateButtons() {
        Sprite[] sprites =
                           new SpriteSheet(ResourceHandler.getSheet("/player/cars/carSprites.png", Level.BLOCK_SIZE * 4,
                                                                    Level.BLOCK_SIZE * 3), 0, 0, 4, 3, Level.BLOCK_SIZE).getSprites();
        buttonSprites = new Sprite[sprites.length];
        int x = 100;
        int y = 100;
        int i = 0;
        for (Sprite s : sprites) {
            System.out.println(s);
            if (s == null)
                continue;
            buttonSprites[i] = s;
            if (x > (Level.BLOCK_SIZE + 100) * 5) {
                x = 100;
                y += Level.BLOCK_SIZE + 100;
            }
            Upgrade b = new Upgrade(s, x, y, 10);
            upgradeButtons.add(b);
            main.add(b);
            x += Level.BLOCK_SIZE + 100;
            i++;
        }
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
