package ru.spb.petrk.scenerenderer.scene;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author PetrK
 */
public class SceneImpl implements Scene {
    
    private final double refractiveIndex;
    
    private final double attenuatonCoefficient;
    
    private final List<SceneObject> sceneObjects;
    
    private final List<Light> lights;
    
    
    public SceneImpl(double refractiveIndex, double attenuatonCoefficient, List<SceneObject> primitives, List<Light> lights) {
        this.refractiveIndex = refractiveIndex;
        this.attenuatonCoefficient = attenuatonCoefficient;
        this.sceneObjects = primitives;
        this.lights = lights;
    }

    @Override
    public double getRefractiveIndex() {
        return refractiveIndex;
    }

    @Override
    public Collection<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    @Override
    public Collection<Light> getLights() {
        return lights;
    }

    @Override
    public double getAttenuationCoefficient() {
        return attenuatonCoefficient;
    }

}
