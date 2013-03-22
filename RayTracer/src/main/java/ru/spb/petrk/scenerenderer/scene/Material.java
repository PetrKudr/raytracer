package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface Material {
    
    Vector3 getDiffuse();
    
    Vector3 getAmbient();
    
    Vector3 getSpecular();
    
    double getSpecularPower();
    
    double getReflectionPower();
    
    double getRefractiveIndex();
    
    double getTransparency();

}
