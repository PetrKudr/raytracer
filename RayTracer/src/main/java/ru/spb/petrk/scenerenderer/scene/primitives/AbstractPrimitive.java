package ru.spb.petrk.scenerenderer.scene.primitives;

import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
abstract class AbstractPrimitive implements Primitive {
    
    protected static final double EMPTY[] = new double[0];
    

    protected Material material;

    
    public AbstractPrimitive(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial(Vector3 point) {
        return material;
    }

}
