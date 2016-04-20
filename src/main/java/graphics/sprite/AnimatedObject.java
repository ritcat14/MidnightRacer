package graphics.sprite;

public class AnimatedObject extends Sprite {

    //walking animations
    public static AnimatedObject up = null;
    public static AnimatedObject down = null;
    public static AnimatedObject left = null;
    public static AnimatedObject right = null;

    private int                  frame                = 0;
    private Sprite               sprite;
    private int                  rate                 = 5;
    private int                  time                 = 0;
    private int                  length               = -1;

    public AnimatedObject(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;
        sprite = sheet.getSprites()[0];
        if (length > sheet.getSprites().length)
            System.out.println("ERROR");
    }

    public void update() {
        time++;
        if (time % rate == 0) {
            if (frame >= length - 1)
                frame = 0;
            else
                frame++;
            sprite = sheet.getSprites()[frame];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRate(int frames) {
        this.frame = frames;
    }

    public void setFrame(int index) {
        if (index > sheet.getSprites().length - 1) {
            System.err.println("Index out of bounds in " + this);
            return;
        }
        sprite = sheet.getSprites()[index];
    }
  
    public void setSpriteSheet(SpriteSheet sheet){
      this.sheet = sheet;
    }
  
    public int getFrame(){
      return frame;
    }

}
