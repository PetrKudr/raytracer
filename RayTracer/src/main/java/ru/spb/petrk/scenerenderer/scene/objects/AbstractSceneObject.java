package ru.spb.petrk.scenerenderer.scene.objects;

import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.tracing.Collision;

/**
 *
 * @author PetrK
 */
abstract class AbstractSceneObject implements SceneObject {
    
    protected static final Collision EMPTY[] = new Collision[0];

}
