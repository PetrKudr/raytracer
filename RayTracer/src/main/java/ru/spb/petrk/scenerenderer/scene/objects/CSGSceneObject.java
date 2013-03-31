package ru.spb.petrk.scenerenderer.scene.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGDiagram;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGDiagramImpl;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGObjectNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGOperationNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGTreeNode;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class CSGSceneObject extends AbstractSceneObject {
    
    private static final CSGDiagram EMPTY_DIAGRAM = new CSGDiagramImpl(Collections.<CSGDiagram.Point>emptyList());
    
    
    private final CSGTreeNode root;

    
    public CSGSceneObject(CSGTreeNode root) {
        this.root = root;
    }
    
    @Override
    public boolean checkIntersectionPossibility(Ray ray) {
        return true;
    }

    @Override
    public Collision[] trace(Ray ray) {
        CSGDiagram diagram = walk(ray, root);
        
        if (!diagram.getCollisionPoints().isEmpty()) {
            Collision collisions[] = new Collision[diagram.getCollisionPoints().size()];
            
            int i = 0;
            
            for (CSGDiagram.Point point : diagram.getCollisionPoints()) {
                Collision collision = point.getCollision();
                
//                if (point.isOut()) {
//                    collision.setInversed(true);
//                }
                
                collisions[i++] = collision;
            }
            
            return collisions;
        }
        
        return EMPTY;
    }
    
    
    private CSGDiagram walk(Ray ray, CSGTreeNode node) {
        if (node instanceof CSGOperationNode) {
            CSGDiagram leftDiagram = walk(ray, node.getLeft());
            CSGDiagram rightDiagram = walk(ray, node.getRight());
            return leftDiagram.apply(leftDiagram, rightDiagram, ((CSGOperationNode) node).getData());
        } else if (node instanceof CSGObjectNode) {
            Collision collisions[] = ((CSGObjectNode) node).getData().trace(ray);
            
            if (collisions.length > 0) {
                Vector3 collisionPoint = ray.getFrom().add(ray.getDirection().multiply(collisions[0].getDistance()));
                boolean currentOutsidePassage = ray.getDirection().dotProduct(collisions[0].getPrimitive().getNorm(collisionPoint)) > 0;
                                
                List<CSGDiagram.Point> points = new ArrayList<CSGDiagram.Point>(collisions.length);
                
                for (Collision collision : collisions) {
                    points.add(new CSGDiagram.Point(collision, currentOutsidePassage));
                    currentOutsidePassage = !currentOutsidePassage;
                }
                
                return new CSGDiagramImpl(points);
            }
            
            return EMPTY_DIAGRAM;
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
