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
   
    public static final Matrix3 xRotatePi2 = rotateX(Math.PI / 2);
    
    public static final Matrix3 yRotatePi2 = rotateY(Math.PI / 2);
    
    public static final Matrix3 zRotatePi2 = rotateZ(Math.PI / 2);
    
    /*
    ********************************************************
    *   Implementation
    ********************************************************
    */
    
    private final static int DIMENSION = 3;
    
    private static final int X = 0;
    
    private static final int Y = 1;
    
    private static final int Z = 2;
    

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
                elements[X][0] * vector.getX() + elements[Y][0] * vector.getY() + elements[Z][0] * vector.getZ(),
                elements[X][1] * vector.getX() + elements[Y][1] * vector.getY() + elements[Z][1] * vector.getZ(),
                elements[X][2] * vector.getX() + elements[Y][2] * vector.getY() + elements[Z][2] * vector.getZ()
        );
    }

    
    /*
    ********************************************************
    *   Helpers
    ********************************************************
    */    
    
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
    
}
