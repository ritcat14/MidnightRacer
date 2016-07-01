package handlers;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class ResourceHandler {

    private static ArrayList<SpriteSheet> sheets = new ArrayList<SpriteSheet>();

    public static SpriteSheet getSheet(String dir, int width, int height) {
        for (int i = sheets.size() - 1; i > -1; i--) {
            if (sheets.get(i).getPath().equals(dir)) {
                return sheets.get(i);
            }
        }
        SpriteSheet s = new SpriteSheet(dir, width, height);
        sheets.add(s);
        return s;
    }
  
    public static Sprite createSprite(int width, int height, int col){
      Sprite s = null;
      
      return s;
    }
  
    public static String[][] getElementData(InputStream is) throws Exception {
      String[] solids = null;
      String[] objects = null;
      String[] mapInfo = new String[4];
      String[] imageInfo = new String[3];
      int width = 0, height = 0, tileCount = 0, objectCount = 0, tileSize = 0, layerNum = 0;
      
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
      Document doc = docBuilder.parse(is);
      doc.getDocumentElement().normalize();
      
      NodeList nList = doc.getElementsByTagName("layer");
      layerNum = nList.getLength();
      
      String[][] allData = new String[layerNum + 4][];
      
      nList = doc.getElementsByTagName("map");
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tileSize = Integer.parseInt(eElement.getAttribute("tilewidth"));
        }
      }
      
      nList = doc.getElementsByTagName("tileset");
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
          solids = new String[tileCount];
        }
      }
      
      nList = doc.getElementsByTagName("layer");
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          width = Integer.parseInt(eElement.getAttribute("width"));
          height = Integer.parseInt(eElement.getAttribute("height"));
          String[] tileData = new String[width * height];
          String data = eElement.getElementsByTagName("data").item(0).getTextContent();
          String[] dataArray = data.split(",");
          for (int i = 0; i < dataArray.length; i++){
            if (dataArray[i] != "" && dataArray[i] != "," && dataArray[i] != " "){
                tileData[i] = dataArray[i].trim();
            }
          }
          allData[temp] = tileData;
        }
      }
      
      nList = doc.getElementsByTagName("tile");
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          int ID = Integer.parseInt(eElement.getAttribute("id"));
          NodeList properties = eElement.getElementsByTagName("properties");
          Element propertiesEl = (Element)properties.item(0);
          
          NodeList property = propertiesEl.getElementsByTagName("property");
          Element valueElement = (Element)property.item(0);
          solids[temp] = ID + "," + valueElement.getAttribute("value");
        }
      }
      
      nList = doc.getElementsByTagName("object");
      objectCount = nList.getLength();
      objects = new String[objectCount];
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          NodeList properties = eElement.getElementsByTagName("property");
          Element propertiesEl = (Element)properties.item(0);
          
          objects[temp] = eElement.getAttribute("x") + "," + eElement.getAttribute("y") + "," + eElement.getAttribute("width") + "," + eElement.getAttribute("height")
            + "," + propertiesEl.getAttribute("value");
        }
      }
      
      nList = doc.getElementsByTagName("image");
      
      for (int temp = 0; temp < nList.getLength(); temp++){
        Node node = nList.item(temp);
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          imageInfo[0] = eElement.getAttribute("source");
          imageInfo[1] = eElement.getAttribute("width");
          imageInfo[2] = eElement.getAttribute("height");
        }
      }
      
      allData[allData.length - 1] = imageInfo;
      allData[allData.length - 2] = objects;
      allData[allData.length - 3] = solids;
      mapInfo[0] = "" + width;
      mapInfo[1] = "" + height;
      mapInfo[2] = "" + tileSize;
      mapInfo[3] = "" + layerNum;
      allData[allData.length - 4] = mapInfo;
      return allData;
    }
    
    public static BufferedImage getImage(String dir) throws Exception {
        Class cls = Class.forName("Main");
        return ImageIO.read(cls.getResource(dir));
    }

    public static InputStream getResource(String dir) throws ClassNotFoundException {
        Class cls = Class.forName("Main");

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();
        // input stream
        InputStream i = cLoader.getResourceAsStream(dir);
        return i;
    }
}
