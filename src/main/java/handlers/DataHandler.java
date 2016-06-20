package handlers;

import java.awt.image.BufferedImage;

import graphics.sprite.Sprite;

public class DataHandler {
  
   public static BufferedImage spriteToImage(Sprite sprite){ //converts sprite to image and returns it to the inventory for storing the item
       int[] pixels = new int[sprite.getWidth() * sprite.getHeight()];
       int[] spritePixels = sprite.getPixels();
       int width = sprite.getWidth();
       int height = sprite.getHeight();
       for (int x = 0; x < width; x++){
           for(int y = 0; y < height; y++){
               int col = spritePixels[x + y * width];
               if (col != 0xffff00ff) pixels[x + y * sprite.getWidth()] = col;
           }
       }
       BufferedImage image = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
       image.setRGB(0, 0, width, height, pixels, 0, width);
       return image;
   }
   
}
