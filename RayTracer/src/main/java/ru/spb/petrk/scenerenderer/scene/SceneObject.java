package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.scene.tracing.Collision;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;

/**
 *
 * @author PetrK
 */
public interface SceneObject {
    
    /**
     * Checks if intersection could happen
     * @param ray
     * @return true if possible
     */
    boolean checkIntersectionPossibility(Ray ray);

    /**
     * Traces ray and returns collision
     * @param ray
     * @return collision or null
     */
    Collision trace(Ray ray);
    
}
