package ru.spb.petrk.scenerenderer.scene.objects.csg;

import java.util.List;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;

/**
 *
 * @author PetrK
 */
public interface CSGDiagram {
    
    /**
     * @return collision points
     */
    List<Point> getCollisionPoints();
    
    /**
     * @param first CSG diagram
     * @param second CSG diagram
     * @param operation to perform
     * @return result of operation
     */
    CSGDiagram apply(CSGDiagram first, CSGDiagram second, CSGOperation operation);
    
    
    /**
     * Represents collision points
     */
    public static final class Point {
        
        private final Collision collision;
        
        private final boolean out;

        public Point(Collision collision, boolean out) {
            this.collision = collision;
            this.out = out;
        }

        public boolean isOut() {
            return out;
        }        

        public Collision getCollision() {
            return collision;
        }
    }
    
}
