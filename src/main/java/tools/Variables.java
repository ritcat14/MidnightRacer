package tools;

public class Variables {
    
    //System
    public static int runningTime = 0;
    public static String versionID = "1.5";
    
    
    //Player
    public static int money = 0;
    public static String name = "Guest";
    public static boolean installed = true;
    public static int carID = 0;
    
    
    //Save
    public static String[] getPlayerData(){
        String[] data = {"NAME:" + name, "MONEY:" + money, "CAR:" + carID};
        return data;
    }
    
    public static String[] getSystemData(){
        String[] data = {"VERSION:" + versionID, "TIME:" + runningTime};
        return data;
    }

    
}
