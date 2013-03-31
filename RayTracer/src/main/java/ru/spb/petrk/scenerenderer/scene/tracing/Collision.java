package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.scene.Primitive;

/**
 *
 * @author PetrK
 */

public final class Collision {

    private double distance;
    
    private Primitive primitive;
    
    private boolean inversed;  // used for csg objects to indicate that norm must be inversed

    
    public Collision(double distance, Primitive primitive) {
        this(distance, primitive, false);
    }
    
    public Collision(double distance, Primitive primitive, boolean inversed) {
        this.distance = distance;
        this.primitive = primitive;
        this.inversed = inversed;
    }

    public double getDistance() {
        return distance;
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public boolean isInversed() {
        return inversed;
    }
    
    public void setInversed(boolean inversed) {
        this.inversed = inversed;
    }
}
