package ru.spb.petrk.scenerenderer.scene.objects.csg;

/**
 *
 * @author PetrK
 */
public interface CSGTreeNode<T> {
    
    CSGTreeNode getLeft();
    
    CSGTreeNode getRight();
    
    boolean isLeaf();
    
    T getData();

}
