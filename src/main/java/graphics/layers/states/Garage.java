package graphics.layers.states;

import java.awt.Color;
import java.awt.image.BufferedImage;

import entity.mob.Player;
import entity.upgrades.engines.Engine;
import events.types.MousePressedEvent;
import graphics.Screen;
import graphics.GUI.GUIButton;
import graphics.GUI.GUIPanel;
import graphics.layers.levels.Level;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import handlers.ResourceHandler;
import handlers.StateHandler;

public class Garage extends State {
    
    BufferedImage backBtn, panel;
    
    GUIPanel main;
    
    Sprite curr;
    
    Engine e;
    
    public final Player player;
    
    public Garage(Player p){
        super();
        this.player = p;
        loadImages();

        GUIPanel main = new GUIPanel(0, 0, 800, 600, Color.BLACK);

        if (panel != null)
            main = new GUIPanel(panel);

        GUIButton back = new GUIButton(0, 550, 300, 50, "BACK") {
            @Override
            public boolean onMousePressed(MousePressedEvent e) {
                if (super.onMousePressed(e))
                    StateHandler.changeState(StateHandler.preState, StateHandler.preEnumState);
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
                        if (super.onMousePressed(e)) {
                        if (player.getHotspot() != null) player.clearMovement(player.getHotspot());
                        StateHandler.changeState(StateHandler.preState, StateHandler.preEnumState);
                        return true;
                    }
                    return false;
                }
            };

        main.add(back);
        
        main.add(p.getCar().getEngine());

        gh.add(main);
    }
    
    private void generateButtons(){
        Sprite[] sprites = new SpriteSheet(ResourceHandler.getSheet("/player/cars/carSprites.png", Level.BLOCK_SIZE * 4,
                                                                 Level.BLOCK_SIZE * 4), 0, 0, 4, 3, Level.BLOCK_SIZE).getSprites();
        int x = 0;
        int y = 0;
        for (Sprite s : sprites){
            curr = s;
            if (x > (Level.BLOCK_SIZE + 5) * 5){
                x = 0;
                y += Level.BLOCK_SIZE + 5;
            }
            main.add(new GUIButton(x,y,s.getWidth(), s.getHeight(), "") {
                @Override
                public boolean onMousePressed(MousePressedEvent e) {
                    if (super.onMousePressed(e)) {
                        if (player.getCar() != null) player.getCar().setSprite(curr);
                    }
                    return true;
                }
            });
            x+= Level.BLOCK_SIZE + 5;
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
