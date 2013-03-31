package ru.spb.petrk.scenerenderer.scene.objects.csg;

import ru.spb.petrk.scenerenderer.scene.SceneObject;

/**
 *
 * @author PetrK
 */
public class CSGObjectNode extends AbstractCSGTreeNode<SceneObject> {

    public CSGObjectNode(SceneObject data) {
        super(null, null, data);
    }
    
}
