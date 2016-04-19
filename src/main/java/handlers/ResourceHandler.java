package handlers;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
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
  
    public static String[][] getElementData(InputStream is, int layerNum) throws Exception {
      String[] solids = null;
      String[][] allData = new String[layerNum + 3][];
      int width = 0, height = 0, tileCount = 0;
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
      Document doc = docBuilder.parse(is);
      doc.getDocumentElement().normalize();
      
      NodeList nList = doc.getElementsByTagName("tileset");
      
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
      allData[allData.length - 1] = solids;
      String[] h = { "" + height};
      String[] w = { "" + width};
      allData[allData.length - 2] = h;
      allData[allData.length - 3] = w;
      return allData;
    }

    public static InputStream getResource(String dir) throws ClassNotFoundException {
        Class cls = Class.forName("Main");

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();
        // input stream
        InputStream i = cLoader.getResourceAsStream(dir);
        return i;
    }

    public static void parse(InputStream is) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("student");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element)nNode;
                System.out.println("Student roll no : " + eElement.getAttribute("rollno"));
                System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                System.out.println("Marks : " + eElement.getElementsByTagName("marks").item(0).getTextContent());
            }
        }
    }
}
