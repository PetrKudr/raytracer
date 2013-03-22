package ru.spb.petrk.scenerenderer.scene.tracing.refraction;

import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.Scene;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.scene.tracing.RayTracer;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface RefractionStrategy {

    /**
     * Calculates refraction part for the given point
     * @param tracer
     * @param scene
     * @param ray
     * @param point - collision point
     * @param norm - collision norm
     * @param primitive - primitive colliding with ray
     * @return color
     */
    Vector3 refract(RayTracer tracer, Scene scene, Ray ray, Vector3 point, Vector3 norm, Primitive primitive);
    
}
