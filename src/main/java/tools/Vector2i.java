package tools;

public class Vector2i {

    public double x = 0, y = 0;

    public Vector2i() {
        set(0, 0);
    }

    public Vector2i(Vector2i vector) {
        set(vector.x, vector.y);
    }

    public Vector2i(double x, double y) {
        set(x, y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public double mod(){
        return Math.sqrt((x * x) + (y * y));
    }

    public Vector2i add(Vector2i vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector2i add(double value) {
        this.x += value;
        this.y += value;
        return this;
    }

    public Vector2i subtract(Vector2i vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public Vector2i setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(double y) {
        this.y = y;
        return this;
    }
    
    public double multiply(Vector2i a){
        double x1 = x * a.x;
        double y1 = y * a.y;
        return x1 + y1;
    }
    
    public Vector2i multiply(double m){
        x = x * m;
        y = y * m;
        return this;
    }
    
    public Vector2i divide(double d){
        x = x / d;
        y = y / d;
        return this;
    }

    public int hashCode() {
        return (int)(this.x * this.y);
    }

    public boolean samePos(Vector2i n) {
        return (getX() == n.getX() && getY() == n.getY());
    }

    public static double getDistance(Vector2i v0, Vector2i v1) {
        double x = v0.getX() - v1.getX();
        double y = v0.getY() - v1.getY();
        return Math.sqrt(x * x + y * y);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Vector2i))
            return false;
        Vector2i vec = (Vector2i)object;
        if (vec.getX() == this.getX() && vec.getY() == this.getY())
            return true;
        else
            return false;
    }
}
