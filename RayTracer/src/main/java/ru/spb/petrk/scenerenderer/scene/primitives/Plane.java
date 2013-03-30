package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class Plane extends AbstractPrimitive {
    
    private Material material;
    
    private Vector3 point;
    
    private Vector3 norm;

    
    public Plane(Material material, Vector3 point, Vector3 norm) {
        super(material);
        this.point = point;
        this.norm = norm.normalize();
    }   

    @Override
    public Vector3 getNorm(Vector3 point) {
        return norm;
    }

    @Override
    public double[] intersect(Ray ray) {
        double projectionOfDirectionOnNorm = ray.getDirection().dotProduct(norm);
        
        if (projectionOfDirectionOnNorm < 0) {
            double distance = - (norm.dotProduct(ray.getFrom().subtract(point))) / projectionOfDirectionOnNorm;
            return new double[] {distance};
        }
        
        return EMPTY;
    }
    
}
