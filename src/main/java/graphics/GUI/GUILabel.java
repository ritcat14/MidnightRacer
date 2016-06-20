package graphics.GUI;

import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class GUILabel extends GUIComponent {
  
    public String text = "";
    protected Font font = null;
    private FontMetrics fm = null;
    private Color colour;
  
    public GUILabel(int x, int y, String text) {
        super(x, y);
        this.text = text;
        font = new Font("Impact", Font.PLAIN, 35);
        colour = new Color(0xfaf572);
    }
  
    public GUILabel setFont(Font font){
      this.font = font;
      return this;
    }
  
    public FontMetrics getFontMetrics(){ return fm; }
  
    public String getText(){ return text; }
  
    public GUILabel setColour(Color c){
      colour = c;
      return this;
    }
  
    public void setText(String text){ this.text = text; }
  
    public void render(Graphics2D g) {
        fm = g.getFontMetrics();
        g.setFont(font);
        g.setColor(Color.BLACK);
        String[] parts = text.split("\n");
        int y0 = 0;
        for (String s : parts){
            g.drawString(s, x + 1, y + 2 + y0);
            y0+=fm.getHeight();
        }
        g.setColor(colour);
        y0 = 0;
        for (String s : parts){
            g.drawString(s, x + 1, y + 2 + y0);
            y0+=fm.getHeight();
        }
    }
}