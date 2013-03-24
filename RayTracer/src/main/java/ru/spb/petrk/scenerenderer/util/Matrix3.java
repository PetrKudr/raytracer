package ru.spb.petrk.scenerenderer.util;

/**
 *
 * @author PetrK
 */
public class Matrix3 {
    
    /*
    ********************************************************
    *   Rotate matrixes
    ********************************************************
    */
    
    public static final Matrix3 identity = new Matrix3(new double[][] {
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    });
   
    public static final Matrix3 xRotatePi2 = rotateX(Math.PI / 2);
    
    public static final Matrix3 yRotatePi2 = rotateY(Math.PI / 2);
    
    public static final Matrix3 zRotatePi2 = rotateZ(Math.PI / 2);
    
    /*
    ********************************************************
    *   Implementation
    ********************************************************
    */
    
    private final static int DIMENSION = 3;
    
    
    private final double elements[][];
    
    
    public Matrix3(double[][] elements) {
        if (elements.length != DIMENSION) {
            throw new IllegalArgumentException("Matrix width must be " + DIMENSION);
        }
        
        for (int i = 0; i < 3; i++) {
            if (elements[i].length != DIMENSION) {
                throw new IllegalArgumentException("Matrix height must be " + DIMENSION);
            }
        }
        
        this.elements = elements;
    }
    
    public Vector3 multiply(Vector3 vector) {
        return new Vector3(
                elements[0][0] * vector.getX() + elements[0][1] * vector.getY() + elements[0][2] * vector.getZ(),
                elements[1][0] * vector.getX() + elements[1][1] * vector.getY() + elements[1][2] * vector.getZ(),
                elements[2][0] * vector.getX() + elements[2][1] * vector.getY() + elements[2][2] * vector.getZ()
        );
    }
    
    public double determinant() {
        return elements[0][0] * elements[1][1] * elements[2][2] 
                + elements[0][1] * elements[1][2] * elements[2][0] 
                + elements[0][2] * elements[2][1] * elements[1][0] 
                - elements[0][2] * elements[1][1] * elements[2][0] 
                - elements[0][0] * elements[2][1] * elements[1][2]
                - elements[2][2] * elements[1][0] * elements[0][1];
    }
      
    /*
    ********************************************************
    *   Helpers
    ********************************************************
    */    
    
    public static Matrix3 rotate(Vector3 axis, double angle) {
        if (MathUtils.equals(angle, 0)) {
            return identity;
        }
        
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);        
        
        Vector3 axisDirection = axis.normalize();
        
        double x = axisDirection.getX(); 
        double y = axisDirection.getY(); 
        double z = axisDirection.getZ();         
        
        return new Matrix3(new double[][] {
            {x * x * (1 - cos) + cos, x * y * (1 - cos) + z * sin, x * z * (1 - cos) - y * sin},
            {x * y * (1 - cos) - z * sin, y * y * (1 - cos) + cos, y * z * (1 - cos) + x * sin},
            {x * z * (1 - cos) + y * sin, y * z * (1 - cos) - x * sin, z * z * (1 - cos) + cos}
        });
    }    
    
    public static Matrix3 rotate(Vector3 from, Vector3 to) {
        if (MathUtils.areCollinear(from, to)) {
            return identity;
        }
        
        Vector3 axis = from.crossProduct(to).normalize();
        if (!isRightHanded(from, to, axis)) {
            axis = to.crossProduct(from);
        }
        
        double angle = Math.acos(from.dotProduct(to));
        
        return rotate(axis, angle);
    }       
    
    public static Matrix3 rotateX(double angle) {
        return new Matrix3(new double[][] {
            {1, 0              , 0                 }, 
            {0, Math.cos(angle), - Math.sin(angle) },
            {0, Math.sin(angle), Math.cos(angle)   }
        });
    }
    
    public static Matrix3 rotateY(double angle) {
        return new Matrix3(new double[][] {
            {Math.cos(angle)  , 0, Math.sin(angle) }, 
            {0                , 1, 0               },
            {- Math.sin(angle), 0, Math.cos(angle) }
        });
    }
    
    public static Matrix3 rotateZ(double angle) {
        return new Matrix3(new double[][] {
            {Math.cos(angle), - Math.sin(angle), 0},
            {Math.sin(angle), Math.cos(angle)  , 0},
            {0              , 0                , 1}
        });
    }
    
    public static boolean isRightHanded(Vector3 vec1, Vector3 vec2, Vector3 vec3) {
        Matrix3 matrix = new Matrix3(new double[][] {
            {vec1.getX(), vec1.getY(), vec1.getZ()},
            {vec2.getX(), vec2.getY(), vec2.getZ()},
            {vec3.getX(), vec3.getY(), vec3.getZ()}
        });
        return matrix.determinant() > 0;
    }
    
}
