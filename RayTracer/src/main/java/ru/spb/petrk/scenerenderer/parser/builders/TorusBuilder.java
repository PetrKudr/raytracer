package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Torus;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class TorusBuilder extends AbstractElementBuilder<Torus> {
    
    private Vector3 center;
    
    private Vector3 norm;
    
    private double innerRadius;
    
    private double outerRadius;    
    

    public TorusBuilder(ElementContext parentContext, FinishCallback<Torus> callback) {
        super(parentContext, callback);
    }

    @Override
    protected ElementHandler createHandler(String name) {
        if ("center".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    TorusBuilder.this.center = value;
                }
                
            });
        } else if ("norm".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    TorusBuilder.this.norm = value;
                }
                
            });            
        } else if ("inner_radius".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    TorusBuilder.this.innerRadius = value;
                }
                
            });
        } else if ("outer_radius".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    TorusBuilder.this.outerRadius = value;
                }
                
            });            
        }
        return null;
    }

    @Override
    protected Torus build() {
        return new Torus(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), center, norm, innerRadius, outerRadius);
    }

}
