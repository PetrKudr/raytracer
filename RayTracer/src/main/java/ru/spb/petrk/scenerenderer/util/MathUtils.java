
package ru.spb.petrk.scenerenderer.util;

import java.awt.Color;

/**
 *
 * @author PetrK
 */
public class MathUtils {
    
    public static final double GEOMETRY_THRESHOLD = 0.0001d;
    
    public static final double RAY_POWER_THRESHOLD = 0.01d;
    
    public static final double LIGHT_POWER = 1d;
    
    

    // Returns the square of a
    public static double sqr(double a) {
        return a * a;
    }

    // Returns the square of a - b
    public static double sqrDiff(double a, double b) {
        return (a - b) * (a - b);
    }
    
    // compares doubles taking into account geometry threshold
    public static boolean equals(double value1, double value2) {
        return (value1 - GEOMETRY_THRESHOLD < value2) && (value1 + GEOMETRY_THRESHOLD > value2);
    }       
    
    // filters max/min values for color
    public static int filterColor(Vector3 color) {
        double r = Math.max(Math.min(color.getX(), 1d), 0);
        double g = Math.max(Math.min(color.getY(), 1d), 0);
        double b = Math.max(Math.min(color.getZ(), 1d), 0);
        return new Color((float)r, (float)g, (float)b).getRGB();
    }
    
    /**
     * @param point - target point
     * @return distance between beginning of this vector and the given point
     */
    public static double distance(Vector3 point1, Vector3 point2) {
        return Math.sqrt(
                MathUtils.sqrDiff(point1.getX(), point2.getX()) +
                MathUtils.sqrDiff(point1.getY(), point2.getY()) + 
                MathUtils.sqrDiff(point1.getZ(), point2.getZ())
        );
    }    
    
    /**
     * @param vec1
     * @param vec2
     * @return true if vectors are collinear
     */
    public static boolean areCollinear(Vector3 vec1, Vector3 vec2) {
        double cos = vec1.normalize().dotProduct(vec2.normalize());
        return equals(cos, 1) || equals(cos, -1);
    }     
    
    // multiplies corresponding components of the vectors
    public static Vector3 merge(Vector3 vec1, Vector3 vec2) {
        return new Vector3(
                vec1.getX() * vec2.getX(),
                vec1.getY() * vec2.getY(),
                vec1.getZ() * vec2.getZ()
        );
    }
    
    /**
     * 
     * @param vector - vector to reflect
     * @param norm - norm to surface
     * @return reflected vector
     */
    public static Vector3 reflect(Vector3 vector, Vector3 norm) {
//        return vector.add(norm.multiply(2 * vector.inverse().dotProduct(norm)));
        double dotProduct = vector.dotProduct(norm);        
        return new Vector3(-vector.getX() + 2 * norm.getX() * dotProduct, -vector.getY() + 2 * norm.getY() * dotProduct, -vector.getZ() + 2 * norm.getZ() * dotProduct);
    }
    
    /**
     * Solves a quadratic equation with coefficients a, b, c. <br /> 
     * Returns (-b) / (2 * a) if only one root exists. <br /> 
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double[] solveQuadraticEquation(double a, double b, double c) {
        double[] roots = null;
        if (a == 0) {
            roots = new double[1];
            roots[0] = -c / b;
        } else {
            double discriminant = MathUtils.sqr(b) - 4 * a * c;

            if (discriminant == 0) {
                roots = new double[1];
                roots[0] = (-b) / (2 * a);
            } else {
                roots = new double[2];
                discriminant = Math.sqrt(discriminant);
                double denominator = 2 * a;
                roots[0] = (-b + discriminant) / (denominator);
                roots[1] = (-b - discriminant) / (denominator);
            }
        }
        return roots;
    }    
	
}
