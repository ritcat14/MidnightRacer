package tools;

public class Variables {
    
    //Player
    public static int money = 0;
    public static String name = "Kris";
    public static boolean installed = true;
    
    public static int runningTime = 0;
    
    //Save
    public static String[] getPlayerData(){
        String[] data = {"Name:" + name, "Money:" + money};
        return data;
    }
    
}
