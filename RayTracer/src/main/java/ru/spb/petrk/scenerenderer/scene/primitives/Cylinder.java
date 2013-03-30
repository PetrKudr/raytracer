package ru.spb.petrk.scenerenderer.scene.primitives;

import java.util.Arrays;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Cylinder extends AbstractPrimitive {
    
    private Vector3 bottom;
    
    private Vector3 top;
    
    private double radius;
    
    // fields for optimization
    
    private Vector3 direction;
    
    private Vector3 bottomToTop;
    

    
    public Cylinder(Material material, Vector3 bottom, Vector3 top, double radius) {
        super(material);
        this.bottom = bottom;
        this.top = top;
        this.radius = radius;
        
        this.bottomToTop = top.subtract(bottom);
        this.direction = bottomToTop.normalize();
    }

    @Override
    public Vector3 getNorm(Vector3 point) {
	// Formulas according to http://answers.yahoo.com/question/index?qid=20080218071458AAYz1s1
        
        // Calculate the projection of the intersection point onto the direction vector of the cylinder
        Vector3 pointToBottom = point.subtract(bottom);
        double t = bottomToTop.dotProduct(pointToBottom) / bottomToTop.dotProduct(bottomToTop);        
        
        Vector3 center = bottom.add(bottomToTop.multiply(t));

        // Calculate the vector from the intersection point to its projection onto the direction of the cylinder.
        return point.subtract(center).normalize();
    }

    @Override
    public double[] intersect(Ray ray) {
        double a, b, c;		// Quadratic equation coefficients

        Vector3 AO = ray.getFrom().subtract(bottom);
        Vector3 AOxAB = AO.crossProduct(direction);
        Vector3 VxAB = ray.getDirection().crossProduct(direction);


        a = VxAB.dotProduct(VxAB);
        b = 2 * VxAB.dotProduct(AOxAB);
        c = AOxAB.dotProduct(AOxAB) - radius * radius;

        // Solve equation for at^2 + bt + c = 0
        double[] roots = MathUtils.solveQuadraticEquation(a, b, c);
        double distance = -1;
        
        if (roots.length == 1) {
            if (roots[0] >= 0) {
                return roots;
            }
        } else {
            if (roots[0] >= MathUtils.GEOMETRY_THRESHOLD && roots[1] >= MathUtils.GEOMETRY_THRESHOLD) {
                Vector3 point1 = ray.getFrom().add(ray.getDirection().multiply(roots[0]));
                Vector3 point2 = ray.getFrom().add(ray.getDirection().multiply(roots[1]));

                if (isPointOnCylinder(point1)) {
                    if (isPointOnCylinder(point2)) {
                        Arrays.sort(roots);
                        return roots;
                    } else {
                        return new double[] {roots[0]};
                    }
                } else if (isPointOnCylinder(point2)) {
                    return new double[] {roots[1]};
                }
            } else if (roots[0] >= 0 || roots[1] >= 0) {
                return new double[] {Math.max(roots[0], roots[1])};
            }
        }        

        return EMPTY;
    }
    
    
    private boolean isPointOnCylinder(Vector3 point) {
        // Formulas according to http://answers.yahoo.com/question/index?qid=20080218071458AAYz1s1
        
        // Calculate the projection of the intersection point onto the direction vector of the cylinder
        Vector3 pointToBottom = point.subtract(bottom);
        double t = pointToBottom.dotProduct(direction);

        if (t > bottomToTop.getLength() || t < 0) {
            return false;
        }

        return true;
    }    
    
}
