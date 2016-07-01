package entity.upgrades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import tools.Variables;
import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.sprite.Sprite;
import handlers.DataHandler;

public class Upgrade extends GUIButton {
    
    protected Sprite sprite;
    
    protected int upgradeLevel = 1;
    protected int costPerUpgrade = 5;
    
    protected GUILabel upgradeLev;
    protected GUILabel upgradeCost;
    
    public Upgrade(Sprite sprite, int x, int y, int costPerUpgrade){
        super(x, y, DataHandler.spriteToImage(sprite));
        this.costPerUpgrade = costPerUpgrade;
        this.sprite = sprite;
        this.width = 50;
        this.height = 50;
        
        upgradeLev = new GUILabel(x, y + height, "lvl. " + upgradeLevel).setFont(new Font("Arial", Font.BOLD, 15)).setColour(Color.DARK_GRAY);
        add(upgradeLev);
        
        upgradeCost = new GUILabel(x + width, y + height, "$" + upgradeLevel * costPerUpgrade).setFont(new Font("Arial", Font.BOLD, 15)).setColour(Color.DARK_GRAY);
        add(upgradeCost);
    }
    
    @Override
    public boolean onMousePressed(MousePressedEvent e) {
        System.out.println("Upgrade recieved event");
        if (super.onMousePressed(e)){
            System.out.println("Event belongs to button");
            if (Variables.money > costPerUpgrade * upgradeLevel){
                Variables.money -= costPerUpgrade * upgradeLevel;
                upgradeLevel ++;
                upgradeLev.setText("lvl. " + upgradeLevel);
                upgradeCost.setText("$" + costPerUpgrade * upgradeLevel);
            }
            return true;
        }
        System.out.println("Event not in bounds");
        return false;
    }
    
    @Override
    public void render(Graphics2D g){
        this.image = DataHandler.spriteToImage(sprite);
        super.render(g);
    }
    
}
