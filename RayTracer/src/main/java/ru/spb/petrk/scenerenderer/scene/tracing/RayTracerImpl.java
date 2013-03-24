package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.scene.Light;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.Scene;
import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.tracing.refraction.RefractionStrategy;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class RayTracerImpl implements RayTracer {
    
    private final Vector3 defaultColor;
    
    private RefractionStrategy refractionStrategy;
    
    
    public RayTracerImpl(Vector3 defaultColor, RefractionStrategy refractionStrategy) {
        this.defaultColor = defaultColor;
        this.refractionStrategy = refractionStrategy;
    }

    @Override
    public Vector3 color(Scene scene, Ray ray) {
        Vector3 color = defaultColor;
        
        Collision collision = trace(scene, ray);
        
        if (collision != null) {  
            Vector3 collisionPoint = ray.getFrom().add(ray.getDirection().multiply(collision.getDistance()));
            Vector3 collisionNorm = collision.getPrimitive().getNorm(collisionPoint);
            
            Material material = collision.getPrimitive().getMaterial(collisionPoint);
            
            // Ambient
            color = material.getAmbient();            
            
            // For all lights we need to calculate diffuse and specular components
            for (Light light : scene.getLights()) {
                double amountOfLight = findAmountOfLight(scene, light, collisionPoint);
                
                if (amountOfLight >= MathUtils.RAY_POWER_THRESHOLD) {
                    Vector3 lightDirection = collisionPoint.subtract(light.getPosition()).normalize();
                    
                    // Diffuse
                    double cos = collisionNorm.dotProduct(lightDirection.inverse());
                    Vector3 diffuseColor = material.getDiffuse().multiply(amountOfLight * cos);
                    color = color.add(diffuseColor);
                    
                    // Specular                    
                    Vector3 lightReflectionDirection = MathUtils.reflect(lightDirection.inverse(), collisionNorm);
                    cos = lightReflectionDirection.dotProduct(ray.getDirection().inverse());
                    Vector3 specularColor = material.getSpecular().multiply(amountOfLight * Math.pow(cos, material.getSpecularPower()));
                    color = color.add(specularColor);
                }
            }
            
            // Reflection
            if (material.getReflectionPower() * ray.getPower() >= MathUtils.RAY_POWER_THRESHOLD) {
                Vector3 reflectionDirection = MathUtils.reflect(ray.getDirection().inverse(), collisionNorm);                        
                Ray reflectionRay = new RayImpl(collisionPoint, reflectionDirection, ray.getPower() * material.getReflectionPower());
                Vector3 reflectionColor = color(scene, reflectionRay);
                color = color.add(reflectionColor);
            }          
            
            // Refraction
            color = color.add(refractionStrategy.refract(this, scene, ray, collisionPoint, collisionNorm, collision.getPrimitive()));            
        }
        
        return color.multiply(ray.getPower());
    }

    
    /**
     * Traces ray and returns collision or null
     * @param scene - scene
     * @param ray - ray
     * @return collision or null if ray doesn't intersect with any object on scene
     */
    private Collision trace(Scene scene, Ray ray) {
        Collision nearestCollision = null;
        
        for (SceneObject sceneObject : scene.getSceneObjects()) {
            if (sceneObject.checkIntersectionPossibility(ray)) {
                Collision collision = sceneObject.trace(ray);

                if (collision != null && collision.getDistance() >= MathUtils.GEOMETRY_THRESHOLD) {
                    if (nearestCollision == null || nearestCollision.getDistance() > collision.getDistance()) {
                        nearestCollision = collision;
                    }
                }
            }
        }
        
        return nearestCollision;
    }
    
    /**
     * Traces light ray from the given light source to the given point
     * @param scene
     * @param light
     * @param point
     * @return power of light
     */
    private double findAmountOfLight(Scene scene, Light light, Vector3 point) {
        Vector3 lightToCollision = point.subtract(light.getPosition());
        
        Ray lightRay = new RayImpl(light.getPosition(), lightToCollision);   
        
        Collision lightCollision = trace(scene, lightRay);        
        
        if (lightCollision != null && MathUtils.equals(lightCollision.getDistance(), lightToCollision.getLength())) { 
            return MathUtils.LIGHT_POWER;
        }
        
        return 0;
    }        
    
}
