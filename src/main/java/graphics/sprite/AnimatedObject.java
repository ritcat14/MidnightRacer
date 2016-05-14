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
    private Sprite[]             sprites;

    public AnimatedObject(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;
        sprites = sheet.getSprites();
        sprite = sprites[0];
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
            sprite = sprites[frame];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRate(int frames) {
        this.frame = frames;
    }
    
    public Sprite[] getSprites(){
        return sprites;
    }
    
    public AnimatedObject setSprites(Sprite[] sprites){
        this.sprites = sprites;
        return this;
    }
    
    public SpriteSheet getSheet(){
        return sheet;
    }
    
    public int getLength(){
        return length;
    }
    
    public static AnimatedObject rotate(AnimatedObject a, double angle){
        Sprite[] spritesTemp = a.getSprites();
        for (int i = 0; i < spritesTemp.length; i++){
            spritesTemp[i] = Sprite.rotate(spritesTemp[i], angle);
        }
        return new AnimatedObject(a.getSheet(), a.getWidth(), a.getHeight(), a.getLength()).setSprites(spritesTemp);
    }

    public void setFrame(int index) {
        if (index > sheet.getSprites().length - 1) {
            System.err.println("Index out of bounds in " + this);
            return;
        }
        sprite = sprites[index];
    }
  
    public void setSpriteSheet(SpriteSheet sheet){
      this.sheet = sheet;
    }
  
    public int getFrame(){
      return frame;
    }

}
