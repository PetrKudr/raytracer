package ru.spb.petrk.scenerenderer.util;

/**
 *
 * @author PetrK
 */
public class Vector3 {
    
    public static final Vector3 ZERO_VECTOR = new Vector3(0, 0, 0);
    
    
    protected double x;
    
    protected double y;
    
    protected double z;

    
    public Vector3() {
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3(Vector3 startPoint, Vector3 endPoint) {
        this.x = endPoint.getX() - startPoint.getX();
        this.y = endPoint.getY() - startPoint.getY();
        this.z = endPoint.getZ() - startPoint.getZ();
    }    
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }        
    
    public double getLength() {
        return Math.sqrt(
                MathUtils.sqr(x) +
                MathUtils.sqr(y) +
                MathUtils.sqr(z)
        );
    }
    
    /**
     * Multiplies vector on scalar value
     * @param value - double value
     * @return vector
     */
    public Vector3 multiply(double value) {
        return new Vector3(
                x * value,
                y * value,
                z * value
        );
    }
    
    /**
     * Adds vector to this vector
     * @param vector - another vector
     * @return this vector + another vector
     */
    public Vector3 add(Vector3 vector) {
        return new Vector3(
                x + vector.x, 
                y + vector.y, 
                z + vector.z
        );
    }
    
    /**
     * Subtracts vector from this vector
     * @param vector - another vector
     * @return this vector - another vector
     */    
    public Vector3 subtract(Vector3 vector) {
        return new Vector3(
                x - vector.x, 
                y - vector.y, 
                z - vector.z
        );        
    }    
    
    /**
     * 
     * @return inversed vector
     */
    public Vector3 inverse() {
        return new Vector3(-x, -y, -z);
    }    
    
    /**
     * 
     * @return normalized vector
     */
    public Vector3 normalize() {
        double norm = getLength();        
        return new Vector3(x / norm, y / norm, z / norm);
    }

    /**
     * Returns the dot product of the current vector's direction with the other vector's direction
     * @param other - other vector
     * @return dot product
     */
    public double dotProduct(Vector3 other) {
        return (x * other.x + y * other.y + z * other.z);
    }
    
    /**
     * Returns the cross product of the current vector's direction with the other vector's direction
     * @param other - other vector
     * @return cross product
     */
    public Vector3 crossProduct(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y, 
                z * other.x - x * other.z, 
                x * other.y - y * other.x
        );
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector3 other = (Vector3) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + "," + z + ")";
    }
    
}
