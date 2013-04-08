package ru.spb.petrk.scenerenderer.scene.objects.csg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author PetrK
 */
public class CSGDiagramImpl implements CSGDiagram {
    
    private final List<Point> points;    

    
    public CSGDiagramImpl(List<Point> points) {
        this.points = points;
    }

    @Override
    public List<Point> getCollisionPoints() {
        return points;
    }

    @Override
    public CSGDiagram apply(CSGDiagram second, CSGOperation operation) {
        PointEmitter emitter;
        
        boolean isInsideFirst = points.size() > 0 ? points.get(0).isGoesOutside() : false;
        boolean isInsideSecond = second.getCollisionPoints().size() > 0 ? second.getCollisionPoints().get(0).isGoesOutside() : false;
        
        switch (operation) {
            case union:
                emitter = new UnionEmitter(isInsideFirst, isInsideSecond);
                break;
                
            case intersection:
                emitter = new IntersectionEmitter(isInsideFirst, isInsideSecond);
                break;
                
            case subtraction:
                emitter = new SubtrationEmitter(isInsideFirst, isInsideSecond);
                break;
                
            default:
                throw new IllegalArgumentException("Unknown operation!");        
        }
        
        List<Point> mergedPoints = new ArrayList<Point>();
        
        Iterator<Point> firstIter = getCollisionPoints().iterator();
        Iterator<Point> secondIter = second.getCollisionPoints().iterator();
        
        Point firstPoint = null;
        Point secondPoint = null;
        
        // Process points from both diagrams
        while ((firstPoint != null || firstIter.hasNext()) && (secondPoint != null || secondIter.hasNext())) {
            if (firstPoint == null) {
                firstPoint = firstIter.next();
            }
            
            if (secondPoint == null) {
                secondPoint = secondIter.next();
            }
            
            Point emitted = null;
            
            if (firstPoint.getCollision().getDistance() < secondPoint.getCollision().getDistance()) {
                emitted = emitter.emit(firstPoint, true);
                firstPoint = null;
            } else {
                emitted = emitter.emit(secondPoint, false);
                secondPoint = null;
            }
            
            if (emitted != null) {
                mergedPoints.add(emitted);
            }            
        }
        
        // Process rest points from first diagram
        if (firstPoint == null && firstIter.hasNext()) {
            firstPoint = firstIter.next();
        }
        
        while (firstPoint != null) {
            Point emitted = emitter.emit(firstPoint, true);
            if (emitted != null) {
                mergedPoints.add(emitted);
            }
            
            if (firstIter.hasNext()) {
                firstPoint = firstIter.next();
            } else {
                firstPoint = null;
            }
        }
        
        // Process rest points from second diagram
        if (secondPoint == null && secondIter.hasNext()) {
            secondPoint = secondIter.next();
        }        
        
        while (secondPoint != null) {
            Point emitted = emitter.emit(secondPoint, false);
            if (emitted != null) {
                mergedPoints.add(emitted);
            }
            
            if (secondIter.hasNext()) {
                secondPoint = secondIter.next();
            } else {
                secondPoint = null;
            }
        }
        
        return new CSGDiagramImpl(mergedPoints);
    }
    
    
    private static abstract class PointEmitter {
        
        private boolean insideFirst;
        
        private boolean insideSecond;

        
        public PointEmitter(boolean insideFirst, boolean insideSecond) {
            this.insideFirst = insideFirst;
            this.insideSecond = insideSecond;
        }

        /**
         * @param point - point
         * @param fromFirst - is point from first diagram
         * @return resulting point or null
         */
        public Point emit(Point point, boolean fromFirst) {
            Point result = fromFirst ? handleFromFirst(point) : handleFromSecond(point);
            
            // change state
            if (fromFirst) {
                insideFirst = !point.isGoesOutside();
            } else {
                insideSecond = !point.isGoesOutside();
            }
            
            return result;
        }

        protected boolean isInsideFirst() {
            return insideFirst;
        }

        protected boolean isInsideSecond() {
            return insideSecond;
        }
        
        protected abstract Point handleFromFirst(Point point);
            
        protected abstract Point handleFromSecond(Point point);
        
    }
    
    private static class UnionEmitter extends PointEmitter {

        public UnionEmitter(boolean insideFirst, boolean insideSecond) {
            super(insideFirst, insideSecond);
        }

        @Override
        protected Point handleFromFirst(Point point) {
            return handle(point, isInsideFirst(), isInsideSecond());
        }

        @Override
        protected Point handleFromSecond(Point point) {
            return handle(point, isInsideSecond(), isInsideFirst());
        }
        
        private Point handle(Point point1, boolean inside1, boolean inside2) {
            if (!inside2) {
                return point1;
            }            
            return null;            
        }        
    }
    
    private static class SubtrationEmitter extends PointEmitter {

        public SubtrationEmitter(boolean insideFirst, boolean insideSecond) {
            super(insideFirst, insideSecond);
        }

        @Override
        protected Point handleFromFirst(Point point) {
            if (!isInsideSecond())  {
                if (!point.isGoesOutside()) {
                    if (!isInsideFirst()) {
                        return point;
                    }
                } else {
                    if (isInsideFirst()) {
                        return point;
                    }
                }
            }
            return null;
        }

        @Override
        protected Point handleFromSecond(Point point) {
            if (isInsideFirst()) {
                point.getCollision().setInversed(true);
                return new Point(point.getCollision(), !point.isGoesOutside());
            }
            return null;
        }  
    }
    
    private static class IntersectionEmitter extends PointEmitter {

        public IntersectionEmitter(boolean insideFirst, boolean insideSecond) {
            super(insideFirst, insideSecond);
        }
        
        @Override
        protected Point handleFromFirst(Point point) {
            return handle(point, isInsideFirst(), isInsideSecond());
        }

        @Override
        protected Point handleFromSecond(Point point) {
            return handle(point, isInsideSecond(), isInsideFirst());
        }
        
        private Point handle(Point point1, boolean inside1, boolean inside2) {
            if (inside2) {
                if (!point1.isGoesOutside()) {
                    if (!inside1) {
                        return point1;
                    }
                } else {
                    if (inside1) {
                        return point1;
                    }
                }
            }
            return null;            
        }
    }    
    
}
