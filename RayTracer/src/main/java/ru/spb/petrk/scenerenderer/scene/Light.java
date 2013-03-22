package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface Light {
    
    Vector3 getPosition();
    
    // not in use
    Vector3 getDiffuse();
    
    // not in use
    Vector3 getAmbient();
    
    // not in use
    Vector3 getSpecular();

}
