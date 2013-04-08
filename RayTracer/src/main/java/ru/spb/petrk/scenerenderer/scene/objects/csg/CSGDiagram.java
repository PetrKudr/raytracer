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
     * @param second CSG diagram
     * @param operation to perform
     * @return result of operation
     */
    CSGDiagram apply(CSGDiagram second, CSGOperation operation);
    
    
    /**
     * Represents collision points
     */
    public static final class Point {
        
        private final Collision collision;
        
        private final boolean goesOutside;

        public Point(Collision collision, boolean goesOutside) {
            this.collision = collision;
            this.goesOutside = goesOutside;
        }

        public boolean isGoesOutside() {
            return goesOutside;
        }        

        public Collision getCollision() {
            return collision;
        }
    }
    
}
