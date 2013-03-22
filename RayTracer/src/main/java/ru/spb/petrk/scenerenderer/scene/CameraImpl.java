package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.scene.tracing.Ray;
import ru.spb.petrk.scenerenderer.scene.tracing.RayImpl;
import ru.spb.petrk.scenerenderer.scene.tracing.RayTracer;
import ru.spb.petrk.scenerenderer.scene.tracing.RayTracerImpl;
import ru.spb.petrk.scenerenderer.scene.tracing.refraction.FresnelRefractionStrategy;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class CameraImpl implements Camera {
    
    private RayTracer tracer;
    
    private Vector3 forwardDirection;
    
    private Vector3 upDirection;
    
    private Vector3 rightDirection;
    
    private Vector3 position;
    
    private double screenDistance;
    
    private double screenWidthAngle;
    
    private double screenHeightAngle;


    public CameraImpl(RayTracer tracer, Vector3 forwardDirection, Vector3 upDirection, Vector3 position, double screenDistance, double screenWidthAngle, double screenHeightAngle) {
        if (!MathUtils.equals(upDirection.dotProduct(forwardDirection), 0)) {
            throw new IllegalArgumentException("Up direction must be orthogonal to forward direction");
        }
        
        this.tracer = tracer;
        this.forwardDirection = forwardDirection.normalize();
        this.upDirection = upDirection.normalize();
        this.rightDirection = this.upDirection.crossProduct(this.forwardDirection);
        this.position = position;
        this.screenDistance = screenDistance;
        this.screenWidthAngle = screenWidthAngle;
        this.screenHeightAngle = screenHeightAngle;
    }

    @Override
    public Vector3 getForwardDirection() {
        return forwardDirection;
    }

    @Override
    public Vector3 getUpDirection() {
        return upDirection;
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public double getScreenDistance() {
        return screenDistance;
    }

    @Override
    public double getScreenWidthAngle() {
        return screenWidthAngle;
    }

    @Override
    public double getScreenHeightAngle() {
        return screenHeightAngle;
    }

    @Override
    public Snapshot makeSnapshot(Scene scene, int xResolution, int yResolution) {
        int scanSize = xResolution;
        int[] picture = new int[scanSize * yResolution];
        
        Vector3 forward = forwardDirection.multiply(screenDistance);
        Vector3 center = position.add(forward);
        Vector3 up = upDirection.multiply(Math.tan(screenHeightAngle / 2) * forward.getLength());
        Vector3 right = rightDirection.multiply(Math.tan(screenWidthAngle / 2) * forward.getLength());        
        
        Vector3 leftTop = center.add(up).subtract(right);
        
        for (int y = 0; y < yResolution; y++) {
            for (int x = 0; x < xResolution; x++) {
                double xCoefficient = ((double)x) / yResolution;
                double yCoefficient = ((double)y) / xResolution;
                
                Vector3 screenPoint = leftTop.add(right.multiply(2 * xCoefficient)).subtract(up.multiply(2 * yCoefficient));
                Ray ray = new RayImpl(position, screenPoint.subtract(position).normalize());                
                
                picture[x + scanSize * y] = MathUtils.filterColor(tracer.color(scene, ray));
            }
        }
        
        return new SnapshotImpl(xResolution, yResolution, scanSize, picture);
    }

}
