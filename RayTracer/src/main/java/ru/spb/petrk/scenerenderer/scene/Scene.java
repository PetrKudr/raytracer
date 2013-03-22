package ru.spb.petrk.scenerenderer.scene;

import java.util.Collection;

/**
 *
 * @author PetrK
 */
public interface Scene {
    
    double getRefractiveIndex();
    
    Collection<SceneObject> getSceneObjects();
    
    Collection<Light> getLights();

}
