package ru.spb.petrk.scenerenderer.scene.tracing.refraction;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.Scene;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.scene.tracing.RayImpl;
import ru.spb.petrk.scenerenderer.scene.tracing.RayTracer;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
abstract class AbstractRefractionStrategy implements RefractionStrategy {

    @Override
    public Vector3 refract(RayTracer tracer, Scene scene, Ray ray, Vector3 point, Vector3 norm, Primitive primitive) {
        Material material = primitive.getMaterial(point);
        
        double refractionPower = calcRefractionPower(scene, ray, material, point, norm);
        
        // Refraction
        if (refractionPower * ray.getPower() >= MathUtils.RAY_POWER_THRESHOLD) {
            Vector3 preparedNorm = norm;
            double refractionRatio = scene.getRefractiveIndex() / material.getRefractiveIndex();

            double cos = preparedNorm.dotProduct(ray.getDirection());

            if (cos > 0) {
                // Ray is inside object
                preparedNorm = preparedNorm.inverse();
                refractionRatio = 1 / refractionRatio;
                cos = -cos;
            }

            double refractionSinSquare = 1 - refractionRatio * refractionRatio * (1 - cos * cos);
            double b = cos * refractionRatio + Math.sqrt(refractionSinSquare);

            if (refractionSinSquare >= MathUtils.GEOMETRY_THRESHOLD) {
                Vector3 refractionDirection = ray.getDirection().multiply(refractionRatio).subtract(preparedNorm.multiply(b)).normalize();
                Ray refractionRay = new RayImpl(point, refractionDirection, ray.getPower() * refractionPower);
                Vector3 refractionColor = tracer.color(scene, refractionRay);
                return refractionColor;
            }
        }
        
        return Vector3.ZERO_VECTOR;
    }
    
    protected abstract double calcRefractionPower(Scene scene, Ray ray, Material material, Vector3 point, Vector3 norm);

}
