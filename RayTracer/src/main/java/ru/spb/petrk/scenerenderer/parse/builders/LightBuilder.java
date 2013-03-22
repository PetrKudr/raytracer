package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;
import ru.spb.petrk.scenerenderer.scene.Light;
import ru.spb.petrk.scenerenderer.scene.LightImpl;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class LightBuilder extends AbstractElementBuilder<Light> {
    
    private Vector3 position;
    
    private Vector3 diffuse;
    
    private Vector3 ambient;
    
    private Vector3 specular;
    

    public LightBuilder(ElementContext parentContext, FinishCallback<Light> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("pos".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    LightBuilder.this.position = value;
                }
                
            });
        } else if ("ambient_emission".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    LightBuilder.this.ambient = value;
                }
                
            });            
        } else if ("diffuse_emission".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    LightBuilder.this.diffuse = value;
                }
                
            });            
        } else if ("specular_emission".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    LightBuilder.this.specular = value;
                }
                
            });            
        }
        return null;
    }

    @Override
    protected Light build() {
        return new LightImpl(position, diffuse, ambient, specular);
    }

}
