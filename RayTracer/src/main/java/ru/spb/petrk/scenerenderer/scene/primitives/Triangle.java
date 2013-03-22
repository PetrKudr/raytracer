package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Triangle extends AbstractPrimitive {
    
    private final Vector3 v0, v1, v2;
    
    private final Vector3 n0, n1, n2;
    
    // for optimizations 
    
    private final Vector3 e1, e2;
    
    private final Vector3 crossE1E2;
    
    private final Vector3 norm;
    
    private final double denominator;

    
    public Triangle(Material material, Vector3 p0, Vector3 p1, Vector3 p2) {
        this(
                material, 
                p0, 
                p1,
                p2,
                p1.subtract(p0).crossProduct(p2.subtract(p0)).normalize(), 
                p1.subtract(p0).crossProduct(p2.subtract(p0)).normalize(), 
                p1.subtract(p0).crossProduct(p2.subtract(p0)).normalize()
       );
    }
    
    public Triangle(Material material, Vector3 p0, Vector3 p1, Vector3 p2, Vector3 n0, Vector3 n1, Vector3 n2) {
        super(material);
        this.v0 = p0;
        this.v1 = p1;
        this.v2 = p2;
        this.n0 = n0;
        this.n1 = n1;
        this.n2 = n2;
        
        this.e1 = v1.subtract(v0);
        this.e2 = v2.subtract(v0);
        this.crossE1E2 = e1.crossProduct(e2);
        this.norm = crossE1E2.normalize();
        this.denominator = 1d / crossE1E2.getLength();
    }    

    public Vector3 getV0() {
        return v0;
    }

    public Vector3 getV1() {
        return v1;
    }

    public Vector3 getV2() {
        return v2;
    }

    @Override
    public Vector3 getNorm(Vector3 dot) {
        double u = dot.subtract(v0).crossProduct(e2).getLength() * denominator;
        double v = e1.crossProduct(dot.subtract(v0)).getLength() * denominator;
        double w = 1d - u - v;

        Vector3 n = new Vector3(
            n1.getX() * u + n2.getX() * v + n0.getX() * w,
            n1.getY() * u + n2.getY() * v + n0.getY() * w,
            n1.getZ() * u + n2.getZ() * v + n0.getZ() * w
        );

        return n;
    }

    @Override
    public double intersect(Ray ray) {
        // Algorithm: http://www.ray-tracing.ru/articles213.html
        
        Vector3 T = ray.getFrom().subtract(v0);
        Vector3 Q = T.crossProduct(e1);
        Vector3 P = ray.getDirection().crossProduct(e2);
        
        double pDotE1 = P.dotProduct(e1);
        
        if (pDotE1 >= MathUtils.GEOMETRY_THRESHOLD || pDotE1 <= -MathUtils.GEOMETRY_THRESHOLD) {
            double u = P.dotProduct(T) / pDotE1;
            double v = Q.dotProduct(ray.getDirection()) / pDotE1;
            
            if (u >= 0 && v >= 0 && u + v <= 1) {
                double distance = Q.dotProduct(e2) / pDotE1;
                return distance;
            }
        }
        
        return -1;
    }
 
}
