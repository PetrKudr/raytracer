package ru.spb.petrk.scenerenderer.scene.objects.csg;

/**
 *
 * @author PetrK
 */
public class CSGOperationNode extends AbstractCSGTreeNode<CSGOperation> {

    public CSGOperationNode(CSGTreeNode left, CSGTreeNode right, CSGOperation data) {
        super(left, right, data);
    }
    
}
