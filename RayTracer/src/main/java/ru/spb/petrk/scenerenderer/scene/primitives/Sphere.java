package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Sphere extends AbstractPrimitive {

    private Vector3 center;
    
    private double radius;


    public Sphere(Material material, Vector3 center, double radius) {
        super(material);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector3 getNorm(Vector3 point) {
        Vector3 distance = point.subtract(center);
        return distance.normalize();
    }

    @Override
    public double[] intersect(Ray ray) {
        Vector3 distance = ray.getFrom().subtract(center);
        
        double b = distance.dotProduct(ray.getDirection()); 
        double c = distance.dotProduct(distance) - radius * radius;
        double d = b * b - c;

        if (d >= 0) {
          double sqrtD = Math.sqrt(d);
          
          double t1 = -b + sqrtD;
          double t2 = -b - sqrtD;

          double min_t = Math.min(t1, t2);
          double max_t = Math.max(t1, t2);

          double t = (min_t >= MathUtils.GEOMETRY_THRESHOLD) ? min_t : max_t;

          return min_t >= MathUtils.GEOMETRY_THRESHOLD ? new double[] {min_t, max_t} : new double[] {max_t};
        }
        
        return EMPTY;
    }
    
    public Vector3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

}
