package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class VectorBuilder extends AbstractElementBuilder<Vector3> {

    private double x;
    
    private double y;
    
    private double z;
    

    public VectorBuilder(ElementContext parentContext, FinishCallback<Vector3> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("x".equalsIgnoreCase(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {
                
                @Override
                public void handle(Double value) {
                    VectorBuilder.this.x = value;
                }                    
                
            });
        } else if ("y".equalsIgnoreCase(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {
                
                @Override
                public void handle(Double value) {
                    VectorBuilder.this.y = value;
                }                    
                
            });
        } else if ("z".equalsIgnoreCase(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {
                
                @Override
                public void handle(Double value) {
                    VectorBuilder.this.z = value;
                }                    
                
            });
        }
        return null;
    }

    @Override
    protected Vector3 build() {
        return new Vector3(x, y, z);
    }
    
}
