package ru.spb.petrk.scenerenderer.parser.builders;

import java.util.ArrayList;
import java.util.List;
import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementContextImpl;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.objects.ModelSceneObject;
import ru.spb.petrk.scenerenderer.scene.Primitive;
import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.objects.SimpleSceneObject;
import ru.spb.petrk.scenerenderer.scene.primitives.Cone;
import ru.spb.petrk.scenerenderer.scene.primitives.Cylinder;
import ru.spb.petrk.scenerenderer.scene.primitives.Plane;
import ru.spb.petrk.scenerenderer.scene.primitives.Sphere;
import ru.spb.petrk.scenerenderer.scene.primitives.Triangle;

/**
 *
 * @author PetrK
 */
class SceneObjectsBuilder extends AbstractElementBuilder<List<SceneObject>> {
    
    private final ElementContext context;
    
    private final List<DeferredElementBuilder<? extends Primitive>> deferredBuilders = new ArrayList<DeferredElementBuilder<? extends Primitive>>();
    
    private final List<SceneObject> sceneObjects = new ArrayList<SceneObject>();    
    

    public SceneObjectsBuilder(ElementContext parentContext, FinishCallback<List<SceneObject>> callback) {
        super(parentContext, callback);
        context = new ElementContextImpl(parentContext);
    }

    @Override
    public ElementHandler getHandler(String name) {
        if ("material".equals(name)) {
            return new MaterialBuilder(context, new FinishCallback<Material>() {

                @Override
                public void handle(Material value) {
                    SceneObjectsBuilder.this.context.putProperty(PropertyNames.MATERIAL, Material.class, value);
                }
                
            });
        } else if ("plane".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new PlaneBuilder(context, new FinishCallback<Plane>() {

                @Override
                public void handle(Plane value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new SimpleSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        } else if ("sphere".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new SphereBuilder(context, new FinishCallback<Sphere>() {

                @Override
                public void handle(Sphere value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new SimpleSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        } else if ("triangle".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new TriangleBuilder(context, new FinishCallback<Triangle>() {

                @Override
                public void handle(Triangle value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new SimpleSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        } else if ("cylinder".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new CylinderBuilder(context, new FinishCallback<Cylinder>() {

                @Override
                public void handle(Cylinder value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new SimpleSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        } else if ("cone".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new ConeBuilder(context, new FinishCallback<Cone>() {

                @Override
                public void handle(Cone value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new SimpleSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        } else if ("model".equals(name)) {
            DeferredElementBuilder deferredBuilder = new DeferredElementBuilder(new ModelBuilder(context, new FinishCallback<List<Triangle>>() {

                @Override
                public void handle(List<Triangle> value) {
                    SceneObjectsBuilder.this.sceneObjects.add(new ModelSceneObject(value));
                }
                
            }));
            deferredBuilders.add(deferredBuilder);
            return deferredBuilder;
        }
        
        return null;
    }

    @Override
    protected List<SceneObject> build() {
        for (DeferredElementBuilder builder : deferredBuilders) {
            builder.launchFinish();
        }
        return sceneObjects;
    }    
    
}
