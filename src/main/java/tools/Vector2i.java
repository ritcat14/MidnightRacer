package tools;

import java.lang.Math;

public class Vector2i {

    public double dX;
    public double dY;

    // Constructor methods ....

    public Vector2i() {
        dX = dY = 0.0;
    }

    public Vector2i(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }

    // Convert vector to a string ...

    public String toString() {
        return "Vector(" + dX + ", " + dY + ")";
    }

    // Compute magnitude of vector ....

    public double length() {
        return Math.sqrt(dX * dX + dY * dY);
    }

    // Sum of two vectors ....

    public Vector2i add(Vector2i v1) {
        Vector2i v2 = new Vector2i(this.dX + v1.dX, this.dY + v1.dY);
        return v2;
    }

    // Subtract vector v1 from v .....

    public Vector2i sub(Vector2i v1) {
        Vector2i v2 = new Vector2i(this.dX - v1.dX, this.dY - v1.dY);
        return v2;
    }

    // Scale vector by a constant ...

    public Vector2i scale(double scaleFactor) {
        Vector2i v2 = new Vector2i(this.dX * scaleFactor, this.dY * scaleFactor);
        return v2;
    }

    // Normalize a vectors length....

    public Vector2i normalize() {
        Vector2i v2 = new Vector2i();

        double length = Math.sqrt(this.dX * this.dX + this.dY * this.dY);
        if (length != 0) {
            v2.dX = this.dX / length;
            v2.dY = this.dY / length;
        }

        return v2;
    }
    
    // Magnitude of vector
    public double magnitude() {
        return Math.sqrt((dX * dX) + (dY * dY));
    }

    // Dot product of two vectors .....

    public double dotProduct(Vector2i v1) {
        return this.dX * v1.dX + this.dY * v1.dY;
    }
    
    // Angle of vector between i a,d j component
    
    public double angle(){
        // tan(theta) = j / i
        return Math.atan2(dY,dX);
    }
}
