package ru.spb.petrk.scenerenderer.scene.objects;

import java.util.List;
import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.primitives.Triangle;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;
import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class ModelSceneObject implements SceneObject {
    
    private final BoundingBox boundingBox;

    private final List<Triangle> triangles;

    
    public ModelSceneObject(List<Triangle> triangles) {
        this.triangles = triangles;
        
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;
        
        for (Triangle triangle : triangles) {
            minX = min(minX, triangle.getV0().getX(), triangle.getV1().getX(), triangle.getV2().getX());
            minY = min(minY, triangle.getV0().getY(), triangle.getV1().getY(), triangle.getV2().getY());
            minZ = min(minZ, triangle.getV0().getZ(), triangle.getV1().getZ(), triangle.getV2().getZ());
            
            maxX = max(maxX, triangle.getV0().getX(), triangle.getV1().getX(), triangle.getV2().getX());
            maxY = max(maxY, triangle.getV0().getY(), triangle.getV1().getY(), triangle.getV2().getY());
            maxZ = max(maxZ, triangle.getV0().getZ(), triangle.getV1().getZ(), triangle.getV2().getZ());        
        }
        
        this.boundingBox = new BoundingBox(
                new Vector3(minX, minY, minZ), 
                new Vector3(maxX, maxY, maxZ)
        );
    }

    @Override
    public boolean checkIntersectionPossibility(Ray ray) {
        return boundingBox.intersect(ray);
    }

    @Override
    public Collision trace(Ray ray) {
        double minDistance = Double.POSITIVE_INFINITY;
        Primitive nearestPrimitive = null;
        
        for (Primitive primitive : triangles) {
            double distance = primitive.intersect(ray);

            if (distance >= MathUtils.GEOMETRY_THRESHOLD && distance < minDistance) {
                nearestPrimitive = primitive;
                minDistance = distance;
            }
        }
        
        return nearestPrimitive != null ? new Collision(minDistance, nearestPrimitive) : null;        
    }    
    
    private double min(double value1, double value2, double value3, double value4) {
        return Math.min(Math.min(Math.min(value1, value2), value3), value4);
    }
    
    private double max(double value1, double value2, double value3, double value4) {
        return Math.max(Math.max(Math.max(value1, value2), value3), value4);
    }    
    
    
    private static class BoundingBox {
        
        private final Vector3 min;
        
        private final Vector3 max;

        public BoundingBox(Vector3 min, Vector3 max) {
            this.min = min;
            this.max = max;
        }
        
        public boolean intersect(Ray r) {
            double tmin, tmax, tymin, tymax, tzmin, tzmax;
            if (r.getDirection().getX() >= 0) {
                tmin = (min.getX() - r.getFrom().getX()) / r.getDirection().getX();
                tmax = (max.getX() - r.getFrom().getX()) / r.getDirection().getX();
            } else {
                tmin = (max.getX() - r.getFrom().getX()) / r.getDirection().getX();
                tmax = (min.getX() - r.getFrom().getX()) / r.getDirection().getX();
            }
            if (r.getDirection().getY() >= 0) {
                tymin = (min.getY() - r.getFrom().getY()) / r.getDirection().getY();
                tymax = (max.getY() - r.getFrom().getY()) / r.getDirection().getY();
            } else {
                tymin = (max.getY() - r.getFrom().getY()) / r.getDirection().getY();
                tymax = (min.getY() - r.getFrom().getY()) / r.getDirection().getY();
            }
            if ((tmin > tymax) || (tymin > tmax)) {
                return false;
            }
            if (tymin > tmin) {
                tmin = tymin;
            }
            if (tymax < tmax) {
                tmax = tymax;
            }
            if (r.getDirection().getZ() >= 0) {
                tzmin = (min.getZ() - r.getFrom().getZ()) / r.getDirection().getZ();
                tzmax = (max.getZ() - r.getFrom().getZ()) / r.getDirection().getZ();
            } else {
                tzmin = (max.getZ() - r.getFrom().getZ()) / r.getDirection().getZ();
                tzmax = (min.getZ() - r.getFrom().getZ()) / r.getDirection().getZ();
            }
            if ((tmin > tzmax) || (tzmin > tmax)) {
                return false;
            }
            if (tzmin > tmin) {
                tmin = tzmin;
            }
            if (tzmax < tmax) {
                tmax = tzmax;
            }
            return (tmin < Double.POSITIVE_INFINITY && tmax >= 0);
//            return true;
        }        
    }
}