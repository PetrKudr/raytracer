package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class RayImpl implements Ray {
    
    private Vector3 from;
    
    private Vector3 direction;
    
    private double power;

    
    public RayImpl(Vector3 from, Vector3 direction) {
        this(from, direction, 1);
    }
    
    public RayImpl(Vector3 from, Vector3 direction, double power) {
        this.from = from;
        this.direction = direction.normalize();
        this.power = power;
    }    

    @Override
    public Vector3 getFrom() {
        return from;
    }
    
    @Override
    public Vector3 getDirection()  {
        return direction;
    }

    @Override
    public double getPower() {
        return power;
    }
    
}
