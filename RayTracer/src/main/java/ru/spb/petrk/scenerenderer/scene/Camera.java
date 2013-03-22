package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public interface Camera {
    
    Vector3 getPosition();
    
    Vector3 getForwardDirection();
    
    Vector3 getUpDirection();

    double getScreenDistance();
    
    double getScreenWidthAngle();
    
    double getScreenHeightAngle();
    
    Snapshot makeSnapshot(Scene scene, int xResolution, int yResolution);

}
