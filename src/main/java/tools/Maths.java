package tools;

public class Maths {
    
    public static Vector2i Impulse(double mass, Vector2i v, Vector2i u){
        double vi = v.x;
        double vj = v.y;
        double ui = u.x;
        double uj = u.y;
        double ii = mass * vi - ui;
        double ij = mass * vj - uj;
        return new Vector2i(ii, ij);
    }
    
    public static double angle(Vector2i vec){
        double vi = vec.x;
        double vj = vec.y;
        
        return Math.atan2(vi, vj);
    }
    
    public static double angle(Vector2i a, Vector2i b){
        double modv1 = a.mod();
        double modv2 = b.mod();
        double ab = a.multiply(b);
        return Math.acos(ab / (modv1 * modv2));
    }
    
    public static Vector2i v1(double m1, Vector2i u1, double m2, Vector2i v2, Vector2i u2){
        // m1v1 - m1u1 = m2v2 - m2u2
        // v1 = (m2v2 - m2u2 + m1u1) / m1
        return (v2.multiply(m2).subtract(u2.multiply(m2))).add(u1.multiply(m1)).divide(m1);
    }
    
}
