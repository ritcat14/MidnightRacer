package entity.upgrades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import tools.Variables;
import entity.mob.Player;
import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.GUI.GUIMessage;
import graphics.sprite.Sprite;
import handlers.DataHandler;
import handlers.StateHandler;

public class Upgrade extends GUIButton {
    
    protected Sprite sprite;
    
    protected int upgradeLevel = 1;
    protected int costPerUpgrade = 5;
    
    protected GUILabel upgradeLev;
    protected GUILabel upgradeCost;
    
    public Upgrade(Sprite sprite, int x, int y, int costPerUpgrade){
        super(x, y, 50, 50, DataHandler.spriteToImage(sprite));
        this.costPerUpgrade = costPerUpgrade;
        this.sprite = sprite;
        
        upgradeLev = new GUILabel(x, y + height, "lvl. " + upgradeLevel).setFont(new Font("Arial", Font.BOLD, 15)).setColour(Color.DARK_GRAY);
        add(upgradeLev);
        
        upgradeCost = new GUILabel(x + width, y + height, "$" + upgradeLevel * costPerUpgrade).setFont(new Font("Arial", Font.BOLD, 15)).setColour(Color.DARK_GRAY);
        add(upgradeCost);
    }
    
    private static boolean firstTime = true;
    @Override
    public boolean onMousePressed(MousePressedEvent e) {
        if(firstTime) {
            firstTime = false;
            StateHandler.state.onEvent(e);
        }
        if (super.onMousePressed(e)){
            if (Variables.money > costPerUpgrade * upgradeLevel) {
                Variables.money -= costPerUpgrade * upgradeLevel;
                upgradeLevel ++;
                upgradeLev.setText("lvl. " + upgradeLevel);
                upgradeCost.setText("$" + costPerUpgrade * upgradeLevel);
                
            } else {
                add(new GUIMessage(100, 20, "Not enough money!", 7));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void render(Graphics2D g){
        this.image = DataHandler.spriteToImage(sprite);
        super.render(g);
    }
    
}
