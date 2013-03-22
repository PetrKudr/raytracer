package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.Scene;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface RayTracer {
    
    /**
     * Calculates color of point in which given ray comes
     * @param scene
     * @param ray
     * @return color of point
     */
    Vector3 color(Scene scene, Ray ray);

}
