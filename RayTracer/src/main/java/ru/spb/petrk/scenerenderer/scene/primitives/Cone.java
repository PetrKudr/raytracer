package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Cone extends AbstractPrimitive {
    
    private final Vector3 top;
    
    private final Vector3 direction;
    
    private final double angle;
    
    private final double height;
    

    public Cone(Material material, Vector3 top, Vector3 direction, double angle, double height) {
        super(material);
        this.top = top;
        this.direction = direction.normalize();
        this.angle = angle;
        this.height = height;
    }

    @Override
    public Vector3 getNorm(Vector3 point) {
        Vector3 topToPointDirection = point.subtract(top).normalize();
        return direction.crossProduct(topToPointDirection).crossProduct(topToPointDirection).normalize();
    }

    @Override
    public double[] intersect(Ray ray) {
        Vector3 rayToTop = ray.getFrom().subtract(top);
        
        double AxR = direction.dotProduct(ray.getDirection());
        double AxBR = direction.dotProduct(rayToTop);
        double cosSquare = Math.pow(Math.cos(angle), 2);
        
        double paramA = AxR * AxR - cosSquare * ray.getDirection().dotProduct(ray.getDirection());
        double paramB = 2 * (AxR * AxBR - cosSquare * ray.getDirection().dotProduct(rayToTop));
        double paramC = AxBR * AxBR - cosSquare * rayToTop.dotProduct(rayToTop);
        
        double roots[] = MathUtils.solveQuadraticEquation(paramA, paramB, paramC);
        
        if (roots != null) {            
            if (roots.length > 1) {
                double minRoot = Math.min(roots[0], roots[1]);
                double maxRoot = Math.max(roots[0], roots[1]);
                
                if (checkRoot(ray, minRoot)) {
                    return new double[] {minRoot, maxRoot};
                } else if (checkRoot(ray, maxRoot)) {
                    return new double[] {maxRoot};
                }                
            } else {
                if (checkRoot(ray, roots[0])) {
                    return roots;
                }                
            }
        }
        
        return EMPTY;
    }
    
    private boolean checkRoot(Ray ray, double root) {
        if (root >= MathUtils.GEOMETRY_THRESHOLD) {
            Vector3 point = ray.getFrom().add(ray.getDirection().multiply(root));
            return isPointOnCone(point);
        }
        return false;
    }
    
    private boolean isPointOnCone(Vector3 point) {
        // Note that we suppose that point is at least on infinite cone
        boolean bottomCheck = point.subtract(top).getLength() * Math.cos(angle) <= height;
        boolean topCheck = point.subtract(top).dotProduct(direction) >= 0;
        return bottomCheck && topCheck;
    }
}
