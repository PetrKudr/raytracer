package ru.spb.petrk.scenerenderer.parse.builders;

import java.util.ArrayList;
import java.util.List;
import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;
import ru.spb.petrk.scenerenderer.parse.World;
import ru.spb.petrk.scenerenderer.scene.Camera;
import ru.spb.petrk.scenerenderer.scene.Light;
import ru.spb.petrk.scenerenderer.scene.SceneImpl;
import ru.spb.petrk.scenerenderer.scene.SceneObject;

/**
 *
 * @author PetrK
 */
public class WorldBuilder extends AbstractElementBuilder<World> {
    
    private final List<Light> lights = new ArrayList<Light>();
    
    private final List<SceneObject> sceneObjects = new ArrayList<SceneObject>();
    
    private Camera camera;
    

    public WorldBuilder(ElementContext parentContext, FinishCallback<World> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("scene".equals(name)) {
            return this;
        } else if ("camera".equals(name)) {
            return new CameraBuilder(parentContext, new FinishCallback<Camera>() {

                @Override
                public void handle(Camera value) {
                    WorldBuilder.this.camera = value;
                }
                
            });            
        } else if ("light".equals(name)) {
            return new LightBuilder(parentContext, new FinishCallback<Light>() {

                @Override
                public void handle(Light value) {
                    WorldBuilder.this.lights.add(value);
                }
                
            });
        } else if ("object".equals(name)) {
            return new SceneObjectsBuilder(parentContext, new FinishCallback<List<SceneObject>>() {

                @Override
                public void handle(List<SceneObject> value) {
                    WorldBuilder.this.sceneObjects.addAll(value);
                }
                
            });            
        }
        return null;
    }    
    
    @Override
    protected World build() {
        return new World(new SceneImpl(1, sceneObjects, lights), camera);
    }

}
