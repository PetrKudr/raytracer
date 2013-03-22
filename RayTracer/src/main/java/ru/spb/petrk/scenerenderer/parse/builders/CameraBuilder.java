package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;
import ru.spb.petrk.scenerenderer.scene.Camera;
import ru.spb.petrk.scenerenderer.scene.CameraImpl;
import ru.spb.petrk.scenerenderer.scene.tracing.RayTracerImpl;
import ru.spb.petrk.scenerenderer.scene.tracing.refraction.FresnelRefractionStrategy;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class CameraBuilder extends AbstractElementBuilder<Camera> {

    private Vector3 lookAt;
    
    private Vector3 upDirection;
    
    private Vector3 position;
    
    private double screenDistance;
    
    private double screenWidthAngle;
    
    private double screenHeightAngle;

    
    public CameraBuilder(ElementContext context, FinishCallback<Camera> callback) {
        super(context, callback);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("pos".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    CameraBuilder.this.position = value;
                }
                
            });
        } else if ("up".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    CameraBuilder.this.upDirection = value;
                }
                
            });            
        } else if ("look_at".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    CameraBuilder.this.lookAt = value;
                }
                
            });            
        } else if ("fov_x".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    CameraBuilder.this.screenWidthAngle = value;
                }
                
            });
        } else if ("fov_y".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    CameraBuilder.this.screenHeightAngle = value;
                }
                
            });
        } else if ("dist_to_near_plane".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    CameraBuilder.this.screenDistance = value;
                }
                
            });            
        }
        return null;
    }

    @Override
    public Camera build() {
        return new CameraImpl(
                new RayTracerImpl(Vector3.ZERO_VECTOR, new FresnelRefractionStrategy()),
                lookAt.subtract(position),
                upDirection,
                position,
                screenDistance,
                screenWidthAngle,
                screenHeightAngle
        );
    }
}
