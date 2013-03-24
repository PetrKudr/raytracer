package ru.spb.petrk.scenerenderer.scene;

import java.util.Collection;

/**
 *
 * @author PetrK
 */
public interface Scene {
    
    double getRefractiveIndex();
    
    double getAttenuationCoefficient();
    
    Collection<SceneObject> getSceneObjects();
    
    Collection<Light> getLights();

}
