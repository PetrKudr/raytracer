package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface Ray {
    
    Vector3 getFrom();
    
    Vector3 getDirection();
    
    double getPower();

}
