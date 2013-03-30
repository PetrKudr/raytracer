package ru.spb.petrk.scenerenderer.scene.objects.csg;

import ru.spb.petrk.scenerenderer.scene.Primitive;

/**
 *
 * @author PetrK
 */
public class CSGPrimitiveNode extends AbstractCSGTreeNode<Primitive> {

    public CSGPrimitiveNode(CSGTreeNode parent, Primitive data) {
        super(parent, null, null, data);
    }
    
}
