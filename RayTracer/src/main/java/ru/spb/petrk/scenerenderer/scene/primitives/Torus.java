package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Solvers;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Torus extends AbstractPrimitive {
    
    // http://users.wowway.com/~phkahler/torus.pdf    

    private final Vector3 center;
    
    private final Vector3 norm;
    
    private final double R;
    
    private final double r;
    
    
    public Torus(Material material, Vector3 center, Vector3 norm, double r, double R) {
        super(material);
        this.center = center;
        this.norm = norm.normalize();
        this.r = r;
        this.R = R;
    }    

    @Override
    public Vector3 getNorm(Vector3 point) {
        double y = point.subtract(center).dotProduct(norm);
        Vector3 D = point.subtract(center).subtract(norm.multiply(y));
        Vector3 X = D.multiply(1d / Math.sqrt(D.dotProduct(D)) * R);
        return point.subtract(center).subtract(X).normalize();
    }

    @Override
    public double intersect(Ray ray) {
        Vector3 Q = ray.getFrom().subtract(center);        
        double u = norm.dotProduct(Q);
        double v = norm.dotProduct(ray.getDirection());
        
        double a = 1 - v*v;
        double b = 2 * (Q.dotProduct(ray.getDirection()) - u * v);
        double c = Q.dotProduct(Q) - u*u;
        
        double alpha = Q.dotProduct(Q) + R*R - r*r;
        
        double A = 1;
        double B = 4 * Q.dotProduct(ray.getDirection());
        double C = 2*alpha + B*B/4 - 4*R*R*a;
        double D = B*alpha - 4*R*R*b;
        double E = alpha * alpha - 4*R*R*c;
        
        double[] roots = Solvers.solveQuartic(A, B, C, D, E);
        
        if (roots == null || roots.length == 0) {
            return -1;
        }

        // Find the closest intersecting point        
        for (int i = 0; i < roots.length; i++) {
            if (roots[i] > MathUtils.GEOMETRY_THRESHOLD) {
                return roots[i];
            }
        }

        return -1;                  
    }

}
