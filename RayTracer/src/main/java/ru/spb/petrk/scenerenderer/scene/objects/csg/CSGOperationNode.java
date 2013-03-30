package ru.spb.petrk.scenerenderer.scene.objects.csg;

/**
 *
 * @author PetrK
 */
public class CSGOperationNode extends AbstractCSGTreeNode<CSGOperation> {

    public CSGOperationNode(CSGTreeNode parent, CSGTreeNode left, CSGTreeNode right, CSGOperation data) {
        super(parent, left, right, data);
    }
    
}
