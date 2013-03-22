package ru.spb.petrk.scenerenderer.scene.tracing;

import ru.spb.petrk.scenerenderer.scene.Primitive;

/**
 *
 * @author PetrK
 */

public final class Collision {

    private double distance;
    
    private Primitive primitive;

    
    public Collision(double distance, Primitive primitive) {
        this.distance = distance;
        this.primitive = primitive;
    }

    public double getDistance() {
        return distance;
    }

    public Primitive getPrimitive() {
        return primitive;
    }
}
