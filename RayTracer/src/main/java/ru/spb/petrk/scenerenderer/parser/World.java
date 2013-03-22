package ru.spb.petrk.scenerenderer.parser;

import ru.spb.petrk.scenerenderer.scene.Camera;
import ru.spb.petrk.scenerenderer.scene.Scene;

/**
 *
 * @author PetrK
 */
public class World {
    
    private Scene scene;
    
    private Camera camera;

    
    public World(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;
    }    

    public Scene getScene() {
        return scene;
    }

    public Camera getCamera() {
        return camera;
    }    
    
}
