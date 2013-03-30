package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface Primitive {
       
    /**
     * Material at given point
     * @param point - point of the object
     * @return material of the object
     */
    Material getMaterial(Vector3 point);
    
    /**
     * Returns norm of surface at given point
     * @param point - point at surface
     * @return norm
     */
    Vector3 getNorm(Vector3 point);
    
    /**
     * Intersects ray with object.
     * @param ray - ray
     * @return array of distances from ray start point to intersection points sorted in ascending order. 
     */
    double[] intersect(Ray ray);
    
}