package handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import tools.Variables;

public class FileHandler {
    
    private String VersionID = "1.8.3";
    
    private String playerDir = System.getenv("userprofile") + File.separator + "Documents" + File.separator + "MidnightRacer" + File.separator + "Player" + File.separator;
    private String systemDir = System.getenv("userprofile") + File.separator + "Documents" + File.separator + "MidnightRacer" + File.separator + "System" + File.separator;
    
    public String playerFile = playerDir + "Player.mrp";
    public String systemFile = systemDir + "SystemData.mrs";

    private static void createTree(String dir) throws Exception {
        File theDir = new File(dir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }
    
    public boolean install() {
        try {
            createTree(playerDir);
            createTree(systemDir);
            save(systemFile, VersionID);
            save(playerFile, Variables.getPlayerData());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
  
    public boolean exists(String dir){
      File f = new File(dir);
      return f.exists();
    }
  
    public String dataToString(String[] data, String divider, int start, int finish) {
      if (finish < start) return "";
      String line = "";
      if (finish == -1) finish = data.length;
      if (start > finish) return "";
      for (int i = start; i < finish; i++){
        line = line + data[i] + divider;
      }
      line = line.substring(0, line.length() - 1);
      return line;
    }
  
    private int getLineNum(String file){
        FileReader fileReader;
        int i = 0;
        String line;
        try {
            fileReader = new FileReader(file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
              if (line.trim() == null || line.trim().equals("")) continue;
              else i++;
            }

            // Always close files.
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      return i;
    }
    
    public boolean save(String dir){
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
            writer.println("");
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean save(String dir, String[] tags, String[] data, String divider) {
        if (tags.length != data.length) return false;
        String[] finalData = new String[tags.length];
        for (int i = 0; i < tags.length; i++){
            finalData[i] = tags[i].trim().toUpperCase() + divider + data[i];
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
            for (String s : finalData) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean save(String dir, String[] data) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
            for (String s : data) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean save(String dir, String data, String divider) {
        String[] dtw = data.split(divider);
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
            for (String s : dtw) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean save(String dir, String data){
        String[] dt = {data};
        return save(dir, dt);
    }
    
    public boolean add(String dir, String data) {
        String[] dt = {data};
        return add(dir, dt);
    }
    
    public boolean add(String dir, String data[]) {
        return add(dir, data, getLineNum(dir));
    }
    
    public boolean add(String dir, String[] data, int line) {
        String[] fileData = getData(dir); // Save a copy of the files current data
        if (line > fileData.length || line < 0) return false;
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
            for (String s : fileData) { // Write back previous data
                writer.println(s);
            }
            for (String s : data) { //Write new data to end of file
              writer.println(s);
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean add(String dir, String[] tags, String[] data, String divider, int lineNum) {
        if (tags.length != data.length) return false;
        String[] fileData = getData(dir); // Save a copy of the files current data (untrimmed)
        if (lineNum > fileData.length || lineNum < 0) return false;
        String[] newData = new String[tags.length];
        int items = tags.length;
        for (int i = 0; i < tags.length; i ++){
            newData[i] = tags[i].trim().toUpperCase() + divider + data[i];
        }
        for (int i = lineNum; i < items + lineNum; i++) {
            fileData[i] = newData[i - lineNum];
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
          for (String s : fileData) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean add(String dir, String data, int line) {
        String[] dt = {data};
        return add(dir, dt, line);
    }
    
    public boolean add(String dir, String data, String tag, String divider, int line) {
        String[] dt = {data};
        String[] tg = {tag.trim().toUpperCase()};
        return add(dir, dt, tg, divider, line);
    }
    
    public boolean remove(String dir, String[] data, String divider, boolean tag) {
        String[] fileData = getData(dir); // Save a copy of the files current data (untrimmed)
        int part = 0;
        if (!tag) part = 1;
        
        for (int i = 0; i < fileData.length; i++){
            for (int j = 0; j < data.length; j++){
                if (fileData[i].split(divider)[part].equals(data[j])){
                    fileData[i] = "null";
                }
            }
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
          for (String s : fileData) if (!s.equals("null")) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean remove(String dir, String data, String divider, boolean tag) {
        String[] dt = {data};
        return remove(dir, dt, divider, false);
    }
    
    public boolean remove(String dir, int line) {
        String[] fileData = getData(dir); // Save a copy of the files current data (untrimmed)
        if (line > fileData.length || line < 0) return false;
        fileData[line] = "null";
        PrintWriter writer;
        try {
            writer = new PrintWriter(dir, "UTF-8");
          for (String s : fileData) if (!s.equals("null")) writer.println(s);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getData(String file) {
      return getData(file, getLineNum(file));
    }
    
    public String getData(String dir, String divider, String tag) {
        String[] fileData = getData(dir);
        for (int i = 0; i < fileData.length; i++){
            if (fileData[i].split(divider)[0].equals(tag.trim().toUpperCase())) return fileData[i].split(divider)[1];
        }
        return "";
    }
  
    public String[] getData(String dir, int lineNum){
        String line;
        String[] data = new String[lineNum];
        FileReader fileReader;
        try {
            fileReader = new FileReader(dir);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
				int i = -1;
            while ((line = bufferedReader.readLine()) != null) {
              i++;
              if (i == lineNum) break;
              data[i] = line;
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      return data;
    }
    
    public String[] getData(String dir, String[] tags, String divider) {
        String[] data = new String[tags.length];
        String[] fileData = getData(dir);
        for (int i = 0; i < fileData.length; i++){
            for (int j = 0; j < tags.length; j++){
                if (fileData[i].split(divider)[0].equals(tags[j].trim().toUpperCase())) data[j] = fileData[i].split(divider)[1];
            }
        }
        return data;
    }
    
    public String[] getData(String dir, int startLine, int finLine) {
        if (finLine < startLine) return new String[0];
        String[] fileData = getData(dir);
        String[] data = new String[(finLine - startLine) + 1];
        int i = 0;
        for (int j = startLine; j < finLine; j++){
            data[i] = fileData[j];
            i++;
        }
        return data;
    }
    
}
