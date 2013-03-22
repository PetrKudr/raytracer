package ru.spb.petrk.scenerenderer.scene.tracing.refraction;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.Scene;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class FresnelRefractionStrategy extends AbstractRefractionStrategy {
    
    @Override
    protected double calcRefractionPower(Scene scene, Ray ray, Material material, Vector3 point, Vector3 norm) {
        if (material.getTransparency() >= 0) {
            double cos = Math.abs(ray.getDirection().dotProduct(norm));
            double fresnel = 1 / Math.pow(1 + cos, material.getTransparency());
            return fresnel;
        }
        return 0;
    }

}
