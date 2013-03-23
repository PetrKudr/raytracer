package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Sphere;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class SphereBuilder extends AbstractElementBuilder<Sphere> {
    
    private Vector3 center;
    
    private double radius;    
    

    public SphereBuilder(ElementContext parentContext, FinishCallback<Sphere> callback) {
        super(parentContext, callback);
    }    

    @Override
    public ElementHandler createHandler(String name) {
        if ("center".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    SphereBuilder.this.center = value;
                }
                
            });
        } else if ("radius".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    SphereBuilder.this.radius = value;
                }
                
            });            
        }
        return null;
    }    

    @Override
    protected Sphere build() {
        return new Sphere(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), center, radius);
    }    
    
}
