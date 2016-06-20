package entity.upgrades;

import tools.Variables;
import events.types.MousePressedEvent;
import graphics.GUI.GUIButton;
import graphics.GUI.GUILabel;
import graphics.sprite.Sprite;
import handlers.DataHandler;

public abstract class Upgrade extends GUIButton {
    
    protected Sprite sprite;
    
    protected int upgradeLevel = 1;
    protected int costPerUpgrade = 5;
    
    protected GUILabel upgradeLev;
    protected GUILabel upgradeCost;
    
    public Upgrade(Sprite sprite, int x, int y, int costPerUpgrade){
        super(x, y, DataHandler.spriteToImage(sprite));
        this.costPerUpgrade = costPerUpgrade;
        this.sprite = sprite;
        
        upgradeLev = new GUILabel(x, y + height, "lvl. " + upgradeLevel);
        add(upgradeLev);
        
        upgradeCost = new GUILabel(x + width, y + height, "$" + upgradeLevel * costPerUpgrade);
        add(upgradeCost);
    }
    
    @Override
    public boolean onMousePressed(MousePressedEvent e) {
        if (super.onMousePressed(e)){
            if (Variables.money > costPerUpgrade * upgradeLevel){
                Variables.money -= costPerUpgrade * upgradeLevel;
                upgradeLevel ++;
                upgradeLev.setText("lvl. " + upgradeLevel);
                upgradeCost.setText("$" + costPerUpgrade * upgradeLevel);
            }
            return true;
        }
        return false;
    }
    
}
