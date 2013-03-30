package ru.spb.petrk.scenerenderer.scene.objects;

import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGPrimitiveNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGOperationNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGTreeNode;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;

/**
 *
 * @author PetrK
 */
public class CSGSceneObject implements SceneObject {
    
    private final CSGTreeNode root;

    
    public CSGSceneObject(CSGTreeNode root) {
        this.root = root;
    }
    
    @Override
    public boolean checkIntersectionPossibility(Ray ray) {
        return true;
    }

    @Override
    public Collision trace(Ray ray) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    private CSGCollision walk(CSGTreeNode node) {
        if (node instanceof CSGOperationNode) {
            
        } else if (node instanceof CSGPrimitiveNode) {
            
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
            
    
    private static class CSGCollision {
        
        private Collision collision;
        
        private boolean isOut;

        
        public CSGCollision(Collision collision, boolean isOut) {
            this.collision = collision;
            this.isOut = isOut;
        }

        public Collision getCollision() {
            return collision;
        }

        public boolean isIsOut() {
            return isOut;
        }
    }

}
