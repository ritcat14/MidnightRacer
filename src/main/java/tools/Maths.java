package tools;

public class Maths {
    
    public static Vector2i Impulse(double mass, Vector2i v, Vector2i u){
        double vi = v.dX;
        double vj = v.dY;
        double ui = u.dX;
        double uj = u.dY;
        double ii = mass * vi - ui;
        double ij = mass * vj - uj;
        return new Vector2i(ii, ij);
    }
    
    public static double angle(Vector2i vec){
        double vi = vec.dX;
        double vj = vec.dY;
        
        return Math.atan2(vi, vj);
    }
    
}
