package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Plane;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class PlaneBuilder extends AbstractElementBuilder<Plane> {
    
    private Vector3 point;
    
    private Vector3 norm;    
    

    public PlaneBuilder(ElementContext parentContext, FinishCallback<Plane> callback) {
        super(parentContext, callback);
    }    

    @Override
    public ElementHandler getHandler(String name) {
        if ("point".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    PlaneBuilder.this.point = value;
                }
                
            });
        } else if ("norm".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    PlaneBuilder.this.norm = value;
                }
                
            });            
        }
        return null;
    }    

    @Override
    protected Plane build() {
        return new Plane(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), point, norm);
    }
   
}
