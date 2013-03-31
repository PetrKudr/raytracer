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
public class SimpleSceneObject extends AbstractSceneObject {

    private final Primitive primitive;

    public SimpleSceneObject(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean checkIntersectionPossibility(Ray ray) {
        return true;
    }

    @Override
    public Collision[] trace(Ray ray) {
        double distances[] = primitive.intersect(ray);
        
        if (distances.length > 0 && distances[0] >= MathUtils.GEOMETRY_THRESHOLD) {
            Collision collisions[] = new Collision[distances.length];
            
            int i = 0;
            
            for (double distance : distances) {
                collisions[i++] = new Collision(distance, primitive);
            }
            
            return collisions;
        }
        
        return EMPTY;
    }
    
}
