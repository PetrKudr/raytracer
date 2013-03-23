package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Cone;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class ConeBuilder extends AbstractElementBuilder<Cone> {
    
    private Vector3 top;
    
    private Vector3 direction;
    
    private double angle;
    
    private double height;
    

    public ConeBuilder(ElementContext parentContext, FinishCallback<Cone> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler createHandler(String name) {
        if ("top".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    ConeBuilder.this.top = value;
                }
                
            });
        } else if ("direction".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    ConeBuilder.this.direction = value;
                }
                
            });            
        } else if ("angle".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    ConeBuilder.this.angle = value;
                }
                
            });            
        } else if ("height".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    ConeBuilder.this.height = value;
                }
                
            });           
        }
        return null;
    }

    @Override
    protected Cone build() {
        return new Cone(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), top, direction, angle, height);
    }
    
}
