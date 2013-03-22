package ru.spb.petrk.scenerenderer.scene.objects;

import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;

/**
 *
 * @author PetrK
 */
public class SimpleSceneObject implements SceneObject {

    private final Primitive primitive;

    public SimpleSceneObject(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean checkIntersectionPossibility(Ray ray) {
        return true;
    }

    @Override
    public Collision trace(Ray ray) {
        double distance = primitive.intersect(ray);

        if (distance >= MathUtils.GEOMETRY_THRESHOLD) {
            return new Collision(distance, primitive);
        }
        
        return null;
    }
    
}
