package ru.spb.petrk.scenerenderer.scene.objects.csg;

/**
 *
 * @author PetrK
 */
public abstract class AbstractCSGTreeNode<T> implements CSGTreeNode<T> {

    private final CSGTreeNode left;
    
    private final CSGTreeNode right;
    
    private final T data;
    

    public AbstractCSGTreeNode(CSGTreeNode left, CSGTreeNode right, T data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    @Override
    public CSGTreeNode getLeft() {
        return left;
    }

    @Override
    public CSGTreeNode getRight() {
        return right;
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public T getData() {
        return data;
    }
    
}
