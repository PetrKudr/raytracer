package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;
import ru.spb.petrk.scenerenderer.parse.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Cylinder;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class CylinderBuilder extends AbstractElementBuilder<Cylinder> {

    private Vector3 bottom;
    
    private Vector3 top;
    
    private double radius;

    
    public CylinderBuilder(ElementContext parentContext, FinishCallback<Cylinder> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("bottom".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    CylinderBuilder.this.bottom = value;
                }
                
            });
        } else if ("top".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    CylinderBuilder.this.top = value;
                }
                
            });            
        } else if ("radius".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    CylinderBuilder.this.radius = value;
                }
                
            });
        }
        return null;
    }

    @Override
    protected Cylinder build() {
        return new Cylinder(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), bottom, top, radius);
    }
    
}
